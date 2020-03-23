package com.example.crApp.ui.dialog;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.crApp.R;
import com.example.crApp.data.PrefHelper;

import java.util.Objects;

public class ConfirmPrescriptionDialogFragment extends DialogFragment {
    private OnButtonConfirmClicked listener;
    private Button btnConfirm;
    private TextView messageText;
    private static final String MESSAGE_KEY = "MESSAGE_KEY";
    private LoadingDialogFragment.DialogVisibilityCallBack dialogVisibilityCallBack;
    public ConfirmPrescriptionDialogFragment() {
    }

    public static ConfirmPrescriptionDialogFragment getInstance(String message) {
        ConfirmPrescriptionDialogFragment dialog = new ConfirmPrescriptionDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        dialog.setArguments(args);
        return dialog;
    }

//    public void setMessage(String message) {
//        messageText.setText(message);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_confirm_prescription, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        messageText = view.findViewById(R.id.messageText);
        boolean isNormal = PrefHelper.getInstance(getContext()).getIsNormal();
        if (isNormal) {
            btnConfirm.setBackgroundColor(getResources().getColor(R.color.skyBlue));
            dialogTitle.setBackgroundColor(getResources().getColor(R.color.skyBlue));
        }
        if (getArguments() != null) {
            messageText.setText(getArguments().getString(MESSAGE_KEY, "Some things went wrong"));
        }

        if (listener != null) {
            btnConfirm.setOnClickListener(v -> {
                listener.onClick();
            });
        }
    }

    public void setOnConfirmClickListener(OnButtonConfirmClicked listener) {
        this.listener = listener;
    }

    public void setDialogVisibilityCallBack(LoadingDialogFragment.DialogVisibilityCallBack callBack) {
        this.dialogVisibilityCallBack = callBack;
    }

    public interface OnButtonConfirmClicked {
        void onClick();
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (dialogVisibilityCallBack != null) {
            dialogVisibilityCallBack.onShowDialog();
        }
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        if (dialogVisibilityCallBack != null) {
            dialogVisibilityCallBack.onDismissDialog();
        }
        super.dismiss();
    }
}
