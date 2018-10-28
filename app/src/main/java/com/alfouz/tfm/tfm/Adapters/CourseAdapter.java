package com.alfouz.tfm.tfm.Adapters;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courseList;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvLessons;
        public PieChart chart;
        public RatingBar ratingBar;
        private Course courseEntity;
        private ImageView courseIcon;

        public ViewHolder(CardView cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.cardCourseTitle);
            tvLessons = cv.findViewById(R.id.cardCourseLessons);
            chart = cv.findViewById(R.id.chart);
            ratingBar = cv.findViewById(R.id.ratingBar);
            courseIcon = cv.findViewById(R.id.courseIcon);

        }

        public void bind(final Course item, final OnItemClickListener listener) {
            courseEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public CourseAdapter(List<Course> courses,  OnItemClickListener listener) {

        courseList = courses;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_course, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO Mejorar las muestras

        holder.tvTitle.setText(courseList.get(position).getTitle());
        holder.tvLessons.setText(String.format(holder.itemView.getContext().getString(R.string.misc_comp_lessons), courseList.get(position).getLessons().size()));

        switch(courseList.get(position).getType()){
            case Maths:
                holder.courseIcon.setImageResource(R.drawable.icon_math);
                break;
            case Physics:
                holder.courseIcon.setImageResource(R.drawable.icon_physic);
                break;
            case Others:
                holder.courseIcon.setImageResource(R.drawable.icon_unknown);
                break;
            default:
                holder.courseIcon.setImageResource(R.drawable.icon_unknown);
                break;
        }

        holder.ratingBar.setRating(courseList.get(position).getLevel());

        List<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(new PieEntry(courseList.get(position).getScore()));
        entries.add(new PieEntry(100-courseList.get(position).getScore()));

        PieDataSet dataSet = new PieDataSet(entries, ""); // add entries to dataset
        List<Integer> colors = new ArrayList<Integer>();

        //colors.add(Color.GREEN);
        //colors.add(Color.GRAY);

        colors.add(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGreenCharts));
        colors.add(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorGrayCharts));
        dataSet.setColors(colors);
        dataSet.setValueTextColor(R.color.colorText); // styling, ...

        PieData lineData = new PieData(dataSet);
        lineData.setDrawValues(false);

        holder.chart.setData(lineData);
        holder.chart.setTouchEnabled(false);
        holder.chart.getLegend().setEnabled(false);
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setCenterText(String.valueOf(courseList.get(position).getScore())+"%");
        holder.chart.setHoleRadius(85f);
        holder.chart.setCenterTextRadiusPercent(100.f);
        holder.chart.setDrawEntryLabels(false);
        holder.chart.invalidate(); // refresh
        holder.bind(courseList.get(position), listener);
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo en RecyclerViews que implementar filtros o búsquedas
    @Override
    public int getItemCount() {
        return courseList.size();
    }

}
