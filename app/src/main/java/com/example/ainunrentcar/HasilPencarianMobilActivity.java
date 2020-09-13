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
import com.example.ainunrentcar.Service.TglSql;
import com.example.ainunrentcar.View.AdapterDaftarMobilTersedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HasilPencarianMobilActivity extends AppCompatActivity {
    private List<ModelDaftarMobilTersedia> modelDaftarMobilTersediaList;
    private BaseUrl baseUrl = new BaseUrl();
    private RecyclerView recyclerView;
    private AdapterDaftarMobilTersedia adapterDaftarMobilTersedia;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private TextView tanggalAwal;
    private TextView tanggalSelesai;
    private String tanggalAwalString;
    private String tanggalSelesaiString;
    private LinearLayout tombolFilter;
    private TextView tampilanLoading;
    private TextView tampilkanMobilKosong;
    private TextView jumlahMobilTersedia;

    public HasilPencarianMobilActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian_mobil);
        toolbar();
        initViews();
        setTitleToolbar();
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
        tampilkanMobilKosong.setVisibility(View.GONE);
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
        recyclerView = (RecyclerView) findViewById(R.id.daftarMobilTersedia);
        tampilanLoading = (TextView) findViewById(R.id.tampilanLoading);
        tampilkanMobilKosong = (TextView) findViewById(R.id.tampilkanMobilKosong);
        jumlahMobilTersedia = (TextView) findViewById(R.id.jumlahMobilTersedia);
    }

    @SuppressLint("SetTextI18n")
    private void setTitleToolbar() {
        tanggalAwalString = getIntent().getExtras().getString("tanggalAwal");
        tanggalSelesaiString = getIntent().getExtras().getString("tanggalSelesai");
        tanggalAwal.setText("Dari tanggal " + tanggalAwalString);
        tanggalSelesai.setText("Sampai tanggal " + tanggalSelesaiString);
    }

    private void getDataApiMobilTersedia() {
        TglSql tglSql = new TglSql();
        String tglAwal = tglSql.tglSql(tanggalAwalString);
        String tglSelesai = tglSql.tglSql(tanggalSelesaiString);
        String urlApiMobilTersedia = baseUrl.baseUrl + "api/v1/find_armada/" + tglAwal + "/" + tglSelesai + "";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlApiMobilTersedia, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                jumlahMobilTersedia.setText(response.length() + " Jumlah Mobil Tersedia");
                if (response.length() == 0) {
                    tampilkanMobilKosong.setVisibility(View.VISIBLE);
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String urlGambar = jsonObject.getString("urlGambar");
                            ModelDaftarMobilTersedia modelDaftarMobilTersedia = new ModelDaftarMobilTersedia();
                            modelDaftarMobilTersedia.setNamaMitra(jsonObject.getString("namaMitra"));
                            modelDaftarMobilTersedia.setNamaLengkapUnit(jsonObject.getString("namaUnitLengkap"));
                            modelDaftarMobilTersedia.setHargaPerhari(jsonObject.getString("harga"));
                            modelDaftarMobilTersedia.setAlamatMitra(jsonObject.getString("alamatMitra"));
                            modelDaftarMobilTersedia.setUrlImgUnit(baseUrl.baseUrl + baseUrl.subUrlImageUnit + urlGambar);
                            modelDaftarMobilTersediaList.add(modelDaftarMobilTersedia);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tampilanLoading.setVisibility(View.GONE);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                tampilanLoading.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                tampilanLoading.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(HasilPencarianMobilActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    // untuk Mengset Adapter MobilTersedia ke recycleview
    private void addData() {

        modelDaftarMobilTersediaList = new ArrayList<ModelDaftarMobilTersedia>();
        adapter = new AdapterDaftarMobilTersedia(HasilPencarianMobilActivity.this, modelDaftarMobilTersediaList);

        linearLayoutManager = new LinearLayoutManager(HasilPencarianMobilActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}