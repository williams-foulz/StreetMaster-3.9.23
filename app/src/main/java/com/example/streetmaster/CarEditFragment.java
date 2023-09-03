package com.example.streetmaster;





import static com.example.streetmaster.CarAdapter.pos;
import static com.example.streetmaster.CarInfoFragment.carsListInfo;

import static com.example.streetmaster.Home_Page_Activity.currentUserIn;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarEditFragment newInstance(String param1, String param2) {
        CarEditFragment fragment = new CarEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    TextView tvSave;
    EditText etModel, etNumber, etYear, etKm, etSchool;

    TextView etExLicense, etExInsurance;
    Button btnSave, btnCal1, btnCal2;
    ImageView btnBack;
    Switch btnTransmission;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_add, container, false);


        tvSave = view.findViewById(R.id.tvSave);
        etModel = view.findViewById(R.id.tvModel);
        etNumber = view.findViewById(R.id.tvNumber);
        etYear = view.findViewById(R.id.tvYear);
        etKm = view.findViewById(R.id.tvKm);
        etExLicense = view.findViewById(R.id.tvExLicense);
        etExInsurance = view.findViewById(R.id.tvExInsurance);
        btnTransmission = view.findViewById(R.id.btnTransmission);
        etSchool = view.findViewById(R.id.tvSchool);
        btnCal1 = view.findViewById(R.id.btnCal1);
        btnCal2 = view.findViewById(R.id.btnCal2);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fts = getFragmentManager().beginTransaction();
                fts.replace(R.id.frmContent, new CarInfoFragment());
                fts.addToBackStack(null);
                fts.commit();
            }
        });

        // data cant be changed
        etModel.setEnabled(false);
        etNumber.setEnabled(false);
        etYear.setEnabled(false);
        btnTransmission.setEnabled(false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // get the document id

        String docID = carsListInfo.get(pos).getNumber();

                    CarStatus thisCar = carsListInfo.get(pos);
                    etModel.setText(thisCar.getModel());
                    etNumber.setText(thisCar.getNumber());
                    etYear.setText(thisCar.getYear());
                    etKm.setText(thisCar.getKm());
                    etExLicense.setText(thisCar.getExLicense());
                    etExInsurance.setText(thisCar.getExInsurance());
                    etSchool.setText(thisCar.getSchool());


        //calendar pick date
        btnCal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(etExLicense);
            }
        });
        btnCal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(etExInsurance);
            }
        });

        // button save --> update
        tvSave.setText("Update");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String model = etModel.getText().toString();
                String number = etNumber.getText().toString();
                String year = etYear.getText().toString();
                String km = etKm.getText().toString();
                String exl = etExLicense.getText().toString();
                String exi = etExInsurance.getText().toString();
                String school = etSchool.getText().toString();
                String transmission = btnTransmission.getText().toString();
                CarStatus cs = new CarStatus(model, number, year, km, exl, exi, transmission, school);


                db.getInstance().collection(currentUserIn + "-car").document(docID).set(cs).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "New Car Added", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fts = getFragmentManager().beginTransaction();
                            fts.replace(R.id.frmContent, new CarInfoFragment());
                            fts.addToBackStack(null);
                            fts.commit();
                        } else {
                            Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return view;
    }

    private void pickDate(TextView tv) {
        android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
        int month = cal.get(android.icu.util.Calendar.MONTH);
        int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);
        int year = cal.get(android.icu.util.Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText(dayOfMonth + "/" + month + "/" + year);

            }
        }, year, month, day);
        datePickerDialog.show();

    }
}
