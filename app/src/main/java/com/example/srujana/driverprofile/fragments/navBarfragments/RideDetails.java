package com.example.srujana.driverprofile.fragments.navBarfragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.example.srujana.driverprofile.R;
import com.example.srujana.driverprofile.dialogBoxes.MonthYearPickerDialog;
import com.example.srujana.driverprofile.fragments.GraphData;
import com.example.srujana.driverprofile.fragments.searchfragments.SearchData;
import com.example.srujana.driverprofile.fragments.searchfragments.SearchBySpinner;
import com.example.srujana.driverprofile.fragments.searchfragments.SearchTextView;

import java.util.Calendar;


public class RideDetails extends Fragment {

    FragmentManager fragmentManager;
    Fragment fragment;
    Switch graph;
    View view;
    private boolean isDate=false;
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rides, container, false);
        graph = (Switch) view.findViewById(R.id.graph_switch);
        graph.setVisibility(View.GONE);
        graph.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showGraph(isChecked);
            }
        });
        final SearchBySpinner staticSpinner = (SearchBySpinner) view.findViewById(R.id.static_spinner);
        staticSpinner.setPrompt("select an option to search");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.searchBy, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        staticSpinner.setAdapter(adapter);
        staticSpinner.setSelection(0, false);
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ("Search By Date".equals(staticSpinner.getSelectedItem().toString())) {
                    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            bundle.putString("Date", dayOfMonth + "-" + monthOfYear + "-" + year);
                            bundle.putBoolean("isDate",true);
                            isDate=true;
                            showFragment();
                        }
                    };
                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), onDateSetListener, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
                    dpd.show();
                } else if("Search By Month".equals(staticSpinner.getSelectedItem().toString())){
                    MonthYearPickerDialog pd = new MonthYearPickerDialog();
                    pd.setListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            bundle.putInt("Year", year);
                            bundle.putInt("Month", monthOfYear);
                            bundle.putBoolean("isDate",false);
                            isDate=false;
                            showFragment();
                        }
                    });

                    pd.show(getFragmentManager(), "MonthYearPickerDialog");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.searchby_content, new SearchTextView()).commit();
        return view;
    }

    private void showFragment() {
        fragment = new SearchData();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.searchby_content, fragment).commit();
        graph.setVisibility(View.VISIBLE);
        graph.setChecked(false);
    }

    private void showGraph(boolean isChecked) {
        if(isChecked){
            if(isDate){
                bundle.putBoolean("isDate",true);
                if(getView()!=null) {
                    TextView display = (TextView) getView().findViewById(R.id.display_date);
                    bundle.putString("Display Text", (String) display.getText());
                }
            }else{
                bundle.putBoolean("isDate",false);
                if(getView()!=null) {
                    TextView display = (TextView) getView().findViewById(R.id.display_date);
                    bundle.putString("Display Text", (String) display.getText());
                }

            }
            fragment =new GraphData();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.searchby_content,fragment).commit();
        }else{
            if(isDate){

                bundle.putBoolean("isDate",true);

            }else{
                fragment=new SearchData();
            }
            fragment =new SearchData();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.searchby_content,fragment).commit();

        }
    }


}
