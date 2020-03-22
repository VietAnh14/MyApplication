package com.example.crApp.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dolphinsolutionsvn.dualiutils.DualCardUtils;
import com.dolphinsolutionsvn.dualiutils.Interface.CardInteractionInterface;
import com.example.crApp.R;
import com.example.crApp.data.ApiHelper;
import com.example.crApp.data.CallPatientRequest;
import com.example.crApp.data.PatientAndQueue;
import com.example.crApp.data.PrefHelper;
import com.example.crApp.data.VerifyPatientRequest;
import com.example.crApp.ui.dialog.LoadingDialogFragment;
import com.example.crApp.utils.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CallPatientActivity extends BaseActivity implements CardInteractionInterface, LoadingDialogFragment.DialogVisibilityCallBack {
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

    // This block of code I copy
    public static final String DEVICE_ID = "deviceid";
    public static final String MOCK_ID = "791901000771";
    private static final String BANK_CARD_TYPE = "2";
    private static final String TEKMEDI_CARD_TYPE = "1";

    public static final String FIRST_USE = "firstuse";


    private static final String MIFARE_KEY_A = "6C6564616E67";
    private static final String DEFAULT_MIFARE_KEY_A = "FFFFFFFFFFFF";
    private static final String DEFAULT_MIFARE_KEY_B = "FFFFFFFFFFFF";

    private static final String ACCESS_BIT = "FF078069";
    private static final int DATA_BLOCK = 14;
    private static final int DATA_SECTOR_TRAILER_BLOCK = 15;
    public static String ACTION_REFRESH = "refresh";
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private DualCardUtils dualCardUtils;
    private String cardId;
    private boolean isDialogShowing = false;

    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (dualCardUtils.connectDevice()) {
                Log.d("startCardDetect", "startCardDetectBroad");
                dualCardUtils.startCardDetect(CallPatientActivity.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_patient);
        setupView();

        dualCardUtils = new DualCardUtils();
        dualCardUtils.initialize(this);
        loadingDialogFragment.setCallBack(this);
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
                                    compositeDisposable.clear();
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

    public void verifyPatient(String patientCode) {
        showLoading("Đang xác nhận ");
        VerifyPatientRequest request = new VerifyPatientRequest();
        request.setDepartmentCode("THUOCBHYT");
        request.setPatientCode(patientCode);
        request.setRequestedDate(Utils.getDate());
        compositeDisposable.add(
                ApiHelper.verifyPatient(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    PatientAdapter patientAdapter = (PatientAdapter) patientRecyclerView.getAdapter();
                                    patientAdapter.removePatient(result.getResult().getPatientCode());
                                    loadingDialogFragment.dismiss();
                                    compositeDisposable.clear();
                                },
                                thr -> {
                                    handleError(thr);
                                    loadingDialogFragment.dismiss();
                                    compositeDisposable.clear();
                                }
                        )
        );
    }

    private void getPatientCodeAndVerify(String tekmediCardNumber) {
        showLoading("Đang xác nhận ");
        compositeDisposable.add(
                ApiHelper.getPatientCodeByTekmediNumber(tekmediCardNumber)
                        .subscribeOn(Schedulers.io())
                        .flatMap(patient -> {
                            Log.d(TAG, "getPatientCodeAndVerify: verify: " + Thread.currentThread().getName());
                            Log.d(TAG, "getPatientCodeAndVerify: code: " + patient.getResult().getCode());
                            VerifyPatientRequest request = new VerifyPatientRequest();
                            request.setDepartmentCode("THUOCBHYT");
                            request.setPatientCode(patient.getResult().getCode());
                            request.setRequestedDate(Utils.getDate());
                            return ApiHelper.verifyPatient(request);
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    PatientAdapter patientAdapter = (PatientAdapter) patientRecyclerView.getAdapter();
                                    patientAdapter.removePatient(result.getResult().getPatientCode());
                                    loadingDialogFragment.dismiss();
                                    compositeDisposable.clear();
                                }, throwable -> {
                                    handleError(throwable);
                                    loadingDialogFragment.dismiss();
                                    compositeDisposable.clear();
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
        unregisterReceiver(mUsbReceiver);
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onCardRecognized() {
        if (!loadingDialogFragment.isVisible()) {
            String tekmediNumber = getTekmediNumber();
            if (tekmediNumber == null || tekmediNumber.isEmpty()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        errorDialog.setError("Không nhận được thông tin thẻ, vui lòng thử lại!");
//                        errorDialog.show();
                        Toast.makeText(CallPatientActivity.this, "Không nhận được thông tin thẻ, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            cardId = tekmediNumber;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getPatientCodeAndVerify(cardId);
                }
            });

        }
    }

    // Too tired to understand this
    String barcode = "";
    Timer timer;
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (isDialogShowing) {
            if (e.getAction() == KeyEvent.ACTION_DOWN
                    && e.getKeyCode() != KeyEvent.KEYCODE_ENTER) { //Not Adding ENTER_KEY to barcode String
                char pressedKey = (char) e.getUnicodeChar();
                if (Character.isAlphabetic(pressedKey) || Character.isDigit(pressedKey) || pressedKey == '|' || pressedKey == '/' || pressedKey == '-' || pressedKey == '$') {
                    barcode += pressedKey;
                    if (timer != null) {
                        timer.cancel();
                    }

                    timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (Character.isDigit(barcode.charAt(0))) {
                                Log.i("TAG", "Barcode Read: " + barcode);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        verifyPatient("079048." + barcode);
                                    }
                                });
                            }
                            barcode = "";

                        }
                    };
                    timer.schedule(timerTask, 100);
                }
            }
            if (e.getAction() == KeyEvent.ACTION_DOWN
                    && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                Log.i("TAG", "Barcode Read: " + barcode);

            }
            return false;
        } else {
            super.dispatchKeyEvent(e);
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
    }

    public String getTekmediNumber() {
        dualCardUtils.anticol();
        if (dualCardUtils.authMifare(MIFARE_KEY_A, DATA_BLOCK, DualCardUtils.KeyType.TYPE_A)) {
            String data = dualCardUtils.readMifare(DATA_BLOCK);
            if (!data.isEmpty()) {
                return data.substring(55, 67);
            }
        }
        return "";
//        return MOCK_ID;
    }

    @Override
    public void onShowDialog() {
        dualCardUtils.stopCardDetect();
        isDialogShowing = true;
    }

    @Override
    public void onDismissDialog() {
        dualCardUtils.startCardDetect(CallPatientActivity.this);
        isDialogShowing = false;
    }

    // Single will auto dispose when on complete or on error is call, not calling doOnDispose
    // doOnDispose only being called when the subscription is disposed before the action is finished
    // Should clear composite disposable to save some memory
}
