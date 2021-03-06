package com.example.ainunrentcar.Service;

public class BaseUrl {
    public String baseUrl = "http://192.168.1.10/ainun-rent/";
    public String subUrlImageUnit = "public/assets/img/gambar_unit/";
    public String subUrlOffersPromo = "api/v1/offers";
    public String subUrlImageOffersPromo = "public/assets/img/gambar_promo/";

    public String urlFilterGroupByUnit(String tglStart, String tglSelesai) {
        return baseUrl + "api/v1/find_armada/group_by_varian/" + tglStart + "/" + tglSelesai;
    }

    public String urlHasilPencarianArmada(String tglStart, String tglSelesai, String sortBy, String unitGroup, String lainnya) {
        return baseUrl + "api/v1/find_armada/" + tglStart + "/" + tglSelesai + "/" + sortBy + "/" + unitGroup + "/" + lainnya;
    }

    public String urlGaleriUnit(String namaFile) {
        return baseUrl + "public/assets/img/gambar_unit/" + namaFile;
    }

    public String detailUnit(String idUnit) {
        return baseUrl + "api/v1/find_armada/detail/" + idUnit;
    }
}

