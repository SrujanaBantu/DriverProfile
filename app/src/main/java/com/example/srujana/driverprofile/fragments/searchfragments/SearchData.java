package com.example.srujana.driverprofile.fragments.searchfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.srujana.driverprofile.R;

import java.text.MessageFormat;
import java.util.Random;


public class SearchData extends Fragment {

    Random random = new Random();
    boolean isDate;
    int[] testValues;
    float rate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_by_date, container, false);
        TextView textView = (TextView) view.findViewById(R.id.display_date);
        isDate = (boolean) getArguments().get("isDate");
        if (isDate) {
            textView.setText(String.format("Ride Details  on %s", getArguments().get("Date")));
        } else {
            int n = (int) getArguments().get("Month");
            textView.setText(String.format("Ride Details For %s-%s", months.values()[n], getArguments().get("Year")));
        }
        TextView tripsValue = (TextView) view.findViewById(R.id.no_of_trips_value);
        TextView kmsValue = (TextView) view.findViewById(R.id.no_of_kms_value);
        TextView incomeValue = (TextView) view.findViewById(R.id.income_value);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_doctor);
        TextView cancelValue = (TextView) view.findViewById(R.id.rides_cancelled_value);
        TextView timeValue = (TextView) view.findViewById(R.id.ride_time_value);
        if (isDate) {
            testValues = new int[]{(random.nextInt(15 - 6) + 6), (random.nextInt(200 - 100) + 100), (random.nextInt(5000 - 1200) + 1200), random.nextInt(4), (random.nextInt(10 - 5) + 5)};
            rate = (random.nextFloat() * (4 - 1) + 1);
        } else {
            testValues = new int[]{(random.nextInt(400 - 200) + 200), (random.nextInt(1700 - 1200) + 1200), (random.nextInt(70000 - 40000) + 40000), (random.nextInt(30 - 15) + 15), (random.nextInt(300 - 250) + 250)};
            rate = (random.nextFloat() * (4 - 1) + 1);
        }

        tripsValue.setText(MessageFormat.format("You Have rode {0} trip(s)", testValues[0]));
        kmsValue.setText(MessageFormat.format("{0} Kms", testValues[1]));
        incomeValue.setText(MessageFormat.format("₹{0}", testValues[2]));
        ratingBar.setRating(rate);
        cancelValue.setText(MessageFormat.format("{0} Trip(s) have been cancelled", testValues[3]));
        timeValue.setText(MessageFormat.format("{0} Hours", testValues[4]));


        return view;
    }

    enum months {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC}

}
