package com.example.ainunrentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ainunrentcar.Model.ModelDetailUnit;
import com.example.ainunrentcar.Service.BaseUrl;
import com.example.ainunrentcar.View.AdapterDetailUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailMobilActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private List<ModelDetailUnit> modelDetailUnitList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView mList;
    private BaseUrl baseUrl = new BaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);
        toolbar();
        mList = (RecyclerView) findViewById(R.id.recyclerView);
        getDataApiMobilTersedia();
        setAdapterKeRecycleView();
    }

    // Untuk toolbar
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toyota Avanza 2019");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataApiMobilTersedia() {
        String idMobil = getIntent().getExtras().getString("idMobil");
        String urlDetailUnit = baseUrl.detailUnit(idMobil);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlDetailUnit, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        ModelDetailUnit modelDetailUnit = new ModelDetailUnit();
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray urlGambar = jsonObject.getJSONArray("urlGambar");
                        for (int j = 0; j < urlGambar.length(); j++) {
                            BaseUrl url = new BaseUrl();
                            String namaFileGambar = urlGambar.get(j).toString();
                            modelDetailUnit.setUrlImage(url.urlGaleriUnit(namaFileGambar));
                            modelDetailUnitList.add(modelDetailUnit);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
//                            tampilanLoading.setVisibility(View.GONE);
                    }
                }


                adapter.notifyDataSetChanged();
//                tampilanLoading.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
//                tampilanLoading.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailMobilActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void setAdapterKeRecycleView() {

        modelDetailUnitList = new ArrayList<ModelDetailUnit>();
        adapter = new AdapterDetailUnit(DetailMobilActivity.this, modelDetailUnitList);

        linearLayoutManager = new LinearLayoutManager(DetailMobilActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
    }

}
