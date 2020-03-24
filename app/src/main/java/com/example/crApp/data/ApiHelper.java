package com.example.crApp.data;

import com.example.crApp.utils.MyUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;


import io.reactivex.Single;

public class ApiHelper {
    private static final String HOST = "http://dev.choray.tekmedi.com/tek.btc.kiot";
    private static final String ENDPOINT_GET_PATIENT_BY_TABLE_NUMBER = HOST + "/api/GenerateNumber/temp/getbytablecode";
    private static final String ENDPOINT_CALL_PATIENT = HOST + "/api/GenerateNumber/temp/call";
    private static final String ENDPOINT_VERIFY_PATIENT = HOST + "/api/GenerateNumber/temp/verify";
    private static final String ENDPOINT_GET_PATIENT_CODE_BY_TEKMEDI_CARD_NUMBER = HOST + "/api/Patient/number/{cardNumber}";
    private static final String ENDPOINT_ADD_PATIENT = HOST + "/api/GenerateNumber/temp/add";
    public static Single<PatientsGetByTableCodeResponse> getListPatientByTable(int table, int priority) {
        return Rx2AndroidNetworking.get(ENDPOINT_GET_PATIENT_BY_TABLE_NUMBER)
                .addQueryParameter("TableCode", String.valueOf(table))
                .addQueryParameter("Type", String.valueOf(priority))
                .addQueryParameter("RequestedDate", MyUtils.getDate())
                .addHeaders("accept", "*/*")
                .build()
                .getObjectSingle(PatientsGetByTableCodeResponse.class);
    }

    public static Single<PatientsGetByTableCodeResponse> callPatient(CallPatientRequest callPatientRequest) {
        return Rx2AndroidNetworking.get(ENDPOINT_CALL_PATIENT)
                .addQueryParameter(callPatientRequest)
                .addHeaders("accept", "*/*")
                .build()
                .getObjectSingle(PatientsGetByTableCodeResponse.class);
    }

    public static Single<VerifyPatientResponse> verifyPatient(VerifyPatientRequest request) {
        return Rx2AndroidNetworking.get(ENDPOINT_VERIFY_PATIENT)
                .addQueryParameter(request)
                .addHeaders("accept", "*/*")
                .build()
                .getObjectSingle(VerifyPatientResponse.class);
    }

    public static Single<PatientGetByTekmediNumberResponse> getPatientCodeByTekmediNumber(String cardNumber) {
        return Rx2AndroidNetworking.get(ENDPOINT_GET_PATIENT_CODE_BY_TEKMEDI_CARD_NUMBER)
                .addPathParameter("cardNumber", cardNumber)
                .addHeaders("accept", "*/*")
                .build()
                .getObjectSingle(PatientGetByTekmediNumberResponse.class);
    }

    public static Single<AddPatientResponse> addPatient(AddPatientRequest request) {
        return Rx2AndroidNetworking.post(ENDPOINT_ADD_PATIENT)
                .addApplicationJsonBody(request)
                .setContentType("application/json-patch+json")
                .build()
                .getObjectSingle(AddPatientResponse.class);
    }
}
