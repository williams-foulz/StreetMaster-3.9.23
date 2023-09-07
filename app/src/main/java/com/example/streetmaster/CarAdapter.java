package com.example.streetmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {


    private Context context;
    private ArrayList<CarStatus> carsList;
    private CarOnListClick ModelClick;
    static int pos;  // pos = car clicked position


    public CarAdapter(ArrayList<CarStatus> carsList, CarOnListClick ModelClick) {
        this.carsList = carsList;
        this.ModelClick = ModelClick;
    }

    @NonNull
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_in_list, parent, false);

        CarViewHolder holder = new CarViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {

        final int finalPosition = position;

        CarStatus carSt = carsList.get(position);


        holder.tvCarModel.setText(carSt.getModel());
        holder.tvCarNumber.setText(carSt.getNumber());

        holder.tvCarModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelClick.CarClicked(carSt);

                // car position on click
                pos = holder.getAdapterPosition();

            }
        });

    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tvCarModel, tvCarNumber;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCarModel = itemView.findViewById(R.id.tvCarModel);
            tvCarNumber = itemView.findViewById(R.id.tvCarNumber);


        }
    }

    public interface CarOnListClick {
        public void CarClicked(CarStatus thisCar);
    }
}