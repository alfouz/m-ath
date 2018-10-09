package com.alfouz.tfm.tfm;


import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View root;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        /*PieChart chart = (PieChart) root.findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(55, "Completado"));
        entries.add(new PieEntry(45, "Pendiente"));

        PieDataSet dataSet = new PieDataSet(entries, "Curso"); // add entries to dataset
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        dataSet.setColors(colors);
        dataSet.setValueTextColor(R.color.colorText); // styling, ...

        PieData lineData = new PieData(dataSet);
        chart.setData(lineData);
        chart.setTouchEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate(); // refresh*/

        return root;
    }

}
