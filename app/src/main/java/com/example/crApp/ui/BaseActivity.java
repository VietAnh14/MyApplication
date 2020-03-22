package com.example.crApp.ui;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.example.crApp.ui.dialog.LoadingDialogFragment;

public class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected LoadingDialogFragment loadingDialogFragment;

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
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.e(TAG, "onError errorDetail : " + err.getErrorDetail(), err);
            }
        } else {
            Log.e(TAG, "handleError: " + error.getMessage(), error);
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
}
