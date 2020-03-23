package com.example.crApp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    public void handleError(@NonNull Throwable error) {
        if (error instanceof ANError) {
            ANError err = (ANError) error;
            if (err.getErrorCode() != 0) {
                // received error from server
                // error.getErrorCode() - the error code from server
                // error.getErrorBody() - the error body from server
                // error.getErrorDetail() - just an error detail
                Log.d(TAG, "onError errorCode : " + err.getErrorCode());
                Log.d(TAG, "onError errorBody : " + err.getErrorBody());
                Log.d(TAG, "onError errorDetail : " + err.getErrorDetail());
                ApiError apiError = new Gson().fromJson(err.getErrorBody(), ApiError.class);
                Toast.makeText(this, apiError.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.e(TAG, "onError errorDetail : " + err.getErrorDetail(), err);
                Toast.makeText(this, err.getErrorDetail(), Toast.LENGTH_SHORT).show();

            }
        } else {
            Log.e(TAG, "handleError: " + error.getMessage(), error);
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Ơ failed mất rồi???", Toast.LENGTH_SHORT).show();
    }

    public void showLoading(@Nullable String message) {
        if (message != null) {
            loadingDialogFragment = LoadingDialogFragment.getInstance(message);
        } else {
            loadingDialogFragment = new LoadingDialogFragment();
        }
        loadingDialogFragment.show(getSupportFragmentManager(), null);
    }

    public void showMessageDialog(@Nullable String message) {
        if (message != null) {
            messageDialog = ConfirmPrescriptionDialogFragment.getInstance(message);
        } else {
            messageDialog = new ConfirmPrescriptionDialogFragment();
        }
    }
}
