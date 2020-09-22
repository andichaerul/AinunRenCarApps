package com.example.ainunrentcar.Service;

public class BaseUrl {
    public String baseUrl = "http://192.168.1.8/ainun-rent/";
    public String subUrlImageUnit = "public/assets/img/gambar_unit/";
    public String subUrlOffersPromo = "api/v1/offers";
    public String subUrlImageOffersPromo = "public/assets/img/gambar_promo/";

    public String urlFilterGroupByUnit(String tglStart, String tglSelesai) {
        return baseUrl + "api/v1/find_armada/group_by_varian/" + tglStart + "/" + tglSelesai;
    }
}

