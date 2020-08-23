package com.example.ainunrentcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity {
    String untuk;
    DatePicker datePicker;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        ActionBar actionBar = getActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pilihTanggalUntuk = getIntent().getExtras().getString("tanggalUntuk");
                switch (pilihTanggalUntuk) {
                    case "pilihTanggalAwal":
                        int tglAwal = getIntent().getExtras().getInt("tglAwal");
                        PilihTanggalAwal(tglAwal);
                        break;
                    case "pilihTanggalSelesai":
                        int tglSelesai = getIntent().getExtras().getInt("tglAwal");
                        String tglFullAwal = getIntent().getExtras().getString("tglFullAwal");
                        PilihTanggalSelesai(tglSelesai, tglFullAwal);
                        break;
                }
            }
        });

        String pilihTanggalUntuk = getIntent().getExtras().getString("tanggalUntuk");
        switch (pilihTanggalUntuk) {
            case "pilihTanggalAwal":
                int tglAwal = getIntent().getExtras().getInt("tglAwal");
                PilihTanggalAwal(tglAwal);
                break;
            case "pilihTanggalSelesai":
                int tglSelesai = getIntent().getExtras().getInt("tglAwal");
                String tglFullAwal = getIntent().getExtras().getString("tglFullAwal");
                PilihTanggalSelesai(tglSelesai, tglFullAwal);
                break;
        }
    }


    //Pilih tanggal Awal
    public void PilihTanggalAwal(int tglAwal) {
        Calendar calendar = Calendar.getInstance();
        datePicker = findViewById(R.id.datePicker);
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), tglAwal, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String tanggalYgDipilih = datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear();
                Intent intent = new Intent();
                intent.putExtra("tglYgDiPilih", tanggalYgDipilih);
                setResult(2, intent);
                finish();
            }
        });
    }


    //Pilih tanggal selesai
    public void PilihTanggalSelesai(int tglAwal, String tglFullAwal) {
        Calendar calendar = Calendar.getInstance();
        datePicker = findViewById(R.id.datePicker);

        String[] pecah = tglFullAwal.split("-");
        calendar.set(
                Integer.parseInt(pecah[2]),
                Integer.parseInt(pecah[1]),
                Integer.parseInt(pecah[0]) + 1
        );
        datePicker.setMinDate(calendar.getTimeInMillis());

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), tglAwal, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String TanggalYgDipilih = datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear();
                Intent intent = new Intent();
                intent.putExtra("tglYgDiPilih", TanggalYgDipilih);
                setResult(3, intent);
                finish();
            }
        });
    }
}
