package com.alfouz.tfm.tfm;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseEntityDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.alfouz.tfm.tfm.Util.JSONHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long idCourse = getIntent().getLongExtra("idCourse", -1);


        final TextView tvUsers = findViewById(R.id.tvUsers);


        new GetCourseEntityDB(new CallbackInterface<CourseEntity>() {
            @Override
            public void doCallback(CourseEntity courseEntity) {
                getSupportActionBar().setTitle(courseEntity.getTitle());

                RequestQueue requestQueueUsers = Volley.newRequestQueue(MyApplication.getAppContext());
                JsonObjectRequest requestUsers = new JsonObjectRequest(
                        Request.Method.GET, //GET or POST
                        APIRestUtil.getResults() + "/countDiffUsersByCourse/" + courseEntity.getIdRemote(), //URL
                        null, //Parameters
                        new Response.Listener<JSONObject>() { //Listener OK

                            @Override
                            public void onResponse(JSONObject responsePlaces) {
                                //Obtenemos los resultados y las mostramos por pantalla
                                try {
                                    tvUsers.setText(String.format(getString(R.string.stats_users), Integer.toString(responsePlaces.getInt("users"))));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() { //Listener ERROR

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error
                        Log.d("tst", error.toString());
                    }
                });

                //Send the request to the requestQueue
                requestQueueUsers.add(requestUsers);

            }
        }, getApplicationContext()).execute(idCourse);
        final JSONHelper jsonHelper = new JSONHelper(getApplicationContext());

        final BarChart chartHorizontal = (BarChart) findViewById(R.id.chartHorizontal);

        new GetCourseLessonsDB(new CallbackInterface<List<LessonEntity>>() {
            @Override
            public void doCallback(List<LessonEntity> lessons) {
                final List<BarEntry> entries = new ArrayList<>();
                final List<Integer> colors = new ArrayList<>();
                RequestQueue requestQueue2 = Volley.newRequestQueue(MyApplication.getAppContext());

                float actLesson= 0;
                for(final LessonEntity lesson : lessons) {
                    actLesson++;
                    final float finalActLesson = actLesson;
                    JsonArrayRequest request2 = new JsonArrayRequest(
                            Request.Method.GET, //GET or POST
                            APIRestUtil.getResults() + "/lesson/" + lesson.getIdRemote(), //URL
                            null, //Parameters
                            new Response.Listener<JSONArray>() { //Listener OK

                                @Override
                                public void onResponse(JSONArray responsePlaces) {
                                    //Obtenemos los resultados y las mostramos por pantalla
                                    try {
                                        List<ResultUserLessonEntity> results = jsonHelper.getResultsFromJSON(responsePlaces);

                                        float percentCorrect = 0;
                                        for(ResultUserLessonEntity r : results){
                                            percentCorrect+=r.getPercentCorrect();
                                        }
                                        percentCorrect=percentCorrect/results.size();
                                        entries.add(new BarEntry(finalActLesson, percentCorrect, lesson.getTitle()));

                                        if(percentCorrect<20){
                                            colors.add(getResources().getColor(R.color.colorHorribleScore));
                                        }else{
                                            if(percentCorrect<40){
                                                colors.add(getResources().getColor(R.color.colorBadScore));
                                            }else{
                                                if(percentCorrect<60){
                                                    colors.add(getResources().getColor(R.color.colorRegularScore));
                                                }else{
                                                    if(percentCorrect<80){
                                                        colors.add(getResources().getColor(R.color.colorGoodScore));
                                                    }else{
                                                        colors.add(getResources().getColor(R.color.colorPerfectScore));
                                                    }
                                                }
                                            }
                                        }

                                        BarDataSet set = new BarDataSet(entries, getString(R.string.misc_lessons));
                                        set.setColors(colors);

                                        BarData data = new BarData(set);
                                        data.setBarWidth(0.5f); // set custom bar width
                                        data.setHighlightEnabled(false);

                                        chartHorizontal.setData(data);
                                        chartHorizontal.setFitBars(true); // make the x-axis fit exactly all bars
                                        chartHorizontal.setTouchEnabled(false);
                                        chartHorizontal.getBarData().setValueFormatter(new IValueFormatter() {
                                            @Override
                                            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                                                return entry.getData().toString();
                                            }
                                        });
                                        chartHorizontal.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
                                            @Override
                                            public String getFormattedValue(float value, AxisBase axis) {
                                                return Float.toString(value) + "%";
                                            }
                                        });
                                        chartHorizontal.getAxisLeft().setAxisMinimum(0f);
                                        chartHorizontal.getAxisLeft().setAxisMaximum(100f);
                                        chartHorizontal.getAxisLeft().setGranularity(1);
                                        chartHorizontal.getAxisRight().setEnabled(false);
                                        chartHorizontal.getData().setValueTextSize(6);
                                        //chartHorizontal.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                                        chartHorizontal.getLegend().setEnabled(false);
                                        chartHorizontal.getDescription().setEnabled(false);
                                        chartHorizontal.getXAxis().setDrawLabels(false);
                                        chartHorizontal.getXAxis().setDrawGridLines(false);


                                        chartHorizontal.invalidate(); // refresh

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() { //Listener ERROR

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //error
                        }
                    });

                    //Send the request to the requestQueue
                    requestQueue2.add(request2);
                }


            }
        }, getApplicationContext()).execute(idCourse);







        /*entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));*/





        /*RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, //GET or POST
                APIRestUtil.getResults() + "/user/" + MyApplication.getIdUser(), //URL
                null, //Parameters
                new Response.Listener<JSONArray>() { //Listener OK

                    @Override
                    public void onResponse(JSONArray responsePlaces) {
                        //Creado resultado
                        Log.d("tst", responsePlaces.toString());
                    }
                }, new Response.ErrorListener() { //Listener ERROR

            @Override
            public void onErrorResponse(VolleyError error) {
                //error
            }
        });


        //Send the request to the requestQueue
        requestQueue.add(request);*/




    }
}
