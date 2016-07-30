package com.example.srujana.driverprofile.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srujana.driverprofile.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.*;


public class GraphData extends Fragment implements OnChartValueSelectedListener {

    private  int itemCount;
    String[] days=new String[]{"MON","TUE","WED","THU","FRI","SAT","SUN"};
    boolean isDate=false;
    CombinedChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_date, container, false);
        TextView diplay = (TextView) view.findViewById(R.id.display);
        isDate= (boolean) getArguments().get("isDate");
        if(isDate) {
            itemCount = 10;
        } else {
            itemCount = 7;
        }
        if(getArguments().get("Display Text")!=null) {
            diplay.setText((CharSequence) getArguments().get("Display Text"));
        }
         mChart = (CombinedChart) view.findViewById(R.id.chart1);
        mChart.setDescription("");
        mChart.setBackgroundColor(WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        Legend legend = mChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
        legend.setExtra(new int[]{Color.BLUE, Color.RED,Color.GREEN},new String[]{"1","2","3"});

       // mChart.getLegend().setEnabled(true);


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinValue(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(8);
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(isDate) {
                    return "Ride" + ((int) value);
                }
                else {
                    return days[(int) value % days.length];
                }

            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        CombinedData data = new CombinedData();

       data.setData(generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaxValue(data.getXMax()+0.25f);
        xAxis.setAxisMinValue(-0.25f);

        mChart.setData(data);
        mChart.setOnChartValueSelectedListener(this);
        mChart.invalidate();


        return view;
    }


    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
if(isDate) {
    for (int index = 0; index < itemCount; index++)
        entries.add(new Entry(index
                , getRandom(15, 5)));
}else{
    for (int index = 0; index < itemCount; index++)
        entries.add(new Entry(index
                , getRandom(200, 58)));
}
        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(9f);
        set.setValueTextColor(rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private float getRandom(int i, int i1) {
        return (new Random().nextFloat() * (i1 - i) + i);
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
        if(isDate) {
            for (int index = 0; index < itemCount; index++) {
                entries2.add(new BarEntry(index, getRandom(10, 3)));
            }
        }
        else {
            for (int index = 0; index < itemCount; index++) {
                entries2.add(new BarEntry(index, getRandom(50, 20)));
            }
        }
        BarDataSet set2 = new BarDataSet(entries2, "Hello");
        set2.setColors(ColorTemplate.COLORFUL_COLORS);
        set2.setValueTextColor(rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        float barWidth = 0.7f; // x2 dataset

        BarData d = new BarData(set2);
        d.setBarWidth(barWidth);

        return d;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        if(isDate)
        Toast.makeText(getActivity(),"Fare for that Ride "+(h.getY())*100+"",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(),"Total income Earned on that day"+(h.getY())*100+"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
