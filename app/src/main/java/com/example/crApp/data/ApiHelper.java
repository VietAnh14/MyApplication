package com.example.crApp.data;

import com.rx2androidnetworking.Rx2AndroidNetworking;


import io.reactivex.Single;

public class ApiHelper {
    private static final String ENDPOINT_GET_PATIENT_BY_TABLE_NUMBER = "http://dev.choray.tekmedi.com/tek.btc.kiot/api/GenerateNumber/temp/getbytablecode";

    public static Single<PatientsGetByTableCodeResponse> getListPatientByTable(int table) {
        return Rx2AndroidNetworking.get(ENDPOINT_GET_PATIENT_BY_TABLE_NUMBER)
                .addQueryParameter("TableCode", String.valueOf(table))
                .addHeaders("accept", "*/*")
                .build()
                .getObjectSingle(PatientsGetByTableCodeResponse.class);
    }
}
