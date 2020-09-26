package com.example.ainunrentcar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarPemesananBelumLoginFragment extends Fragment {

    private Button tombolMasuk;

    public DaftarPemesananBelumLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daftar_pemesanan_belum_login, container, false);
        initViews(root);
        intentToLogin();
        return root;
    }

    private void initViews(View root) {
        tombolMasuk = root.findViewById(R.id.tombolMasuk);
    }

    private void intentToLogin() {
        tombolMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
