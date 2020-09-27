package com.example.ainunrentcar.Model;

public class ModelOffersSeeAll {
    public String judulPromo;
    public String deskripsiPromo;
    public String gambarPromo;
    public String idOffer;

    public ModelOffersSeeAll() {

    }

    public ModelOffersSeeAll(String judulPromo, String deskripsiPromo, String gambarPromo) {
        this.judulPromo = judulPromo;
        this.deskripsiPromo = deskripsiPromo;
        this.gambarPromo = gambarPromo;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public String getJudulPromo() {
        return judulPromo;
    }

    public void setJudulPromo(String judulPromo) {
        this.judulPromo = judulPromo;
    }

    public String getDeskripsiPromo() {
        return deskripsiPromo;
    }

    public void setDeskripsiPromo(String deskripsiPromo) {
        this.deskripsiPromo = deskripsiPromo;
    }

    public String getGambarPromo() {
        return gambarPromo;
    }

    public void setGambarPromo(String gambarPromo) {
        this.gambarPromo = gambarPromo;
    }

}