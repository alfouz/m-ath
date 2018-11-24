package com.alfouz.tfm.tfm;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfouz.tfm.tfm.Adapters.CourseAdapter;
import com.alfouz.tfm.tfm.Adapters.CourseBoardAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetUserCoursesDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private CourseBoardAdapter mAdapter;

    View root;
    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_board, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.list_courses);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        /*courseList.add(new Course("Números Naturales 4º ESO", null, 1f, 8));
        courseList.add(new Course("Números Reales 4º ESO", null, 1.5f, 15));
        courseList.add(new Course("Álgebra 4º ESO", null, 2f, 64));
        courseList.add(new Course("Sucesiones 4º ESO", null, 2.5f, 41));
        courseList.add(new Course("Vectores 4º ESO", null, 4f, 100));
        courseList.add(new Course("Derivadas 4º ESO", null, 5f, 4));
        courseList.add(new Course("Estadística 4º ESO", null, 3.5f, 55));*/

        // Asociamos un adapter (ver más adelante cómo definirlo)
        /*mAdapter = new CourseBoardAdapter(courseList, new CourseAdapter.OnItemClickListener() {
            @Override public void onItemClick(Course item) {
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                intent.putExtra("idCourse", item.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);*/

        FloatingActionButton fab = root.findViewById(R.id.id_floating_button_add_course);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewCourseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Course> courseList = new ArrayList<Course>();

        new GetUserCoursesDB(new CallbackInterface<List<Course>>() {
            @Override
            public void doCallback(List<Course> courses) {
                mAdapter = new CourseBoardAdapter(courses, new CourseBoardAdapter.OnItemClickListener() {
                    @Override public void onItemClick(Course item) {
                        //Log.d("tst", Integer.toString(item.getId()) + " " + item.getTitle());
                        Intent intent = new Intent(getActivity(), EditCourseActivity.class);
                        intent.putExtra("idCourse", item.getId());
                        startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        }, getContext()).execute(1l);
    }
}
