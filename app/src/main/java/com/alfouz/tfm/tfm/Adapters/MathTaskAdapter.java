package com.alfouz.tfm.tfm.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.DeleteMathTaskDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetMathTaskDB;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.R;

import java.util.List;

public class MathTaskAdapter extends RecyclerView.Adapter<MathTaskAdapter.ViewHolder> {
    private List<MathTask> mathTaskList;

    private final MathTaskAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MathTask item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        //public TextView tvEquation;
        public MathTask mathTaskEntity;
        public CardView cv;
        public ImageButton deleteButton;
        public TextView tvSubtitle;
        //public TextView tvDuration;
        public ViewHolder(CardView cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.cardMathTaskOptionDescription);
            deleteButton = cv.findViewById(R.id.deleteMathTaskButton);
            tvSubtitle = cv.findViewById(R.id.cardMathTaskOptionSubtitle);
            //tvLessons = cv.findViewById(R.id.cardMathTaskTasks);
            //tvDuration = cv.findViewById(R.id.cardLessonDuration);
            this.cv = cv;
        }

        public void bind(final MathTask item, final MathTaskAdapter.OnItemClickListener listener) {
            mathTaskEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public MathTaskAdapter(List<MathTask> mathTasks, MathTaskAdapter.OnItemClickListener listener) {

        mathTaskList = mathTasks;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public MathTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_mathtask, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //TODO Mejorar las muestras

        holder.tvTitle.setText(mathTaskList.get(position).getDescription().length()<30?mathTaskList.get(position).getDescription():mathTaskList.get(position).getDescription().substring(0,30)+"...");

        new GetMathTaskDB(new CallbackInterface<MathTask>() {
            @Override
            public void doCallback(MathTask mathTask) {
                if(mathTask!=null){
                    holder.tvSubtitle.setText(String.format(holder.itemView.getContext().getString(R.string.misc_comp_options),(mathTask.getAnswers()!=null)?mathTask.getAnswers().size():0));
                }
            }
        }, holder.itemView.getContext()).execute(mathTaskList.get(position).getId());

        //holder.tvLessons.setText(mathTaskList.get(position).getEcuation());
        /*holder.tvDuration.setText(mathTaskList.get(position).getDuration().toString());
        if(mathTaskList.get(position).isDone()){
            ((CardView)holder.itemView).setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.cardview_background_selected));
        }*/


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new GetMathTaskDB(new CallbackInterface<MathTask>() {
                    @Override
                    public void doCallback(MathTask mathTask) {
                        if(mathTask!=null){
                            MathTaskEntity mte = new MathTaskEntity();
                            mte.setId(mathTask.getId());
                            mte.setEcuation(mathTask.getEcuation());
                            mte.setDescription(mathTask.getEcuation());
                            mte.setLesson(mathTask.getLesson());

                            new DeleteMathTaskDB(new CallbackInterface<Boolean>() {
                                @Override
                                public void doCallback(Boolean isDeleted) {
                                    mathTaskList.remove(position);
                                    notifyDataSetChanged();
                                }
                            }, holder.itemView.getContext()).execute(mte);
                        }
                    }
                }, holder.itemView.getContext()).execute(mathTaskList.get(position).getId());

            }
        });

        holder.bind(mathTaskList.get(position), listener);



    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo en RecyclerViews que implementar filtros o búsquedas
    @Override
    public int getItemCount() {
        return mathTaskList.size();
    }
}
