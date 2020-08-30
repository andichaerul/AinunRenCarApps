package com.example.ainunrentcar.Model;


import android.widget.ImageView;

public class ModelDaftarMobilTersedia {

    private String nama;
    private String npm;
    private String nohp;
    private String urlGambar;

    public ModelDaftarMobilTersedia(String nama, String npm, String nohp, String urlGambar) {
        this.nama = nama;
        this.npm = npm;
        this.nohp = nohp;
        this.urlGambar = urlGambar;

    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}