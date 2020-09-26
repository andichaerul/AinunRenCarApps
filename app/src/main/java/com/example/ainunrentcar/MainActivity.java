package com.example.ainunrentcar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

//import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        menuBottom();
    }

    public void initViews() {
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void menuBottom() {
        fragmentBottomMenu(new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                }
                switch (item.getItemId()) {
                    case R.id.pemesanan:
                        fragment = new DaftarPemesananBelumLoginFragment();
                }
                switch (item.getItemId()) {
                    case R.id.bantuan:
                        fragment = new HomeFragment();
                }
                switch (item.getItemId()) {
                    case R.id.profile:
                        fragment = new ProfilFragment();
                }
                return fragmentBottomMenu(fragment);
            }
        });
    }


    private boolean fragmentBottomMenu(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}


