package com.example.storeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BestSelling extends AppCompatActivity {

    //Initialize BarChart
    BarChart barChart;
    // Initialize BarEntries
    ArrayList<BarEntry> barEntries = new ArrayList<>();

    //Bar Chart Item Labels
    String[] mStackLabels = new String[]{"Item 1", "Item 2", "Item 3","Item 4","Item 5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_selling);
        barChart=findViewById(R.id.bar_chart);
        BarDataSet ds = null;
        for(int i = 0; i<5;i++)
        {
            //float value = number of items sold
            float value=10;
            //initialize bar chart entry (X,Y)
            BarEntry barEntry = new BarEntry(i,value);
            barEntries.add(barEntry);
        }
        ds = new BarDataSet(barEntries,"BestSelling Items");
        ds.setColors(ColorTemplate.MATERIAL_COLORS);
        ds.setDrawValues(false);
        ds.setBarBorderWidth(5);
        //fill bar chart with data
        barChart.setData(new BarData(ds));
        //Add Labels to X Axis
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(mStackLabels));
    }
}