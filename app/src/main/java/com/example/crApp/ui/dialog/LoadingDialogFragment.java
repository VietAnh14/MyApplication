package com.example.crApp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.crApp.R;

import java.util.Objects;

public class LoadingDialogFragment extends DialogFragment {
    private TextView messageText;
    private static final String MESSAGE_KEY = "message";
    private DialogVisibilityCallBack callBack;

    public LoadingDialogFragment() {
        // default message
    }

    public void setCallBack(DialogVisibilityCallBack callBack) {
        this.callBack = callBack;
    }

    // To pass message only
    public static LoadingDialogFragment getInstance(@NonNull String message) {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        loadingDialogFragment.setArguments(args);
        return  loadingDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_loading, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageText = view.findViewById(R.id.loadingMessage);
        if (getArguments() != null) {
            String message = getArguments().getString(MESSAGE_KEY, "Lấy danh sách bệnh nhân");
            messageText.setText(message);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(false);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (callBack != null) {
            callBack.onShowDialog();
        }
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        if (callBack != null) {
            callBack.onDismissDialog();
        }
        super.dismiss();
    }

    public interface DialogVisibilityCallBack {
        void onShowDialog();
        void onDismissDialog();
    }
}
