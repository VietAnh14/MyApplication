package com.example.crApp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.crApp.R;

public class PatientItem extends ConstraintLayout {
    public PatientItem(Context context, AttributeSet attrs, String name) {
        super(context, attrs);
        inflate(context, R.layout.patient_item, this);
        ((TextView)findViewById(R.id.patientName)).setText(name);
    }
}
