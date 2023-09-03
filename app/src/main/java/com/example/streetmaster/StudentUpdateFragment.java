package com.example.streetmaster;

import static com.example.streetmaster.Home_Page_Activity.currentUserIn;
import static com.example.streetmaster.StudentAdapter.currentposition;
import static com.example.streetmaster.StudentsFragment.stuList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentUpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentUpdateFragment newInstance(String param1, String param2) {
        StudentUpdateFragment fragment = new StudentUpdateFragment();
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

    StudentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_add, container, false);

        //find views by id

        Button btnStuUpdate = view.findViewById(R.id.btnStuUpdate);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        EditText etStuFname = view.findViewById(R.id.etStuFname);
        EditText etStuLname = view.findViewById(R.id.etStuLname);
        EditText etStuId = view.findViewById(R.id.etStuId);
        EditText etStuMobile = view.findViewById(R.id.etStuMobile);
        EditText etStuAdress = view.findViewById(R.id.etStuAdress);
        EditText etStuMedicalDate = view.findViewById(R.id.etStuMedicalDate);
        EditText etStuBeginDate = view.findViewById(R.id.etStuBeginDate);
        EditText etStuTestDate = view.findViewById(R.id.etStuTestDate);
        EditText etStuEyesDate = view.findViewById(R.id.etStuEyesDate);
        CheckBox cBoxStuA = view.findViewById(R.id.cBoxStuA);
        CheckBox cBoxStuB = view.findViewById(R.id.cBoxStuB);
        CheckBox cBoxStuC = view.findViewById(R.id.cBoxStuC);
        Switch swStuGlasses = view.findViewById(R.id.swStuGlasses);
        // button update change text (ADD to Update)
        btnStuUpdate.setText("Update");


        // connect to data base
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String docID = stuList.get(currentposition).getStdId();

        db.collection(currentUserIn + "-Students").document(docID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // student in position
                Student editSTD = stuList.get(currentposition);

                // fill the data and close some fields
                etStuFname.setText(editSTD.getStdFname());
                etStuFname.setEnabled(false);

                etStuLname.setText(editSTD.getStdLname());

                etStuId.setText(editSTD.getStdId());
                etStuId.setEnabled(false);

                etStuMobile.setText(editSTD.getStdPhone());

                etStuAdress.setText(editSTD.getStdAdress());

                etStuMedicalDate.setText(editSTD.getStdMedicalConfirmationDate());

                etStuBeginDate.setText(editSTD.getStdStartDate());

                etStuTestDate.setText(editSTD.getStdTestDate());

                etStuEyesDate.setText(editSTD.getStdEyesDate());

                String glass = editSTD.getStdGlasses().toString();

                // btn update
                btnStuUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = etStuFname.getText().toString();
                        String b = etStuLname.getText().toString();
                        String c = etStuId.getText().toString();
                        String d = etStuMobile.getText().toString();
                        String e = etStuAdress.getText().toString();
                        String f = etStuMedicalDate.getText().toString();
                        String g = etStuBeginDate.getText().toString();
                        String h = etStuTestDate.getText().toString();
                        String i = etStuEyesDate.getText().toString();

                        String strank = "";
                        if (cBoxStuA.isChecked()) {
                            strank = "A ";
                        }
                        if (cBoxStuB.isChecked()) {
                            strank = strank + " B ";
                        }
                        if (cBoxStuC.isChecked()) {
                            strank = strank + " C ";
                        }

                        if(glass == "Yes"){swStuGlasses.setChecked(true);}
                        else {swStuGlasses.setChecked(false);}

                        //  create new student after update
                        Student estu = new Student(a, b, c, d, e,strank, f, g, h, i,glass);
                        stuList.add(estu);

                        //  delete old document from db
                       //  db.collection(currentUserIn + "-Students").document(docID).delete();

                        //   add new document to db
                        db.collection(currentUserIn + "-Students").document(editSTD.getStdId()).set(estu).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "updating ...", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction fts = getFragmentManager().beginTransaction();
                                    fts.replace(R.id.frmContent, new StudentsFragment());
                                    fts.commit();
                                }

                            }
                        });

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