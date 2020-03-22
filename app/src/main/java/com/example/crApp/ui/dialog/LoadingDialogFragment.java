package com.example.crApp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.crApp.R;

public class LoadingDialogFragment extends DialogFragment {
    private TextView messageText;
    private static final String MESSAGE_KEY = "message";

    public LoadingDialogFragment() {
        // default message
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
}
