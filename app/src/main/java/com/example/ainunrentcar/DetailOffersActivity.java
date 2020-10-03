package com.example.ainunrentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ainunrentcar.Service.BaseUrl;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailOffersActivity extends AppCompatActivity {
    private TextView judulSlide;
    private TextView isiContentSlide;
    private ImageView imgSlide;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offers);
        initViews();
        getDataApiOffers();
    }

    // Untuk toolbar
    private void toolbar(String judulToolbar) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(judulToolbar);
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
        judulSlide = (TextView) findViewById(R.id.judulContentSlide);
        isiContentSlide = (TextView) findViewById(R.id.isiContentSlide);
        imgSlide = (ImageView) findViewById(R.id.imgSlide);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_detail_offers);
    }

    private void getDataApiOffers() {
        String idOffers = getIntent().getExtras().getString("idOffers");
        final BaseUrl baseUrl = new BaseUrl();
        String apiUrl = baseUrl.baseUrl + "api/v1/offers/detail/" + idOffers;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(apiUrl, new Response.Listener<JSONArray>() {

            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONArray response) {
                Log.d("sasasa", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("sasasa", jsonObject.getString("judulPromo"));
                        String judul = jsonObject.getString("judulPromo");
                        String isiContent = jsonObject.getString("deskripsiPromo");
                        String imgUrl = jsonObject.getString("urlGambar");
                        toolbar(judul);
                        judulSlide.setText(judul);
                        isiContentSlide.setText(isiContent);
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.image_null);
                        requestOptions.error(R.drawable.image_null);
                        Glide.with(DetailOffersActivity.this)
                                .setDefaultRequestOptions(requestOptions)
                                .load(baseUrl.baseUrl + "public/assets/img/gambar_promo/" + imgUrl)
                                .apply(new RequestOptions().centerCrop())
                                .into(imgSlide);
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        progressDialog.dismiss();

                    }
                }
//                progressDialog.dismiss();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
//                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DetailOffersActivity.this);
        requestQueue.add(jsonArrayRequest);
    }
}
