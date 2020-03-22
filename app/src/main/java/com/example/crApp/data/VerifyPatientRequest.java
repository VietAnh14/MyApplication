package com.example.crApp.data;

import com.google.gson.annotations.SerializedName;

public class VerifyPatientRequest{
    @SerializedName("DepartmentCode")
    String departmentCode;
    @SerializedName("PatientCode")
    String patientCode;
    @SerializedName("RequestedDate")
    String requestedDate;

    public VerifyPatientRequest() {
    }

    public VerifyPatientRequest(String departmentCode, String patientCode, String requestedDate) {
        this.departmentCode = departmentCode;
        this.patientCode = patientCode;
        this.requestedDate = requestedDate;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }
}
