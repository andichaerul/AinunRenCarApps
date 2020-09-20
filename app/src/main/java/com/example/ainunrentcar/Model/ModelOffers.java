package com.example.ainunrentcar.Model;


import com.example.ainunrentcar.Service.BaseUrl;

public class ModelOffers {

    public String judul;
    public String deskripsi;
    public String urlGambar;
    public String idOffer;

    public ModelOffers() {

    }

    public ModelOffers(String judul, String deskripsi, String urlGambar, String idOffer) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.urlGambar = urlGambar;
        this.idOffer = idOffer;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }
}