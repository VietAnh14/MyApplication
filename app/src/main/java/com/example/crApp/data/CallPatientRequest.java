package com.example.crApp.data;

import com.example.crApp.utils.MyUtils;
import com.google.gson.annotations.SerializedName;

public class CallPatientRequest {
    @SerializedName("DeparmentCode")
    private String departmentCode;
    @SerializedName("Type")
    private String type;
    @SerializedName("Limit")
    private
    String limit = "1";
    @SerializedName("RequestedDate")
    private String requestDate = MyUtils.getDate();

    @SerializedName("TableCode")
    private int tableCode;

    public CallPatientRequest(String departmentCode, String type, String limit, String requestDate, int tableCode) {
        this.departmentCode = departmentCode;
        this.type = type;
        this.limit = limit;
        this.requestDate = requestDate;
        this.tableCode = tableCode;
    }

    public CallPatientRequest() {
    }

    public int getTableCode() {
        return tableCode;
    }

    public void setTableCode(int tableCode) {
        this.tableCode = tableCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
