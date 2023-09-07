package com.example.streetmaster;


import static com.example.streetmaster.CarAdapter.pos;
import static com.example.streetmaster.Home_Page_Activity.currentUserIn;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarInfoFragment extends Fragment implements CarAdapter.CarOnListClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarInfoFragment newInstance(String param1, String param2) {
        CarInfoFragment fragment = new CarInfoFragment();
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

    ImageButton btnAddCar, btnEditCar, btnDeleteCar;
    RecyclerView rcvCars;
    CarAdapter adapter;
    static ArrayList<CarStatus> carsListInfo;

    TextView tvModel, tvNumber, tvYear, tvKm, tvExLicense, tvExInsurance, tvTransmission, tvSchool, tvLicenseEnd, tvInsuranceEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_info, container, false);

        rcvCars = view.findViewById(R.id.rcvCars);
        btnAddCar = view.findViewById(R.id.btnAddCar);
        btnEditCar = view.findViewById(R.id.btnEditCar);
        btnDeleteCar = view.findViewById(R.id.btnDeleteCar);

        tvModel = view.findViewById(R.id.tvModel);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvYear = view.findViewById(R.id.tvYear);
        tvKm = view.findViewById(R.id.tvKm);
        tvExLicense = view.findViewById(R.id.tvExLicense);
        tvExInsurance = view.findViewById(R.id.tvExInsurance);
        tvTransmission = view.findViewById(R.id.tvTransmission);
        tvSchool = view.findViewById(R.id.tvSchool);
        tvLicenseEnd = view.findViewById(R.id.tvLicenseEnd);
        tvInsuranceEnd = view.findViewById(R.id.tvInsuranceEnd);


        Toast.makeText(getContext(), currentUserIn, Toast.LENGTH_SHORT).show();

        // car info list

        carsListInfo = new ArrayList<>();
        adapter = new CarAdapter(carsListInfo, this);
        rcvCars.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvCars.setLayoutManager(manager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(currentUserIn + "-car").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    carsListInfo.clear();
                    for (DocumentSnapshot doc : task.getResult()) {
                        CarStatus cs = doc.toObject(CarStatus.class);
                        carsListInfo.add(cs);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        // button add new car
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Fragment fragment = new CarAddFragment();
                FragmentTransaction fts = getFragmentManager().beginTransaction();
                fts.replace(R.id.frmContent, new CarAddFragment());
                fts.addToBackStack(null);
                // Commit the transaction
                fts.commit();
            }
        });


        // button to update car info
        btnEditCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fts = getFragmentManager().beginTransaction();
                fts.replace(R.id.frmContent, new CarEditFragment());
                fts.addToBackStack(null);
                fts.commit();
            }
        });


        // button to delete car fromlist
        btnDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String docID = carsListInfo.get(pos).getNumber();
                db.collection(currentUserIn + "-car").document(docID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            db.collection(currentUserIn + "-car").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for (DocumentChange chg : value.getDocumentChanges()) {
                                        CarStatus cr = chg.getDocument().toObject(CarStatus.class);
                                        switch (chg.getType()) {
                                            case REMOVED:
                                                carsListInfo.remove(cr);
                                                break;
                                        }
                                    }
                                }
                            });
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void CarClicked(CarStatus thisCar) {

        tvModel.setText(thisCar.getModel());
        tvNumber.setText(thisCar.getNumber());
        tvYear.setText(thisCar.getYear());
        tvKm.setText(thisCar.getKm());
        tvExLicense.setText(thisCar.getExLicense());
        tvExInsurance.setText(thisCar.getExInsurance());
        tvTransmission.setText(thisCar.getTransmission());
        tvSchool.setText(thisCar.getSchool());
        betweenDates(tvExLicense, tvLicenseEnd);
        betweenDates(tvExInsurance, tvInsuranceEnd);

    }

    private void betweenDates(TextView tvMyDate, TextView tvBetween) {
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
