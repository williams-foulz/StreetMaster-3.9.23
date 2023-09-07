package com.example.streetmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private LocalDate selectedDate;
    private ArrayList<String> days;

    public CalendarAdapter(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
        days = buildDaysInMonth();
    }

    @NonNull
    @Override
    public CalendarAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day, parent, false);

        // size of layout
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        // height of layout
        layoutParams.height = (int)(parent.getHeight()/6.0);


        CalendarViewHolder holder = new CalendarViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.CalendarViewHolder holder, int position) {

        holder.tvDay.setText(days.get(position));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }

    private ArrayList<String> buildDaysInMonth() {
        ArrayList<String> result = new ArrayList<>();

        // get year month
        YearMonth yearMonth = YearMonth.from(selectedDate);

        //get number of days in month
        int DaysInMonth = yearMonth.lengthOfMonth();

        //first day in month
        LocalDate firstDayDate = selectedDate.withDayOfMonth(1);

        //first day in week value
        int firstDayInWeek = firstDayDate.getDayOfWeek().getValue();

        // 42 max grid list
        for (int i = 1; i <= 42; i++) {
            if (i <= firstDayInWeek || i>=DaysInMonth+firstDayInWeek) {
                result.add("");
            } else {

                //empty days
                result.add(String.valueOf(i - firstDayInWeek));
            }
        }
        return result;

    }

}
