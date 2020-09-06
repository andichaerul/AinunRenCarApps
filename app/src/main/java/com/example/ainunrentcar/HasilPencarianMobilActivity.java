package com.example.ainunrentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ainunrentcar.Model.ModelDaftarMobilTersedia;
import com.example.ainunrentcar.Service.BaseUrl;
import com.example.ainunrentcar.View.AdapterDaftarMobilTersedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HasilPencarianMobilActivity extends AppCompatActivity {
    private List<ModelDaftarMobilTersedia> modelDaftarMobilTersediaList;
    private BaseUrl baseUrl = new BaseUrl();
    private String urlApiMobilTersedia = baseUrl.baseUrl + "api/v1/find_armada/2019-01-01/2019-01-01";
    private RecyclerView recyclerView;
    private AdapterDaftarMobilTersedia adapterDaftarMobilTersedia;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private TextView tanggalAwal;
    private TextView tanggalSelesai;
    private String tanggalAwalString;
    private String tanggalSelesaiString;
    private LinearLayout tombolFilter;

    public HasilPencarianMobilActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian_mobil);
        toolbar();
        initViews();
        setTitleToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.daftarMobilTersedia);
        addData();
        getDataApiMobilTersedia();

        tombolFilter = (LinearLayout) findViewById(R.id.tombolFilter);
        tombolFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UrutkanMobilYgTersediaActivity.class);
                startActivity(i);
            }
        });
    }

    // Untuk toolbar
    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        tanggalAwal = findViewById(R.id.tanggalAwal);
        tanggalSelesai = findViewById(R.id.tanggalSelesai);
    }

    @SuppressLint("SetTextI18n")
    private void setTitleToolbar() {
        tanggalAwalString = getIntent().getExtras().getString("tanggalAwal");
        tanggalSelesaiString = getIntent().getExtras().getString("tanggalSelesai");
        tanggalAwal.setText("Dari tanggal " + tanggalAwalString);
        tanggalSelesai.setText("Sampai tanggal " + tanggalSelesaiString);
    }

    private void getDataApiMobilTersedia() {
        final ProgressDialog progressDialog = new ProgressDialog(HasilPencarianMobilActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlApiMobilTersedia, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        ModelDaftarMobilTersedia modelDaftarMobilTersedia = new ModelDaftarMobilTersedia();
                        modelDaftarMobilTersedia.setNamaMitra(jsonObject.getString("namaMitra"));
                        modelDaftarMobilTersedia.setUnitBrand(jsonObject.getString("unitBrand"));
                        modelDaftarMobilTersediaList.add(modelDaftarMobilTersedia);
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
        RequestQueue requestQueue = Volley.newRequestQueue(HasilPencarianMobilActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    // untuk Mengset Adapter Offers (Penawaran) ke recycleview
    private void addData() {

        modelDaftarMobilTersediaList = new ArrayList<ModelDaftarMobilTersedia>();
        adapter = new AdapterDaftarMobilTersedia(HasilPencarianMobilActivity.this, modelDaftarMobilTersediaList);

        linearLayoutManager = new LinearLayoutManager(HasilPencarianMobilActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
