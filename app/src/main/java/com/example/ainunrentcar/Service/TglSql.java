package com.example.ainunrentcar.Service;

public class TglSql {
    public String tglSql(String tanggal) {
        String[] tanggalArr = tanggal.split("-");
        return tanggalArr[2] + "-" + tanggalArr[1] + "-" + tanggalArr[0];
    }
}
