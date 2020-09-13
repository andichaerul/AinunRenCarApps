package com.example.ainunrentcar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ainunrentcar.Model.ModelOffers;
import com.example.ainunrentcar.Service.BaseUrl;
import com.example.ainunrentcar.View.AdapterOffers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.icu.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeFragment extends Fragment {
    private LinearLayout cariMobilTersedia;
    private DatePickerDialog datePickerDialog;
    private TextView resultTanggalAwal;
    private TextView resultTanggalSelesai;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ModelOffers> modelOffersList;
    private RecyclerView.Adapter adapter;
    private BaseUrl baseUrl = new BaseUrl();
    private String urlSlide = baseUrl.baseUrl + baseUrl.subUrlOffersPromo;
    private LinearLayout tanggalAwal;
    private LinearLayout tanggalSelesai;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        setAdapterKeRecycleView();
        defaultValue();
        getDataApiOffers();
        intentCariMobil();
        eventKlikPilihTanggalAwal();
        eventKlikPilihTanggalSelesai();
        return root;
    }

    // untuk Deklarasi xml Id
    private void initViews(View root) {
        resultTanggalAwal = (TextView) root.findViewById(R.id.tglAwal);
        resultTanggalSelesai = (TextView) root.findViewById(R.id.tglSelesai);
        mList = (RecyclerView) root.findViewById(R.id.recyclerView);
        cariMobilTersedia = (LinearLayout) root.findViewById(R.id.cariMobilTersedia);
        tanggalAwal = (LinearLayout) root.findViewById(R.id.tanggalAwal);
        tanggalSelesai = (LinearLayout) root.findViewById(R.id.tanggalSelesai);
    }

    // Event click pilih tanggal awal
    private void eventKlikPilihTanggalAwal() {
        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                tampilkanDatePicker("awal");
            }
        });
    }

    // Event click pilih tanggal selesai
    private void eventKlikPilihTanggalSelesai() {
        tanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                tampilkanDatePicker("selesai");
            }
        });
    }

    // Intent ke Activity cari mobil yang tersedia
    private void intentCariMobil() {
        cariMobilTersedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), HasilPencarianMobilActivity.class);
                i.putExtra("tanggalAwal", resultTanggalAwal.getText().toString());
                i.putExtra("tanggalSelesai", resultTanggalSelesai.getText().toString());
                startActivity(i);
            }
        });
    }


    // untuk Mengset Adapter Offers (Penawaran) ke recycleview
    private void setAdapterKeRecycleView() {

        modelOffersList = new ArrayList<ModelOffers>();
        adapter = new AdapterOffers(getContext(), modelOffersList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
    }


    // Untuk mengset value tgl awal dan tgl selesai pada saat aplikasi awal dibuka
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void defaultValue() {
        Calendar currentTime = Calendar.getInstance();
        resultTanggalAwal.setText(dateFormat.format(currentTime.getTime()));
        currentTime.add(Calendar.DATE, 1);
        resultTanggalSelesai.setText(dateFormat.format(currentTime.getTime()));
    }


    // Untuk menampilkan datepicker
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void tampilkanDatePicker(final String awalOrSelesai) {
        Calendar newCalendar = Calendar.getInstance();
        int setTanggal = 1;
        if (awalOrSelesai.equals("awal")) {
            String[] tglAwalArr = resultTanggalAwal.getText().toString().split("-");
            setTanggal = Integer.parseInt(tglAwalArr[0]);
            //Set minDate
            newCalendar.add(Calendar.YEAR, 0);
        } else if (awalOrSelesai.equals("selesai")) {
            String[] tglSelesaiArr = resultTanggalSelesai.getText().toString().split("-");
            setTanggal = Integer.parseInt(tglSelesaiArr[0]);
            setTanggalMinimalSelesaiRental(newCalendar);
        }
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (awalOrSelesai.equals("awal")) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    resultTanggalAwal.setText(dateFormat.format(newDate.getTime()));
                    setResultTanggalSelesai(newDate);
                } else if (awalOrSelesai.equals("selesai")) {
                    Calendar newDate1 = Calendar.getInstance();
                    newDate1.set(year, monthOfYear, dayOfMonth);
                    resultTanggalSelesai.setText(dateFormat.format(newDate1.getTime()));
                }
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), setTanggal);
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTanggalMinimalSelesaiRental(Calendar newCalendar) {
        String[] dariTanggalAwal = resultTanggalAwal.getText().toString().split("-");
        int mm = Integer.parseInt(dariTanggalAwal[1]);
        int dd = Integer.parseInt(dariTanggalAwal[0]);
        int yyyy = Integer.parseInt(dariTanggalAwal[2]);
        newCalendar.set(Calendar.MONTH, mm);
        newCalendar.set(Calendar.DATE, dd);
        newCalendar.set(Calendar.YEAR, yyyy);
        newCalendar.add(Calendar.DATE, 1);
    }


    // Untuk mengset tanggal selesai
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setResultTanggalSelesai(Calendar date) {
        date.add(Calendar.DATE, 1);
        resultTanggalSelesai.setText(dateFormat.format(date.getTime()));
    }


    // Mengambil data api slide offers (Penawaran)
    private void getDataApiOffers() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlSlide, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        ModelOffers modelOffers = new ModelOffers();
                        modelOffers.setJudul(jsonObject.getString("JudulPromo"));
                        String namaFileGambar = jsonObject.getString("UrlGambar");
                        modelOffers.setUrlGambar(baseUrl.baseUrl + baseUrl.subUrlImageOffersPromo + namaFileGambar);
                        modelOffersList.add(modelOffers);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

}
