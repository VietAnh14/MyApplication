package com.example.crApp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.error.ANError;
import com.example.crApp.data.ApiError;
import com.example.crApp.ui.dialog.ConfirmPrescriptionDialogFragment;
import com.example.crApp.ui.dialog.LoadingDialogFragment;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected LoadingDialogFragment loadingDialogFragment;
    protected ConfirmPrescriptionDialogFragment messageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialogFragment = new LoadingDialogFragment();
        messageDialog = new ConfirmPrescriptionDialogFragment();
    }

    public void showLoading(@Nullable String message) {
        if (message != null) {
            loadingDialogFragment = LoadingDialogFragment.getInstance(message);
        } else {
            loadingDialogFragment = new LoadingDialogFragment();
        }
        loadingDialogFragment.show(getSupportFragmentManager(), null);
    }

    public void dismissLoadingDialog() {
        loadingDialogFragment.dismiss();
    }

    public void dismissMessageDialog() {
        messageDialog.dismiss();
    }

    public void showMessageDialog(@Nullable String message, @Nullable ConfirmPrescriptionDialogFragment.OnButtonConfirmClicked callBack) {
        if (message != null) {
            messageDialog = ConfirmPrescriptionDialogFragment.getInstance(message);
            messageDialog.setOnConfirmClickListener(callBack);
        } else {
            messageDialog = new ConfirmPrescriptionDialogFragment();
            messageDialog.setOnConfirmClickListener(callBack);
        }
        messageDialog.show(getSupportFragmentManager(), null);
    }
}
