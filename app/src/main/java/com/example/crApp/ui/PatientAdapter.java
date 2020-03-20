package com.example.crApp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crApp.R;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    // TODO: Change data type
    private List<String> listPatient;

    public PatientAdapter(List<String> listPatient) {
        this.listPatient = listPatient;
    }

    public void setPatients(List<String> listPatient) {
        this.listPatient = listPatient;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        // TODO: implement bind data to view item
        holder.patientName.setText(listPatient.get(position));
    }

    @Override
    public int getItemCount() {
        return listPatient == null ? 0 : listPatient.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        public TextView patientName;
        public TextView patientDes;
        public TextView patientPhoneNumber;
        public TextView patientCountNumber;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            patientDes = itemView.findViewById(R.id.txtPatientDes);
            patientPhoneNumber = itemView.findViewById(R.id.patientPhoneNumber);
            patientCountNumber = itemView.findViewById(R.id.patientCountNumber);
        }
    }
}
