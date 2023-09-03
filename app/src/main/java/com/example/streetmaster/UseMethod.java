package com.example.streetmaster;


import static com.example.streetmaster.Home_Page_Activity.currentUserIn;
import static com.example.streetmaster.StudentAdapter.currentposition;
import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UseMethod {

    public void pickdate(TextView tv) {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    public void betweenDates(TextView tvMyDate,TextView tvBetween) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String mydate = tvMyDate.getText().toString();
        Date x;
        try {
            x = dateFormat.parse(mydate);
        } catch (
                ParseException e) {
            x = new Date();
        }

        Date y = new Date();

        long between = x.getTime() - y.getTime();
        between = TimeUnit.DAYS.convert(between, TimeUnit.MILLISECONDS);

        tvBetween.setText((between + 1) + "");
    }
/*

    tvTitle = view.findViewById(R.id.tvTitle);
    btnBack = view.findViewById(R.id.btnBack);
    btnStuAdd = view.findViewById(R.id.btnStuAdd);
        tvTitle.setText("Student Update");
        btnBack.setVisibility(View.GONE);
        btnStuAdd.setVisibility(View.GONE);



    EditText etStuFname = view.findViewById(R.id.etStuFname);
    EditText etStuLname = view.findViewById(R.id.etStuLname);
    EditText etStuId = view.findViewById(R.id.etStuId);
    EditText etStuMobile = view.findViewById(R.id.etStuMobile);
    EditText etStuAdress = view.findViewById(R.id.etStuAdress);
    EditText etStuMedicalDate = view.findViewById(R.id.etStuMedicalDate);
    EditText etStuBeginDate = view.findViewById(R.id.etStuBeginDate);
    EditText etStuTestDate = view.findViewById(R.id.etStuTestDate);
    EditText etStuEyesDate = view.findViewById(R.id.etStuEyesDate);
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    String docID = stuList.get(currentposition).getStdId();
        db.collection(currentUserIn + "-Students").document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            // student in position
            Student editSTD = stuList.get(currentposition);

            // fill the data
            etStuFname.setText(editSTD.getStdFname());
            String a = etStuFname.getText().toString();
            etStuFname.setEnabled(false);
            etStuLname.setText(editSTD.getStdLname());
            String b = etStuLname.getText().toString();
            etStuId.setText(editSTD.getStdId());
            String c = etStuId.getText().toString();
            etStuId.setEnabled(false);
            etStuMobile.setText(editSTD.getStdPhone());
            String d = etStuMobile.getText().toString();
            etStuAdress.setText(editSTD.getStdAdress());
            String e = etStuAdress.getText().toString();
            etStuMedicalDate.setText(editSTD.getStdMedicalConfirmationDate());
            String f = etStuMedicalDate.getText().toString();
            etStuBeginDate.setText(editSTD.getStdStartDate());
            String g = etStuBeginDate.getText().toString();
            etStuTestDate.setText(editSTD.getStdTestDate());
            String h = etStuTestDate.getText().toString();
            //etStuEyesDate.setText(editSTD.getStdEyesDate());
            // String i = etStuEyesDate.getText().toString();


            //  create new student after update
            Student estu = new Student(a, b, c, d, e, "a", f, g, h, "i", "yes");
            stuList.add(estu);

            //  delete old document from db
            db.collection(currentUserIn + "-Students").document(docID).delete();
            //   add new document to db
            db.collection(currentUserIn + "-Students").document(editSTD.getStdId()).set(estu).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    });
*/
}
/*
simple android list
<ListView
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:id="@android:id/list"
    xmlns:android="http://schemas.android.com/apk/res/android">

</ListView>

*/


/*      car info list
 <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rcvCarInfo"
            android:layout_width="368dp"
            android:layout_height="613dp"
            android:layout_marginStart="24dp"
            android:layout_marginTop="24dp"
            android:layout_marginEnd="24dp"
            android:layout_marginBottom="80dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
 */




 /*
 CAR ADAPTER FIX   *************************

 package com.example.streetmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {


   private Context context;
    private ArrayList<CarStatus> carsList;
    public CarAdapter(FragmentActivity context, ArrayList<CarStatus> carsList) {
        this.carsList = carsList;
        this.context = context;
    }
    public CarAdapter(ArrayList<CarStatus> carsList) {
        this.carsList = carsList;

    }

    @NonNull
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_in_list,parent,false);

        CarViewHolder holder = new CarViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {

        CarStatus carSt = carsList.get(position);

        holder.tvModel.setText(carSt.getModel());
        holder.tvNumber.setText(carSt.getNumber());
        holder.tvYear.setText(carSt.getYear());
        holder.tvKm.setText(carSt.getKm());
        holder.tvExLicense.setText(carSt.getExLicense());
        holder.tvExInsurance.setText(carSt.getExInsurance());
        holder.tvTransmission.setText(carSt.getTransmission());
        holder.tvSchool.setText(carSt.getSchool());
        // between 2 dates in days
        betweenDates(holder.tvExLicense,holder.tvLicenseEnd);
        betweenDates(holder.tvExInsurance,holder.tvInsuranceEnd);

    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder  {

        TextView tvModel,tvNumber,tvYear,tvKm,tvExLicense,tvExInsurance,tvTransmission,tvSchool;
        TextView tvLicenseEnd,tvInsuranceEnd;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);

            tvModel=itemView.findViewById(R.id.etModel);
            tvNumber=itemView.findViewById(R.id.etNumber);
            tvYear=itemView.findViewById(R.id.etYear);
            tvKm=itemView.findViewById(R.id.etKm);
            tvExLicense=itemView.findViewById(R.id.etExLicense);
            tvExInsurance=itemView.findViewById(R.id.etExInsurance);
            tvTransmission=itemView.findViewById(R.id.etTransmission);
            tvSchool=itemView.findViewById(R.id.etSchool);
            tvLicenseEnd=itemView.findViewById(R.id.tvLicenseEnd);
            tvInsuranceEnd=itemView.findViewById(R.id.tvInsuranceEnd);

        }
    }
    private void betweenDates(TextView tvMyDate,TextView tvBetween) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String mydate = tvMyDate.getText().toString();
        Date x;
        try {
            x = dateFormat.parse(mydate);
        } catch (
                ParseException e) {
            x = new Date();
        }

        Date y = new Date();

        long between = x.getTime() - y.getTime();
        between = TimeUnit.DAYS.convert(between, TimeUnit.MILLISECONDS);

        tvBetween.setText((between + 1) + "Days left");
    }
}


  */