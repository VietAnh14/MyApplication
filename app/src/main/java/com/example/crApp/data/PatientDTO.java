package com.example.crApp.data;

import com.google.gson.annotations.SerializedName;

public class PatientDTO {
    private String id;
    @SerializedName("tekmedi_uid")
    private String tekmediUid;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("first_name")
    private String firstName;
    private Integer gender;
    private String birthday;
    @SerializedName("birthday_year_only")
    private Boolean birthdayYearOnly;
    private String code;

    public PatientDTO(String id, String tekmediUid, String lastName, String firstName, Integer gender, String birthday, Boolean birthdayYearOnly, String code) {
        this.id = id;
        this.tekmediUid = tekmediUid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthday = birthday;
        this.birthdayYearOnly = birthdayYearOnly;
        this.code = code;
    }

    public PatientDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTekmediUid() {
        return tekmediUid;
    }

    public void setTekmediUid(String tekmediUid) {
        this.tekmediUid = tekmediUid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthDay) {
        this.birthday = birthDay;
    }

    public Boolean getBirthdayYearOnly() {
        return birthdayYearOnly;
    }

    public void setBirthdayYearOnly(Boolean birthDayYearOnly) {
        this.birthdayYearOnly = birthDayYearOnly;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
