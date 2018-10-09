package com.alfouz.tfm.tfm.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder>{

    private List<Lesson> lessonList;

    private final LessonAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Lesson item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvLessons;
        public Lesson lessonEntity;
        public CardView cv;
        //public TextView tvDuration;
        public ImageButton buttonDelete;
        public ViewHolder(CardView cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.cardMathTaskOptionDescription);
            tvLessons = cv.findViewById(R.id.cardLessonTasks);
            //tvDuration = cv.findViewById(R.id.cardLessonDuration);
            buttonDelete = cv.findViewById(R.id.deleteLessonButton);
            this.cv = cv;
        }

        public void bind(final Lesson item, final LessonAdapter.OnItemClickListener listener) {
            lessonEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public LessonAdapter(List<Lesson> lessons,  LessonAdapter.OnItemClickListener listener) {

        lessonList = lessons;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public LessonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_lesson, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //TODO Mejorar las muestras

        holder.tvTitle.setText(lessonList.get(position).getTitle());
        holder.tvLessons.setText(lessonList.get(position).getDescription());
        //holder.tvDuration.setText(lessonList.get(position).getDuration().toString());
        if(lessonList.get(position).isDone()){
            ((CardView)holder.itemView).setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.cardview_background_selected));
        }

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), R.string.misc_not_implemented_yet, Toast.LENGTH_SHORT).show();
            }
        });
        holder.bind(lessonList.get(position), listener);

    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo en RecyclerViews que implementar filtros o búsquedas
    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}
