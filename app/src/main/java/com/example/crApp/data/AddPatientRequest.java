package com.example.crApp.data;

import com.google.gson.annotations.SerializedName;

public class AddPatientRequest {
    @SerializedName("PatientCode")
    String patientCode;
    @SerializedName("DepartmentCode")
    String departmentCode;
    @SerializedName("TableCode")
    String tableCode;
    @SerializedName("RequestedDate")
    String requestDate;

    public AddPatientRequest() {
    }

    public AddPatientRequest(String patientCode, String departmentCode, String tableCode, String requestDate) {
        this.patientCode = patientCode;
        this.departmentCode = departmentCode;
        this.tableCode = tableCode;
        this.requestDate = requestDate;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
