package com.example.ainunrentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ainunrentcar.Model.ModelOffersSeeAll;
import com.example.ainunrentcar.Service.BaseUrl;
import com.example.ainunrentcar.View.AdapterOffersSeeAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SeeAllOffersActivity extends AppCompatActivity {
    private BaseUrl baseUrl = new BaseUrl();
    private String urlList = baseUrl.baseUrl + "api/v1/offers/see_all";
    private List<ModelOffersSeeAll> modelOffersSeeAllList;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_offers);
        toolbar();
        initViews();
        getDataApiOffersAll();
        setAdapterKeRecycleView();

    }

    // Untuk toolbar
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Penawaran Untuk Anda");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        mList = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void getDataApiOffersAll() {
        final ProgressDialog progressDialog = new ProgressDialog(SeeAllOffersActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlList, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ModelOffersSeeAll modelOffersSeeAll = new ModelOffersSeeAll();
                        modelOffersSeeAll.setJudulPromo(jsonObject.getString("judulPromo"));
                        String namaFileGambar = jsonObject.getString("urlGambar");
                        modelOffersSeeAll.setGambarPromo(baseUrl.baseUrl + baseUrl.subUrlImageOffersPromo + namaFileGambar);
                        modelOffersSeeAllList.add(modelOffersSeeAll);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SeeAllOffersActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void setAdapterKeRecycleView() {

        modelOffersSeeAllList = new ArrayList<ModelOffersSeeAll>();
        adapter = new AdapterOffersSeeAll(SeeAllOffersActivity.this, modelOffersSeeAllList);

        linearLayoutManager = new LinearLayoutManager(SeeAllOffersActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
    }
}
