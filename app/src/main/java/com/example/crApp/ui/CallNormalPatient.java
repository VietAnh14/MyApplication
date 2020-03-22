package com.example.crApp.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crApp.R;
import com.example.crApp.data.PrefHelper;

import java.util.ArrayList;

public class CallNormalPatient extends AppCompatActivity {
    private RecyclerView patientRecyclerView;
    private Button btnLogout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_patient);
        setupView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setUpRecyclerView();
        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(v -> finish());
//        setUpList();
        setupTableNumber();
    }

    private void setupView() {
        findViewById(R.id.dummyBg).setBackgroundColor(getResources().getColor(R.color.skyBlue));
        ((TextView) findViewById(R.id.priorityPatientTxt)).setText(R.string.benh_nhan);
        ((Button) findViewById(R.id.btnNextPatient)).setBackgroundColor(getResources().getColor(R.color.skyBlue));
    }

    public void setUpRecyclerView() {
        patientRecyclerView = findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setHasFixedSize(true);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ArrayList<String> names = new ArrayList<>();
//        names.add("VIET ANH");
//        names.add("VINH");
//        names.add("TUAN ANH");
//        names.add("VIET ANH");
//        names.add("VINH");
//        names.add("TUAN ANH");
//        // TODO: SET ADAPTER
//        patientRecyclerView.setAdapter(new PatientAdapter(names));
    }

    public void setupTableNumber() {
        PrefHelper prefHelper = PrefHelper.getInstance(this);
        TextView tableNumber = findViewById(R.id.tableNumber);
        tableNumber.setText(String.valueOf(prefHelper.getTableNumber()));
    }

//    public void setUpList() {
//        LinearLayout listView = findViewById(R.id.listPatient);
//        ArrayList<String> names = new ArrayList<>();
//        names.add("VIET ANH");
//        names.add("VINH");
//        names.add("TUAN ANH");
//        names.add("VIET ANH");
//        names.add("VINH");
//        names.add("TUAN ANH");
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
//        lp.weight = 1;
//        for (String name : names) {
//            listView.addView(new PatientItem(this, null, name), lp);
//        }
//    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CallNormalPatient.class);
    }
}
