package com.example.crApp.data;

import com.google.gson.annotations.SerializedName;

public class PatientAndQueue {
    PatientDTO patient;
    Queue queue;
    @SerializedName("priority_type")
    Integer priorityType;

    public PatientAndQueue(PatientDTO patient, Queue queue, Integer priorityType) {
        this.patient = patient;
        this.queue = queue;
        this.priorityType = priorityType;
    }

    public PatientAndQueue() {
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Integer getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(Integer priorityType) {
        this.priorityType = priorityType;
    }
}
