package com.example.crApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.crApp.data.PatientAndQueue;
import com.example.crApp.data.PrefHelper;
import com.example.crApp.ui.BaseActivity;
import com.example.crApp.ui.CallPatientActivity;
import com.example.crApp.utils.MyUtils;

import java.util.List;

public class MainActivity extends BaseActivity{
    private Button btnStart;
    private CheckBox checkBoxPriority;
    private PrefHelper prefHelper;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onResume: date" + MyUtils.getDate());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setupView();
    }

    public void setupView() {
        findView();
        setupBtnStart();
        setupSpinner();

        String ipAddress = MyUtils.getIPAddress(true);
        ipAddress = ipAddress.equals("") ? getString(R.string.connection_error) : ipAddress;
        ((TextView) findViewById(R.id.txtIp)).setText(ipAddress);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> tableAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.pillTable, android.R.layout.simple_spinner_dropdown_item);
        tableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(tableAdapter);
        spinner.setSelection(prefHelper.getTableNumber() - 1);
    }

    private void findView() {
        prefHelper = PrefHelper.getInstance(this);
        spinner = findViewById(R.id.numberSpinner);
        checkBoxPriority = findViewById(R.id.priorityCheckBox);
        btnStart = findViewById(R.id.btnStart);
    }

    private void setupBtnStart() {
        btnStart.setOnClickListener(v -> {
            prefHelper.saveTableNumber(Integer.parseInt(spinner.getSelectedItem().toString()));
            prefHelper.setIsNormal(!checkBoxPriority.isChecked());
            Log.d(TAG, "save tableNumber: " + spinner.getSelectedItem().toString());
//            Intent intent = checkBoxPriority.isChecked() ? CallPatientActivity.getIntent(this) : CallNormalPatient.getIntent(this);
            Intent intent = CallPatientActivity.getIntent(this);
            startActivity(intent);
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
