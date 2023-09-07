package com.example.streetmaster;


import static com.example.streetmaster.Home_Page_Activity.currentUserIn;
import static com.example.streetmaster.StudentAdapter.currentposition;
import static com.example.streetmaster.StudentsFragment.stuList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentCardFragment newInstance(String param1, String param2) {
        StudentCardFragment fragment = new StudentCardFragment();
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

    TextView tvMyName, tvTotLesson, tvTotTest;
    ImageButton btnLessonPlus, btnLessonMinus, btnTestMinus, btnTestPlus;
    Switch swInTest;
    CheckBox box11, box12, box13, box14, box15, box16, box17, box18, box19, box21, box22, box23, box24, box25, box31, box32, box33, box34, box35, box36, box37;
    CheckBox box41, box42, box43, box44, box51, box52, box53, box54, box61, box62, box63, box64;
    CheckBox box71, box72, box73, box74, box81, box82, box83, box84, box85, box86;
    CheckBox box91, box92, box93, box94, box95, box101, box102, box103, box104;
    FloatingActionButton btnSaveCard;
    int lessons;
    int tests;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_card, container, false);

        currentUserIn = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        tvMyName = view.findViewById(R.id.tvMyName);
        tvTotLesson = view.findViewById(R.id.tvTotLesson);
        tvTotTest = view.findViewById(R.id.tvTotTest);
        btnLessonPlus = view.findViewById(R.id.btnLessonPlus);
        btnLessonMinus = view.findViewById(R.id.btnLessonMinus);
        btnTestMinus = view.findViewById(R.id.btnTestMinus);
        btnTestPlus = view.findViewById(R.id.btnTestPlus);
        swInTest = view.findViewById(R.id.swInTest);
        box11 = view.findViewById(R.id.box11);
        box12 = view.findViewById(R.id.box12);
        box13 = view.findViewById(R.id.box13);
        box14 = view.findViewById(R.id.box14);
        box15 = view.findViewById(R.id.box15);
        box16 = view.findViewById(R.id.box16);



        btnSaveCard = view.findViewById(R.id.btnSaveCard);

        Student myStudent = stuList.get(currentposition);
        String myID = myStudent.getStdId();
        tvMyName.setText(myStudent.getStdFname().toString() + " " + myStudent.getStdLname().toString());

        btnLessonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessons++;
                tvTotLesson.setText(lessons + "");
            }
        });
        btnLessonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessons == 0) {
                } else {
                    lessons--;
                    tvTotLesson.setText(lessons + "");
                }
            }
        });
        btnTestPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swInTest.isChecked() == true) {
                    tests++;
                    tvTotTest.setText(tests + "");
                }
            }
        });
        btnTestMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swInTest.isChecked() == true) {
                    if (tests == 0) {
                    } else {
                        tests--;
                        tvTotTest.setText(tests + "");
                    }
                }
            }
        });

        swInTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

        xbox(box11);xbox(box12);xbox(box13);xbox(box14);xbox(box15);xbox(box16);



        btnSaveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentCard myCard = new StudentCard(myID, lessons, tests, swInTest.isChecked(), box11.isChecked(), box12.isChecked(), box13.isChecked(), box14.isChecked(), box15.isChecked(), box16.isChecked());
                FirebaseFirestore.getInstance().collection(currentUserIn + "-StudenCard").document(myID).set(myCard).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Card updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }

    private void xbox(CheckBox box) {
        box.setChecked(false);
        if (box.isChecked()) {
            box.setChecked(true);
        }
    }

}