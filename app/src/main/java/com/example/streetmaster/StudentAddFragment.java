package com.example.streetmaster;


import static com.example.streetmaster.Home_Page_Activity.currentUserIn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentAddFragment newInstance(String param1, String param2) {
        StudentAddFragment fragment = new StudentAddFragment();
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

    EditText etStuFname, etStuLname, etStuId, etStuMobile, etStuAdress, etStuMedicalDate, etStuBeginDate, etStuTestDate, etStuEyesDate;
    CheckBox cBoxStuA, cBoxStuB, cBoxStuC;
    Switch swStuGlasses;
    Button btnStuAdd;
    ImageView btnBack;
    String stfname, stlname, stid, stmobile, stadress, strank, stmedical, stbegin, sttest, steyes, stglasses;
    FirebaseFirestore db;
    ArrayList<Student> students;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_student_add, container, false);

        etStuFname = view.findViewById(R.id.etStuFname);
        etStuLname = view.findViewById(R.id.etStuLname);
        etStuId = view.findViewById(R.id.etStuId);
        etStuMobile = view.findViewById(R.id.etStuMobile);
        etStuAdress = view.findViewById(R.id.etStuAdress);
        cBoxStuA = view.findViewById(R.id.cBoxStuA);
        cBoxStuB = view.findViewById(R.id.cBoxStuB);
        cBoxStuC = view.findViewById(R.id.cBoxStuC);
        etStuMedicalDate = view.findViewById(R.id.etStuMedicalDate);
        etStuBeginDate = view.findViewById(R.id.etStuBeginDate);
        etStuTestDate = view.findViewById(R.id.etStuTestDate);
        etStuEyesDate = view.findViewById(R.id.etStuEyesDate);
        swStuGlasses = view.findViewById(R.id.swStuGlasses);
        btnStuAdd = view.findViewById(R.id.btnStuUpdate);
        btnBack = view.findViewById(R.id.btnBack);


        currentUserIn = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        students = new ArrayList<>();

        btnStuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stfname = etStuFname.getText().toString();
                stlname = etStuLname.getText().toString();
                stid = etStuId.getText().toString();
                stmobile = etStuMobile.getText().toString();
                stadress = etStuAdress.getText().toString();

                strank = "";
                if (cBoxStuA.isChecked()) {
                    strank = "A ";
                }
                if (cBoxStuB.isChecked()) {
                    strank = strank + " B ";
                }
                if (cBoxStuC.isChecked()) {
                    strank = strank + " C ";
                }

                stmedical = etStuMedicalDate.getText().toString();

                stbegin = etStuBeginDate.getText().toString();

                sttest = etStuTestDate.getText().toString();

                steyes = etStuEyesDate.getText().toString();

                stglasses = "No";
                swStuGlasses.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (swStuGlasses.isChecked()) {
                            stglasses = "Yes";
                        }
                    }
                });


                Student std = new Student(stfname, stlname, stid, stmobile, stadress, strank, stmedical, stbegin, sttest, steyes, stglasses);

                students.add(std);

                db.getInstance().collection(currentUserIn + "-Students").document(std.getStdId()).set(std).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "New Student Added", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fts = getFragmentManager().beginTransaction();
                            fts.replace(R.id.frmContent, new StudentsFragment());
                            fts.addToBackStack(null);
                            fts.commit();
                        } else {
                            Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fts = getFragmentManager().beginTransaction();
                fts.replace(R.id.frmContent, new StudentsFragment());
                fts.commit();
            }
        });

        return view;
    }

}