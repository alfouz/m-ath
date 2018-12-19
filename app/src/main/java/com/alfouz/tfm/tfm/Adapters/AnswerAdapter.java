package com.alfouz.tfm.tfm.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.R;

import java.util.List;

import katex.hourglass.in.mathlib.MathView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private List<MathTaskOption> mathTaskOptionList;

    private final AnswerAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MathTaskOption item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MathView tvMath;
        public CheckBox isCorrect;
        public MathTaskOption mathTaskOptionEntity;
        public View cv;
        //public TextView tvDuration;
        public ViewHolder(final View cv) {
            super(cv);
            tvMath = cv.findViewById(R.id.answerOption);
            isCorrect = cv.findViewById(R.id.checkBoxIsCorrectAnswer);

            this.cv = cv;
        }

        public void bind(final MathTaskOption item, final AnswerAdapter.OnItemClickListener listener) {
            mathTaskOptionEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public AnswerAdapter(List<MathTaskOption> mathTaskOptions, AnswerAdapter.OnItemClickListener listener) {

        mathTaskOptionList = mathTaskOptions;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // Creamos una nueva vista
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_answer, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        AnswerAdapter.ViewHolder vh = new AnswerAdapter.ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final AnswerAdapter.ViewHolder holder, final int position) {
        //TODO Mejorar las muestras

        holder.tvMath.setDisplayText(mathTaskOptionList.get(position).getText());
        //holder.isCorrect.setChecked(mathTaskOptionList.get(position).isCorrect());


        holder.isCorrect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mathTaskOptionList.get(position).setCorrect(isChecked);
            }
        });
        //holder.tvLessons.setText(mathTaskList.get(position).getEcuation());
        /*holder.tvDuration.setText(mathTaskList.get(position).getDuration().toString());
        if(mathTaskList.get(position).isDone()){
            ((CardView)holder.itemView).setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.cardview_background_selected));
        }*/
        holder.bind(mathTaskOptionList.get(position), listener);

    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo en RecyclerViews que implementar filtros o búsquedas
    @Override
    public int getItemCount() {
        return mathTaskOptionList.size();
    }
}
