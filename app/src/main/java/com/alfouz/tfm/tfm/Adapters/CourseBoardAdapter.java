package com.alfouz.tfm.tfm.Adapters;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.R;
import com.alfouz.tfm.tfm.StatsActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class CourseBoardAdapter  extends RecyclerView.Adapter<CourseBoardAdapter.ViewHolder> {

    private List<Course> courseList;

    private final CourseBoardAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvLessons;
        public RatingBar ratingBar;
        private Course courseEntity;
        public ImageButton statButton;
        public ViewHolder(CardView cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.cardCourseTitle);
            tvLessons = cv.findViewById(R.id.cardCourseLessons);
            ratingBar = cv.findViewById(R.id.ratingBar);
            statButton = cv.findViewById(R.id.idButtonCourseStat);

        }

        public void bind(final Course item, final CourseBoardAdapter.OnItemClickListener listener) {
            courseEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public CourseBoardAdapter(List<Course> courses,  CourseBoardAdapter.OnItemClickListener listener) {

        courseList = courses;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public CourseBoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_course_board, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        CourseBoardAdapter.ViewHolder vh = new CourseBoardAdapter.ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final CourseBoardAdapter.ViewHolder holder, int position) {
        //TODO Mejorar las muestras

        holder.tvTitle.setText(courseList.get(position).getTitle());
        holder.tvLessons.setText(String.format(holder.itemView.getContext().getString(R.string.misc_comp_lessons), courseList.get(position).getLessons().size()));

        holder.ratingBar.setRating(courseList.get(position).getLevel());

        holder.statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), StatsActivity.class);
                intent.putExtra("idCourse", holder.courseEntity.getId());
                holder.itemView.getContext().startActivity(intent);
                //Toast.makeText(holder.itemView.getContext(), holder.itemView.getContext().getText(R.string.misc_not_implemented_yet), Toast.LENGTH_SHORT).show();
            }
        });
        holder.bind(courseList.get(position), listener);
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo en RecyclerViews que implementar filtros o búsquedas
    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
