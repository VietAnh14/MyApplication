package com.example.crApp.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.crApp.R;
import com.example.crApp.data.ApiHelper;
import com.example.crApp.data.PrefHelper;
import com.example.crApp.ui.dialog.ConfirmPrescriptionDialogFragment;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CallPatientActivity extends BaseActivity {
    private RecyclerView patientRecyclerView;
    private Button btnLogout;
    private Button buttonNextPatient;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_patient);
        setupView();
    }

    public void setUpRecyclerView() {
        showLoading(null);
        patientRecyclerView = findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setHasFixedSize(true);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PatientAdapter patientAdapter = new PatientAdapter();
        patientRecyclerView.setAdapter(patientAdapter);
        // TODO: SET ADAPTER
        compositeDisposable.add(
                ApiHelper.getListPatientByTable(tableNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    Log.d(TAG, "setUpRecyclerView: code: " + result.getCode());
                                    patientAdapter.setPatients(result.getResult());
                                    // save some memory
                                    compositeDisposable.clear();
                                    loadingDialogFragment.dismiss();
                                },
                                err -> {
                                    handleError(err);
                                    loadingDialogFragment.dismiss();
                                }
                        )
        );
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CallPatientActivity.class);
    }

    public void setupTableNumber() {
        PrefHelper prefHelper = PrefHelper.getInstance(getApplicationContext());
        TextView tableNumberTv = findViewById(R.id.tableNumber);
        tableNumber = prefHelper.getTableNumber();
        tableNumberTv.setText(String.valueOf(tableNumber));
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

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    // Single will auto dispose when on complete or on error is call, not calling doOnDispose
    // doOnDispose only being called when the subscription is disposed before the action is finished
    // Should clear composite disposable to save some memory
}
