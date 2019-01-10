package com.alfouz.tfm.tfm;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.CourseAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetLastCoursePlayedDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetResultsAlongTimeDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Util.CourseType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View root;

    private RecyclerView mRecyclerView;
    private CourseAdapter mAdapter;

    private long idUser;

    private LineChart lineChart;
    private Spinner spinner;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        idUser = MyApplication.getIdUser();

        //Warning
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((TextView) root.findViewById(R.id.textView)).setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }*/



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        spinner = root.findViewById(R.id.spDays);
        List<String> values = new ArrayList<>();
        values.add(getString(R.string.misc_last_week));
        values.add(getString(R.string.misc_last_month));
        values.add(getString(R.string.misc_last_year));
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, values));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                long days = 7;
                switch (position){
                    case 0: days=7;
                    break;
                    case 1: days=30;
                    break;
                    case 2: days=365;
                    break;
                    default: days=7;
                    break;
                }
                new GetResultsAlongTimeDB(new CallbackInterface<List<Integer>>() {
                    @Override
                    public void doCallback(final List<Integer> list) {
                        List<Entry> entries = new ArrayList<Entry>();
                        List<Integer> invertedList = new ArrayList<>();
                        for(int i=(list.size()-1); i>=0; i--){
                            invertedList.add(list.get(i));
                        }
                        for(int i=0; i<invertedList.size(); i++){
                            entries.add(new Entry(i, invertedList.get(i)));
                        }
                        LineDataSet setComp1 = new LineDataSet(entries, getString(R.string.home_activity));
                        setComp1.setDrawValues(false);
                        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                        setComp1.setDrawFilled(true);
                        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                        dataSets.add(setComp1);
                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);
                        lineChart.getAxisLeft().setAxisMinimum(0f);
                        lineChart.getAxisRight().setAxisMinimum(0f);
                        lineChart.getLegend().setEnabled(false);
                        lineChart.getDescription().setEnabled(false);
                        //lineChart.getXAxis().setEnabled(true);
                        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Integer.toString((int)Math.floor(list.size()-value));
                            }
                        });
                        lineChart.getAxisLeft().setEnabled(false);
                        lineChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return String.format(getString(R.string.misc_comp_lessons), (int)value);
                            }
                        });
                        lineChart.getAxisRight().setTextSize(6);
                        lineChart.setTouchEnabled(false);
                        lineChart.getAxisRight().setEnabled(true);
                        lineChart.invalidate(); // refresh
                    }
                }, getContext()).execute(idUser, days);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        lineChart = root.findViewById(R.id.chartLine);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.courseCard);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        new GetLastCoursePlayedDB(new CallbackInterface<Course>() {
            @Override
            public void doCallback(Course course) {

                List<Course> courses = new ArrayList();
                if(course!=null) {
                    courses.add(course);
                }else{
                    courses.add(new Course(-1, idUser, getString(R.string.course_not_enough_data), new ArrayList<Lesson>(), 0f, 0, getString(R.string.course_not_enough_data_desc), true, com.alfouz.tfm.tfm.Util.CourseType.Others, -1));
                }
                mAdapter = new CourseAdapter(courses, new CourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Course item) {
                        if(item.getId()>0) {
                            Intent intent = new Intent(getActivity(), CourseActivity.class);
                            intent.putExtra("idCourse", item.getId());
                            intent.putExtra("idUser", idUser);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), R.string.home_course_example, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        }, getContext()).execute(idUser);

        new GetResultsAlongTimeDB(new CallbackInterface<List<Integer>>() {
            @Override
            public void doCallback(final List<Integer> list) {
                List<Entry> entries = new ArrayList<Entry>();
                List<Integer> invertedList = new ArrayList<>();
                for(int i=(list.size()-1); i>=0; i--){
                    invertedList.add(list.get(i));
                }
                for(int i=0; i<invertedList.size(); i++){
                    entries.add(new Entry(i, invertedList.get(i)));
                }
                /*entries.add(new Entry(0, 2));
                entries.add(new Entry(1, 5));
                entries.add(new Entry(2, 12));
                entries.add(new Entry(3, 8));
                entries.add(new Entry(4, 25));
                entries.add(new Entry(5, 1));
                entries.add(new Entry(6, 11));*/
                LineDataSet setComp1 = new LineDataSet(entries, getString(R.string.home_activity));
                setComp1.setDrawValues(false);
                setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                setComp1.setDrawFilled(true);
                List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(setComp1);
                LineData data = new LineData(dataSets);
                lineChart.setData(data);
                lineChart.getAxisLeft().setAxisMinimum(0f);
                lineChart.getAxisRight().setAxisMinimum(0f);
                lineChart.getLegend().setEnabled(false);
                lineChart.getDescription().setEnabled(false);
                //lineChart.getXAxis().setEnabled(true);
                lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return Integer.toString((int)Math.floor(list.size()-value));
                    }
                });
                lineChart.getAxisLeft().setEnabled(false);
                lineChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return String.format(getString(R.string.misc_comp_lessons), (int)value);
                    }
                });
                lineChart.getAxisRight().setTextSize(6);
                lineChart.setTouchEnabled(false);
                lineChart.getAxisRight().setEnabled(true);
                lineChart.invalidate(); // refresh
            }
        }, getContext()).execute(idUser, 7l);

    }
}
