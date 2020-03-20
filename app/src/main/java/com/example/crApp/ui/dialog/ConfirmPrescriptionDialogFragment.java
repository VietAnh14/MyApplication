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

import com.example.crApp.R;
import com.example.crApp.data.PrefHelper;

import java.util.Objects;

public class ConfirmPrescriptionDialogFragment extends DialogFragment {
    private OnButtonConfirmClicked listener;
    private Button btnConfirm;
    private EditText txtConfirm;


    public ConfirmPrescriptionDialogFragment() {
        // not public instance
    }

//    public static ConfirmPrescriptionDialogFragment getInstance(boolean isNormalPatient) {
//        ConfirmPrescriptionDialogFragment dialog = new ConfirmPrescriptionDialogFragment();
//        Bundle args = new Bundle();
//        args.putBoolean(NORMAL_KEY, isNormalPatient);
//        dialog.setArguments(args);
//        return dialog;
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
        txtConfirm = view.findViewById(R.id.confirmPrescriptionEditText);
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        boolean isNormal = PrefHelper.getInstance(getContext()).getIsNormal();
        if (isNormal) {
            btnConfirm.setBackgroundColor(getResources().getColor(R.color.skyBlue));
            dialogTitle.setBackgroundColor(getResources().getColor(R.color.skyBlue));
        }
    }

    public void setOnConfirmClickListener(OnButtonConfirmClicked listener) {
        this.listener = listener;
        btnConfirm.setOnClickListener(v -> listener.onClick(txtConfirm.getText().toString()));
    }

    interface OnButtonConfirmClicked {
        void onClick(String value);
    }
}
