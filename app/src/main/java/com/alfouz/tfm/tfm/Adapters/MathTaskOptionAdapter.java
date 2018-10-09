package com.alfouz.tfm.tfm.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.DeleteMathTaskOptionDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetMathTaskOptionDB;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;
import com.alfouz.tfm.tfm.R;

import java.util.List;

import katex.hourglass.in.mathlib.MathView;

//import io.github.kexanie.library.MathView;

public class MathTaskOptionAdapter extends RecyclerView.Adapter<MathTaskOptionAdapter.ViewHolder> {
    private List<MathTaskOption> mathTaskOptionList;

    private final MathTaskOptionAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MathTaskOption item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MathView tvMath;
        public CheckBox isCorrect;
        public ImageButton deleteButton;
        //public TextView tvEquation;
        public MathTaskOption mathTaskOptionEntity;
        public CardView cv;
        //public TextView tvDuration;
        public ViewHolder(final CardView cv) {
            super(cv);
            tvMath = cv.findViewById(R.id.newMathViewEquation);
            isCorrect = cv.findViewById(R.id.cb_mathtaskoption);
            deleteButton = cv.findViewById(R.id.delete_mathtaskoption_button);

            this.cv = cv;
        }

        public void bind(final MathTaskOption item, final MathTaskOptionAdapter.OnItemClickListener listener) {
            mathTaskOptionEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public MathTaskOptionAdapter(List<MathTaskOption> mathTaskOptions, MathTaskOptionAdapter.OnItemClickListener listener) {

        mathTaskOptionList = mathTaskOptions;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public MathTaskOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_mathtaskoption, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        MathTaskOptionAdapter.ViewHolder vh = new MathTaskOptionAdapter.ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final MathTaskOptionAdapter.ViewHolder holder, final int position) {
        //TODO Mejorar las muestras

        holder.tvMath.setDisplayText(mathTaskOptionList.get(position).getText().length()<30?mathTaskOptionList.get(position).getText():mathTaskOptionList.get(position).getText().substring(0,30)+"...");
        holder.isCorrect.setChecked(mathTaskOptionList.get(position).isCorrect());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mathTaskOptionList.remove(position);
                notifyDataSetChanged();

                new GetMathTaskOptionDB(new CallbackInterface<MathTaskOptionEntity>() {
                    @Override
                    public void doCallback(MathTaskOptionEntity option) {
                        if(option!=null){
                            new DeleteMathTaskOptionDB(new CallbackInterface<Boolean>() {
                                @Override
                                public void doCallback(Boolean isDeleted) {
                                    //Deleted
                                }
                            }, holder.itemView.getContext()).execute(option);
                        }
                    }
                }, holder.itemView.getContext());

            }
        });

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
