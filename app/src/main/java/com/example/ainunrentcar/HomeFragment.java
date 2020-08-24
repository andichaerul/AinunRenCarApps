package com.example.ainunrentcar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private TextView resultTanggalAwal;
    private TextView resultTanggalSelesai;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @RequiresApi(api = Build.VERSION_CODES.N)
    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Disini Inisialisasi View
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        resultTanggalAwal = (TextView) root.findViewById(R.id.tglAwal);
        resultTanggalSelesai = (TextView) root.findViewById(R.id.tglSelesai);

        defaultValue();

        LinearLayout tanggalAwal = (LinearLayout) root.findViewById(R.id.tanggalAwal);
        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanDatePicker("awal");
            }
        });

        LinearLayout tanggalSelesai = (LinearLayout) root.findViewById(R.id.tanggalSelesai);
        tanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanDatePicker("selesai");
            }
        });
        return root;
    }

    // Untuk mengset value tgl awal dan tgl selesai pada saat aplikasi awal dibuka
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void defaultValue() {
        Calendar currentTime = Calendar.getInstance();
        resultTanggalAwal.setText(dateFormat.format(currentTime.getTime()));

        currentTime.add(Calendar.DATE,1);
        resultTanggalSelesai.setText(dateFormat.format(currentTime.getTime()));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void tampilkanDatePicker(final String awalOrSelesai) {
        Calendar newCalendar = Calendar.getInstance();
        int setDate = 1;
        if (awalOrSelesai.equals("awal")) {
            String[] tglAwalArr = resultTanggalAwal.getText().toString().split("-");
            setDate = Integer.parseInt(tglAwalArr[0]);

            //Set minDate
            newCalendar.add(Calendar.YEAR, 0);
        } else {
            String[] tglSelesaiArr = resultTanggalSelesai.getText().toString().split("-");
            setDate = Integer.parseInt(tglSelesaiArr[0]);

            //SetMinDate
            String[] dariTanggalAwal = resultTanggalAwal.getText().toString().split("-");
            int mm = Integer.parseInt(dariTanggalAwal[1]);
            int dd = Integer.parseInt(dariTanggalAwal[0]);
            int yyyy = Integer.parseInt(dariTanggalAwal[2]);
            newCalendar.set(Calendar.MONTH, mm);
            newCalendar.set(Calendar.DATE, dd);
            newCalendar.set(Calendar.YEAR, yyyy);
            newCalendar.add(Calendar.DATE, 1);
        }

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (awalOrSelesai.equals("awal")) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    resultTanggalAwal.setText(dateFormat.format(newDate.getTime()));
                    setResultTanggalSelesai();
                } else {
                    Calendar newDate1 = Calendar.getInstance();
                    newDate1.set(year, monthOfYear, dayOfMonth);
                    resultTanggalSelesai.setText(dateFormat.format(newDate1.getTime()));
                }
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), setDate);
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setResultTanggalSelesai() {
        String[] dariTanggalAwal = resultTanggalAwal.getText().toString().split("-");
        int mm = Integer.parseInt(dariTanggalAwal[1]);
        int dd = Integer.parseInt(dariTanggalAwal[0]) + 1;
        int yyyy = Integer.parseInt(dariTanggalAwal[2]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, mm);
        cal.set(Calendar.DATE, dd);
        cal.set(Calendar.YEAR, yyyy);
        Date date = cal.getTime();
        String setValue = dateFormat.format(date);
        resultTanggalSelesai.setText(setValue);
    }

}
