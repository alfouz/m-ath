package com.alfouz.tfm.tfm;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetCoursesByStudentDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetImageFromUrl;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.Util.CourseType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View root;

    private long idUser;

    private RadarChart radarChart;

    private TextView tvTotalCursos;
    private TextView tvTotalCorrecto;

    GetCoursesByStudentDB getCoursesByStudentDB;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        idUser = MyApplication.getIdUser();

        String name = getActivity().getIntent().getStringExtra("personName");
        TextView accountName = root.findViewById(R.id.accountName);
        accountName.setText(name);
        final ImageView accountProfileImage;accountProfileImage = root.findViewById(R.id.accountProfileImage);

        new GetImageFromUrl(new CallbackInterface<Bitmap>() {
            @Override
            public void doCallback(Bitmap image) {
                if(image != null) {
                    Bitmap output = Bitmap.createBitmap(image.getWidth(), image
                            .getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(output);

                    final int color = 0xff424242;
                    final Paint paint = new Paint();
                    final Rect rect = new Rect(0, 0, image.getWidth(), image.getHeight());
                    final RectF rectF = new RectF(rect);
                    final float roundPx = 250;

                    paint.setAntiAlias(true);
                    canvas.drawARGB(0, 0, 0, 0);
                    paint.setColor(color);
                    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(image, rect, rect, paint);

                    accountProfileImage.setImageBitmap(output);
                }
            }
        }).execute(getActivity().getIntent().getStringExtra("personPhotoUrl"));
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

    @Override
    public void onResume() {
        super.onResume();

        radarChart = root.findViewById(R.id.chartRadar);

        tvTotalCursos = root.findViewById(R.id.tvTotalCursos);
        tvTotalCorrecto = root.findViewById(R.id.tvTotalCorrecto);

        getCoursesByStudentDB = new GetCoursesByStudentDB(new CallbackInterface<List<Course>>() {
            @Override
            public void doCallback(List<Course> courses) {

                float totalCorrecto = 0;
                int totalCursos = courses.size();

                List<Integer> results = new ArrayList<>(); //0 others, 1 maths, 2 science, 3 eng, 4 tec
                results.add(0);
                results.add(0);
                results.add(0);
                results.add(0);
                results.add(0);
                List<Integer> scores = new ArrayList<>();
                scores.add(0);
                scores.add(0);
                scores.add(0);
                scores.add(0);
                scores.add(0);
                for(Course c : courses){
                    totalCorrecto+=c.getScore();
                    switch(c.getType()){
                        case Maths:
                            results.set(1, results.get(1)+1);
                            scores.set(1, scores.get(1)+c.getScore());
                            break;
                        case Science:
                            results.set(2, results.get(2)+1);
                            scores.set(2, scores.get(2)+c.getScore());
                            break;
                        case Engineering:
                            results.set(3, results.get(3)+1);
                            scores.set(3, scores.get(3)+c.getScore());
                            break;
                        case Technology:
                            results.set(4, results.get(4)+1);
                            scores.set(4, scores.get(4)+c.getScore());
                            break;
                        case Others:
                            results.set(0, results.get(0)+1);
                            scores.set(0, scores.get(0)+c.getScore());
                            break;
                        default:
                            results.set(0, results.get(0)+1);
                            scores.set(0, scores.get(0)+c.getScore());
                            break;
                    }
                }

                tvTotalCorrecto.setText(String.format(getString(R.string.profile_total_score), (int)Math.round(totalCorrecto/totalCursos)));
                tvTotalCursos.setText(String.format(getString(R.string.profile_total_courses), totalCursos));

                List<RadarEntry> entries = new ArrayList<RadarEntry>();
                List<RadarEntry> scorentries = new ArrayList<>();

                for(int i=0; i<results.size(); i++){
                    entries.add(new RadarEntry(results.get(i), i));
                    scorentries.add(new RadarEntry(results.get(i)*scores.get(i)/100, i));
                }



                RadarDataSet dataSet = new RadarDataSet(entries, getString(R.string.misc_courses)); // add entries to dataset
                dataSet.setColor(getContext().getResources().getColor(R.color.colorBadScore));
                dataSet.setFillColor(getContext().getResources().getColor(R.color.colorBadScore));
                dataSet.setDrawFilled(true);

                RadarDataSet dataSetScore = new RadarDataSet(scorentries, getString(R.string.misc_scores)); // add entries to dataset
                dataSetScore.setColor(getContext().getResources().getColor(R.color.colorPerfectScore));
                dataSetScore.setFillColor(getContext().getResources().getColor(R.color.colorPerfectScore));
                dataSetScore.setFillAlpha(100);
                dataSetScore.setDrawFilled(true);
                //colors.add(Color.GREEN);
                //colors.add(Color.GRAY);

                /*colors.add(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGreenCharts));
                colors.add(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGrayCharts));*/
                //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                //dataSet.setValueTextColor(R.color.colorText); // styling, ...
                List<IRadarDataSet> datasets = new ArrayList<>();
                datasets.add(dataSet);
                datasets.add(dataSetScore);

                RadarData lineData = new RadarData(datasets);
                lineData.setDrawValues(false);

                radarChart.setData(lineData);
                radarChart.setTouchEnabled(false);
                radarChart.getLegend().setEnabled(false);
                //radarChart.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                radarChart.getDescription().setEnabled(false);
                radarChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);
                radarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return CourseType.getType((int)value).toString();
                    }
                });
                radarChart.getXAxis().setTextSize(8f);

                radarChart.getYAxis().setTextSize(6f);
                radarChart.setExtraOffsets(50, 50, 50, 50);
                //radarChart.setCenterText(getString(R.string.misc_courses));
                //pieChart.setHoleRadius(85f);
                //pieChart.setCenterTextRadiusPercent(100.f);
                //pieChart.setDrawEntryLabels(true);
                radarChart.invalidate(); // refresh
            }
        }, getContext());
        getCoursesByStudentDB.execute(idUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        getCoursesByStudentDB.cancel(true);
    }
}
