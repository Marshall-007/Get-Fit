package com.example.getfit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class FitnessTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_tracker);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(3f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(30f, 6));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");
        labels.add("Sunday");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Weight Gained  VS Weight Lost  ");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);

    }
}