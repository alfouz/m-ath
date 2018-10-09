package com.alfouz.tfm.tfm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfouz.tfm.tfm.Adapters.CourseAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetUserCoursesDB;
import com.alfouz.tfm.tfm.DTOs.Course;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeskFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CourseAdapter mAdapter;

    View root;
    public DeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_desk, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.list_courses);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);



        new GetUserCoursesDB(new CallbackInterface<List<Course>>() {
            @Override
            public void doCallback(List<Course> courses) {
                /*List<Course> courseList = new ArrayList<Course>();
                courseList.add(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true));
                courseList.add(new Course("Números Reales 4º ESO", null, 1.5f, 15, "Estructura de los números reales en 4º ESO", true));
                courseList.add(new Course("Álgebra 4º ESO", null, 2f, 64, "Estructura del álgebra en 4º ESO", true));
                courseList.add(new Course("Sucesiones 4º ESO", null, 2.5f, 41, "Estructura de las sucesiones en 4º ESO", true));
                courseList.add(new Course("Vectores 4º ESO", null, 4f, 100, "Estructura de los vectores en 4º ESO", true));
                courseList.add(new Course("Derivadas 4º ESO", null, 5f, 4, "Estructura de las derivadas en 4º ESO", true));
                courseList.add(new Course("Estadística 4º ESO", null, 3.5f, 55, "Estructura de la estadística en 4º ESO", true));

                courseList.addAll(courses);*/
                // Asociamos un adapter (ver más adelante cómo definirlo)
                mAdapter = new CourseAdapter(courses, new CourseAdapter.OnItemClickListener() {
                    @Override public void onItemClick(Course item) {
                        Intent intent = new Intent(getActivity(), CourseActivity.class);
                        intent.putExtra("idCourse", item.getId());
                        startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        },getContext()).execute(1l);


        return root;
    }

}
