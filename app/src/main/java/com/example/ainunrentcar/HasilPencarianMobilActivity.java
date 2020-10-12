package com.example.ainunrentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
    private LinearLayout placeHolder;
    private TextView jumlahMobilTersedia;
    private TextView closeFormFilter;
    private BottomSheetBehavior behavior;
    private View tampilanFilter;
    private ChipGroup filterUnit;
    private TextView tombolSimpanFilter;
    private ChipGroup chipGroupSort;
    private ChipGroup chipGroupByUnit;
    private TglSql tglSql = new TglSql();
    private static int firstVisibleInListview;

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
        getDataApiMobilTersedia("null", "null");
        setCloseFormFilter();
        setBottomSheetTampilkan();
        parsingDaftarUnitOnFilter();
        lihatHasilFilter();
        recycleViewIsScroll();

    }

    private void recycleViewIsScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();

                if (currentFirstVisible > firstVisibleInListview) {
                    Log.i("RecyclerView scrolled: ", "scroll up!");
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (currentFirstVisible < firstVisibleInListview) {
                    Log.i("RecyclerView scrolled: ", "scroll down!");
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                firstVisibleInListview = currentFirstVisible;

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
        tampilanFilter = (View) findViewById(R.id.tampilanFilter);
        tanggalAwal = (TextView) findViewById(R.id.tanggalAwal);
        tanggalSelesai = (TextView) findViewById(R.id.tanggalSelesai);
        recyclerView = (RecyclerView) findViewById(R.id.daftarMobilTersedia);
        placeHolder = (LinearLayout) findViewById(R.id.placeHolder);
        jumlahMobilTersedia = (TextView) findViewById(R.id.jumlahMobilTersedia);
        closeFormFilter = (TextView) findViewById(R.id.closeFormFilter);
        tombolSimpanFilter = (TextView) findViewById(R.id.tombolSimpanFilter);
        chipGroupSort = (ChipGroup) findViewById(R.id.filterSort);
        chipGroupByUnit = (ChipGroup) findViewById(R.id.chipGroupByUnit);
    }


    private void lihatHasilFilter() {
        tombolSimpanFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tglAwal = tglSql.tglSql(tanggalAwalString);
                String tglSelesai = tglSql.tglSql(tanggalSelesaiString);
                String url = baseUrl.urlHasilPencarianArmada(tglAwal, tglSelesai, sortByApa(), filterUnitApa(), "null");
                getDataApiMobilTersedia("update", url);
                addData();
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    //    Untuk Filter
    private String sortByApa() {
        String sortBy;
        Chip chip = null;
        List<Integer> ids = chipGroupSort.getCheckedChipIds();
        if (ids.size() == 0) {
            sortBy = "null";
        } else {
            for (Integer id : ids) {
                chip = chipGroupSort.findViewById(id);
            }
            sortBy = chip.getTag().toString();
        }
        return sortBy;
    }

    //    Untuk Filter Unit
    private String filterUnitApa() {
        String filterUnitBy;
        Chip chipUnit = null;
        List<Integer> ids = chipGroupByUnit.getCheckedChipIds();
        ArrayList<String> xx = new ArrayList<String>();
        if (ids.size() == 0) {
            filterUnitBy = "null";
        } else {
            for (Integer id : ids) {
                chipUnit = chipGroupByUnit.findViewById(id);
                xx.add(chipUnit.getTag().toString());
            }
            filterUnitBy = android.text.TextUtils.join("_", xx);
        }
        return filterUnitBy;
    }

    private void setBottomSheetTampilkan() {
        behavior = BottomSheetBehavior.from(tampilanFilter);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("sasasa", "1");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("sasasa", "2");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("sasasa", "3");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("sasasa", "4");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("sasasa", "5");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("sasasa", "6");
            }
        });

//        LinearLayout tombolFilter = (LinearLayout) findViewById(R.id.tombolFilter);
//        tombolFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else {
//                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//        });
    }

    //  Event Tombol close pada filter
    private void setCloseFormFilter() {
        closeFormFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    // Set judul halaman
    @SuppressLint("SetTextI18n")
    private void setTitleToolbar() {
        tanggalAwalString = getIntent().getExtras().getString("tanggalAwal");
        tanggalSelesaiString = getIntent().getExtras().getString("tanggalSelesai");
        tanggalAwal.setText("Dari tanggal " + tanggalAwalString);
        tanggalSelesai.setText("Sampai tanggal " + tanggalSelesaiString);
    }

    //    Get API Daftar Mobil Tersedia
    private void getDataApiMobilTersedia(String update, String url) {
        placeHolder.setVisibility(View.VISIBLE);
        TglSql tglSql = new TglSql();
        String tglAwal = tglSql.tglSql(tanggalAwalString);
        String tglSelesai = tglSql.tglSql(tanggalSelesaiString);
        String urlApiMobilTersedia;
        if (update.equals("update")) {
            urlApiMobilTersedia = url;
        } else {
            urlApiMobilTersedia = baseUrl.urlHasilPencarianArmada(tglAwal, tglSelesai, "null", "null", "null");
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlApiMobilTersedia, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                jumlahMobilTersedia.setText(response.length() + " Jumlah Mobil Tersedia");
                if (response.length() == 0) {
                    placeHolder.setVisibility(View.VISIBLE);
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
                            modelDaftarMobilTersedia.setIdMobil(jsonObject.getString("idMobil"));
                            modelDaftarMobilTersediaList.add(modelDaftarMobilTersedia);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            placeHolder.setVisibility(View.GONE);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                placeHolder.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                placeHolder.setVisibility(View.GONE);
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


    private void parsingDaftarUnitOnFilter() {
//        final ProgressDialog progressDialog = new ProgressDialog(HasilPencarianMobilActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        String tglAwal = tglSql.tglSql(tanggalAwalString);
        String tglSelesai = tglSql.tglSql(tanggalSelesaiString);
        String urlUnit = baseUrl.urlFilterGroupByUnit(tglAwal, tglSelesai);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlUnit, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        filterUnit = (ChipGroup) findViewById(R.id.chipGroupByUnit);

                        final Chip chip = new Chip(HasilPencarianMobilActivity.this);
                        chip.setText(jsonObject.getString("nama"));
                        chip.setTag(jsonObject.getString("id"));
                        chip.setCheckable(true);
                        chip.setFocusable(true);
                        chip.setClickable(true);
                        filterUnit.addView(chip);

                    } catch (JSONException e) {
                        e.printStackTrace();
//                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
//                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(HasilPencarianMobilActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

}
