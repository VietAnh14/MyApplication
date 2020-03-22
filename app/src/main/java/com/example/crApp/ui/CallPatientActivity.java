package com.example.crApp.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crApp.R;
import com.example.crApp.data.ApiHelper;
import com.example.crApp.data.CallPatientRequest;
import com.example.crApp.data.PatientAndQueue;
import com.example.crApp.data.PrefHelper;
import com.example.crApp.data.VerifyPatientRequest;
import com.example.crApp.ui.dialog.ConfirmPrescriptionDialogFragment;
import com.example.crApp.utils.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CallPatientActivity extends BaseActivity {
    private RecyclerView patientRecyclerView;
    private Button btnLogout;
    private Button buttonNextPatient;
    private TextView txtCurrentPatientNumber;
    private TextView txtCurrentPatientName;
    private TextView txtCurrentPatientDes;
    private TextView txtCurrentPatientCode;
    private EditText editTextPatientCode;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int tableNumber = PrefHelper.getInstance(this).getTableNumber();
    private final int type = PrefHelper.getInstance(this).getIsNormal() ? 0 : 1;

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
        compositeDisposable.add(
                ApiHelper.getListPatientByTable(tableNumber)
                        .subscribeOn(Schedulers.io())
                        .map(result -> {
                            // Check type
                            Log.d(TAG, "setUpRecyclerView: thread " + Thread.currentThread().getName());
                            LinkedList<PatientAndQueue> listPatient = new LinkedList<>();
                            for (PatientAndQueue patientAndQueue : result.getResult()) {
                                if (patientAndQueue.getQueue().getType() == type) {
                                    listPatient.add(patientAndQueue);
                                }
                            }
                            return listPatient;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    Log.d(TAG, "setUpRecyclerView: thread " + Thread.currentThread().getName());
                                    if (!result.isEmpty()) {
                                        patientAdapter.setPatients(result);
                                        setupCurrentPatientCall(result.get(result.size() - 1));
                                        // save some memory
                                    } else {
                                        // no more patients
                                        Toast.makeText(this, "Hiện chưa có bệnh nhân", Toast.LENGTH_SHORT).show();
                                    }
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
        TextView tableNumberTv = findViewById(R.id.tableNumber);
        tableNumberTv.setText(String.valueOf(tableNumber));
    }

//    public void setUpConfirmBtn() {
//        Button btnConfirm = findViewById(R.id.btnConfirm);
////        btnConfirm.setOnClickListener(v -> {
////            ConfirmPrescriptionDialogFragment confirmDialog = new ConfirmPrescriptionDialogFragment();
////            confirmDialog.show(getSupportFragmentManager(), null);
//////            confirmDialog.setOnConfirmClickListener(value -> );
////        });
//        btnConfirm.setOnClickListener(v -> {
//            verifyPatient();
//        });
//    }

    public void setupColor() {
        boolean isNormal = PrefHelper.getInstance(this).getIsNormal();
        if (isNormal) {
            findViewById(R.id.dummyBg).setBackgroundColor(getResources().getColor(R.color.skyBlue));
            ((TextView) findViewById(R.id.priorityPatientTxt)).setText(R.string.benh_nhan);
            buttonNextPatient.setBackgroundColor(getResources().getColor(R.color.skyBlue));
        }
    }

    public void setupView() {
        txtCurrentPatientCode = findViewById(R.id.patientCode);
        txtCurrentPatientDes = findViewById(R.id.txtPatientDes);
        txtCurrentPatientName = findViewById(R.id.txtPatientName);
        txtCurrentPatientNumber = findViewById(R.id.txtNumber);
        editTextPatientCode = findViewById(R.id.editTextPatientCode);
        buttonNextPatient = findViewById(R.id.btnNextPatient);
        btnLogout = findViewById(R.id.btnLogOut);

        setupColor();
        setupTableNumber();
        setUpRecyclerView();
//        setUpConfirmBtn();
        editTextPatientCode.requestFocus();
        btnLogout.setOnClickListener(v -> finish());
        buttonNextPatient.setOnClickListener(v -> callNextPatient());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void verifyPatient() {
        showLoading("Đang xác nhận ");
        VerifyPatientRequest request = new VerifyPatientRequest();
        request.setDepartmentCode("THUOCBHYT");
        request.setPatientCode(editTextPatientCode.getText().toString());
        request.setRequestedDate(Utils.getDate());
        ArrayList a = new ArrayList();
        compositeDisposable.add(
                ApiHelper.verifyPatient(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    PatientAdapter patientAdapter = (PatientAdapter) patientRecyclerView.getAdapter();
                                    patientAdapter.removePatient(result.getResult().getPatientCode());
                                    loadingDialogFragment.dismiss();
                                },
                                thr -> {
                                    handleError(thr);
                                    loadingDialogFragment.dismiss();
                                }
                        )
        );
    }


    private void callNextPatient() {
        showLoading("Đang gọi bệnh nhân....");
        CallPatientRequest request = new CallPatientRequest();
        request.setDepartmentCode("THUOCBHYT");
        request.setLimit("1");
        request.setType(String.valueOf(type));
        request.setRequestDate(Utils.getDate());
        compositeDisposable.add(
                ApiHelper.callPatient(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    Log.d(TAG, "callNextPatient: response code: " + result.getCode());
                                    if (result.getResult().isEmpty()) {
                                        Log.d(TAG, "callNextPatient: no more patient, go home bros");
                                        Toast.makeText(this, "Hiện chưa có bệnh nhân", Toast.LENGTH_SHORT).show();
                                    } else {
                                        PatientAdapter patientAdapter = (PatientAdapter) patientRecyclerView.getAdapter();
                                        patientAdapter.addPatient(result.getResult().get(0));
                                        setupCurrentPatientCall(result.getResult().get(0));
                                        Toast.makeText(this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialogFragment.dismiss();
                                    compositeDisposable.clear();
                                }, thr -> {
                                    handleError(thr);
                                    loadingDialogFragment.dismiss();
                                }
                        )
        );
    }

    public void setupCurrentPatientCall(PatientAndQueue patientAndQueue) {
        String name = patientAndQueue.getPatient().getLastName() + " " + patientAndQueue.getPatient().getFirstName();
        txtCurrentPatientName.setText(name);
        String number = patientAndQueue.getQueue().getNumber().toString();
        txtCurrentPatientNumber.setText(number);
        txtCurrentPatientCode.setText(patientAndQueue.getQueue().getPatientCode());
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date birthday = format.parse(patientAndQueue.getPatient().getBirthday());
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday != null ? birthday : cal.getTime());
            int age = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            String des = patientAndQueue.getPatient().getGender() == 1 ? "Nam" : "Nữ";
            des += "/" + age + " tuổi";
            txtCurrentPatientDes.setText(des);
        } catch (ParseException e) {
            // ignore :)
            e.printStackTrace();
        }
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
