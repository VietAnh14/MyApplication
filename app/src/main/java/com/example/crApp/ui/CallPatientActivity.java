package com.example.crApp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.crApp.MainActivity;
import com.example.crApp.R;
import com.example.crApp.data.PrefHelper;
import com.example.crApp.ui.dialog.ConfirmPrescriptionDialogFragment;

import java.util.ArrayList;

public class CallPatientActivity extends BaseActivity {
    private RecyclerView patientRecyclerView;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_patient);
        setupView();
    }

    public void setUpRecyclerView() {
        patientRecyclerView = findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setHasFixedSize(true);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: SET ADAPTER
        ArrayList<String> names = new ArrayList<>();
        names.add("VIET ANH");
        names.add("VINH");
        names.add("TUAN ANH");
        names.add("VIET ANH");
        names.add("VINH");
        names.add("TUAN ANH");
        patientRecyclerView.setAdapter(new PatientAdapter(names));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CallPatientActivity.class);
    }

    public void setupTableNumber() {
        PrefHelper prefHelper = PrefHelper.getInstance(getApplicationContext());
        TextView tableNumber = findViewById(R.id.tableNumber);
        tableNumber.setText(String.valueOf(prefHelper.getTableNumber()));
    }

    public void setUpConfirmBtn() {
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            ConfirmPrescriptionDialogFragment confirmDialog = new ConfirmPrescriptionDialogFragment();
            confirmDialog.show(getSupportFragmentManager(), null);
//            confirmDialog.setOnConfirmClickListener(value -> );
        });
    }

    public void setupColor() {
        boolean isNormal = PrefHelper.getInstance(this).getIsNormal();
        if (isNormal) {
            findViewById(R.id.dummyBg).setBackgroundColor(getResources().getColor(R.color.skyBlue));
            ((TextView) findViewById(R.id.priorityPatientTxt)).setText(R.string.benh_nhan);
            ((Button) findViewById(R.id.btnNextPatient)).setBackgroundColor(getResources().getColor(R.color.skyBlue));
        }
    }

    public void setupView() {
        setupColor();
        setupTableNumber();
        setUpRecyclerView();
        setUpConfirmBtn();
        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(v -> finish());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
