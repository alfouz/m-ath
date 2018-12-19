package com.alfouz.tfm.tfm.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.R;
import com.alfouz.tfm.tfm.Util.APIRestUtil;

import java.util.List;

public class CourseShopAdapter  extends RecyclerView.Adapter<CourseShopAdapter.ViewHolder> {

    private List<Course> courseList;

    private final CourseShopAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Course item);
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public RatingBar ratingBar;
        private Course courseEntity;
        public ImageButton downloadButton;
        private ImageView courseIcon;
        public ViewHolder(CardView cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.cardCourseTitle);
            ratingBar = cv.findViewById(R.id.ratingBar);
            downloadButton = cv.findViewById(R.id.buttonDownload);
            courseIcon = cv.findViewById(R.id.courseIconShop);
        }

        public void bind(final Course item, final CourseShopAdapter.OnItemClickListener listener) {
            courseEntity = item;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public CourseShopAdapter(List<Course> courses, CourseShopAdapter.OnItemClickListener listener) {

        courseList = courses;
        this.listener = listener;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public CourseShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // Creamos una nueva vista
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_course_shop, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        CourseShopAdapter.ViewHolder vh = new CourseShopAdapter.ViewHolder(v);
        return vh;
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(final CourseShopAdapter.ViewHolder holder, final int position) {
        //TODO Mejorar las muestras

        holder.tvTitle.setText(courseList.get(position).getTitle());

        holder.ratingBar.setRating(courseList.get(position).getLevel());
        switch(courseList.get(position).getType()){
            case Maths:
                holder.courseIcon.setImageResource(R.drawable.icon_math);
                break;
            case Science:
                holder.courseIcon.setImageResource(R.drawable.icon_science);
                break;
            case Engineering:
                holder.courseIcon.setImageResource(R.drawable.icon_engineering);
                break;
            case Technology:
                holder.courseIcon.setImageResource(R.drawable.icon_tecnology);
                break;
            case Others:
                holder.courseIcon.setImageResource(R.drawable.icon_unknown);
                break;
            default:
                holder.courseIcon.setImageResource(R.drawable.icon_unknown);
                break;
        }
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIRestUtil.getCourseComplete(courseList.get(position).getIdRemote(), holder.itemView.getContext());
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
