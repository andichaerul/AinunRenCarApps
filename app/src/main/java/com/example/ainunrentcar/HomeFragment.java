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

        // Set value
//        tanggalAwal.setText(setValueTglAwal());
//        tanggalSelesai.setText(setValueTglSelesai());


//        tanggalAwal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), DatePickerActivity.class);
//
//                String tanggalUntuk = "pilihTanggalAwal";
//                i.putExtra("tanggalUntuk", tanggalUntuk);
//
//                String[] splitTglAwal = tanggalAwal.getText().toString().split("-");
//                i.putExtra("tglAwal", Integer.parseInt(splitTglAwal[0]));
//
//                startActivityForResult(i, 2);
//            }
//        });


//        tanggalSelesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), DatePickerActivity.class);
//
//                String tanggalUntuk = "pilihTanggalSelesai";
//                i.putExtra("tanggalUntuk", tanggalUntuk);
//
//
//                String[] splitTglAwal = tanggalSelesai.getText().toString().split("-");
//                i.putExtra("tglAwal", Integer.parseInt(splitTglAwal[0]));
//
//                String tA = tanggalAwal.getText().toString();
//                i.putExtra("tglFullAwal", tA);
//                startActivityForResult(i, 3);
//            }
//        });

        return root;
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
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                resultTanggalAwal.setText(dateFormatter.format(newDate.getTime()));
                if (awalOrSelesai.equals("awal")) {
                    setResultTanggalSelesai();
                }
            }
        },
                newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), setDate);
        datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }


//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public String setValueTglAwal() {
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String valueTglAwal = dateFormat.format(c);
//        return valueTglAwal;
//    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public String setValueTglSelesai() {
//        Date dt = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(dt);
//        c.add(Calendar.DATE, 1);
//        dt = c.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String valueTglSelesai = dateFormat.format(dt);
//        return valueTglSelesai;
//    }

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String setValue = dateFormat.format(date);
        resultTanggalSelesai.setText(setValue);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            String tglYgDiPilih = data.getStringExtra("tglYgDiPilih");
            Log.d("sasasasa", "sasasasa");
            TextView tanggalAwal = (TextView) getView().findViewById(R.id.tglAwal);
            tanggalAwal.setText(tglYgDiPilih);
        } else if (requestCode == 3) {
            String tglYgDiPilih = data.getStringExtra("tglYgDiPilih");
            TextView tanggalSelesai = (TextView) getView().findViewById(R.id.tglSelesai);
            tanggalSelesai.setText(tglYgDiPilih);
        }
    }
}
