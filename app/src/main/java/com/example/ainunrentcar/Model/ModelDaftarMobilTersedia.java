package com.example.ainunrentcar.Model;

public class ModelDaftarMobilTersedia {


    private String namaMitra;
    private String unitBrand;
    private String hargaPerhari;
    private String namaLengkapUnit;
    private String alamatMitra;
    private String urlImgUnit;

    public ModelDaftarMobilTersedia() {

    }

    public ModelDaftarMobilTersedia(String namaMitra, String namaLengkapUnit, String hargaPerhari, String alamatMitra, String urlImgUnit) {
        this.namaMitra = namaMitra;
        this.namaLengkapUnit = namaLengkapUnit;
        this.hargaPerhari = hargaPerhari;
        this.alamatMitra = alamatMitra;
        this.urlImgUnit = urlImgUnit;
    }

    public String getUrlImgUnit() {
        return urlImgUnit;
    }

    public void setUrlImgUnit(String urlImgUnit) {
        this.urlImgUnit = urlImgUnit;
    }


    public String getAlamatMitra() {
        return alamatMitra;
    }

    public void setAlamatMitra(String alamatMitra) {
        this.alamatMitra = alamatMitra;
    }

    public String getNamaLengkapUnit() {
        return namaLengkapUnit;
    }

    public void setNamaLengkapUnit(String namaLengkapUnit) {
        this.namaLengkapUnit = namaLengkapUnit;
    }

    public String getHargaPerhari() {
        return hargaPerhari;
    }

    public void setHargaPerhari(String hargaPerhari) {
        this.hargaPerhari = hargaPerhari;
    }


    public String getNamaMitra() {
        return namaMitra;
    }

    public void setNamaMitra(String namaMitra) {
        this.namaMitra = namaMitra;
    }

    public String getUnitBrand() {
        return unitBrand;
    }

    public void setUnitBrand(String unitBrand) {
        this.unitBrand = unitBrand;
    }

}