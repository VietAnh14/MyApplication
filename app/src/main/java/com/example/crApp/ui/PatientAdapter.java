package com.example.crApp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crApp.R;
import com.example.crApp.data.PatientAndQueue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    // TODO: Change data type
    private List<PatientAndQueue> listPatient;

    public PatientAdapter() {

    }

    public PatientAdapter(List<PatientAndQueue> listPatient) {
        this.listPatient = listPatient;
    }

    public void setPatients(List<PatientAndQueue> listPatient) {
        this.listPatient = listPatient;
        notifyDataSetChanged();
    }

    public void addPatient(PatientAndQueue patientAndQueue) {
        listPatient.add(patientAndQueue);
        notifyItemInserted(listPatient.size() - 1);
    }

    public void removePatient(String patientCode) {
        // Linked list
        int index = -1;
        for (PatientAndQueue patient : listPatient) {
            index++;
            if (patient.getQueue().getPatientCode().equals(patientCode)) {
                listPatient.remove(index);
                break;
            }
        }
        if (index >= 0) {
            notifyItemRemoved(index);
        }
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientAndQueue patientAndQueue = listPatient.get(position);
        String name = patientAndQueue.getPatient().getLastName() + " " + patientAndQueue.getPatient().getFirstName();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date birthday = format.parse(patientAndQueue.getPatient().getBirthday());
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday != null ? birthday : cal.getTime());
            int age = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            String des = patientAndQueue.getPatient().getGender() == 1 ? "Nam" : "Nữ";
            des +=  "/" + age + " tuổi";
            holder.patientDes.setText(des);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.patientCode.setText(patientAndQueue.getQueue().getPatientCode());
        holder.patientName.setText(name);
        String countNumber = patientAndQueue.getQueue().getNumber().toString();
        holder.patientCountNumber.setText(countNumber);
    }

    @Override
    public int getItemCount() {
        return listPatient == null ? 0 : listPatient.size();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        public TextView patientName;
        public TextView patientDes;
        public TextView patientCode;
        public TextView patientCountNumber;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientName);
            patientDes = itemView.findViewById(R.id.txtPatientDes);
            patientCode = itemView.findViewById(R.id.patientCode);
            patientCountNumber = itemView.findViewById(R.id.patientCountNumber);
        }
    }
}
