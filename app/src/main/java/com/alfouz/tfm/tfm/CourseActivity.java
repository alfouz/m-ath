package com.alfouz.tfm.tfm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alfouz.tfm.tfm.Adapters.LessonAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LessonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final long idCourse = intent.getLongExtra("idCourse", -1);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_lessons);

        // Usar esta línea para mejorar el rendimiento
        // si sabemos que el contenido no va a afectar
        // el tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);




        final Context context = this;
        new GetCourseDB(new CallbackInterface<Course>() {
            @Override
            public void doCallback(Course course) {

                getSupportActionBar().setTitle(course.getTitle());

                TextView tvDescription = findViewById(R.id.tvCourseDescription);
                TextView tvOwner = findViewById(R.id.tvCourseOwner);
                TextView tvVisibility = findViewById(R.id.tvCourseVisibility);
                RatingBar ratingBar = findViewById(R.id.ratingBarCourseLevel);

                tvDescription.setText(course.getDescription());
                //Warning
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tvDescription.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                tvOwner.setText("Nombre-Propietario");
                tvVisibility.setText(course.isPublic()?"Pública":"Privada");
                ratingBar.setRating(course.getLevel());

                mAdapter = new LessonAdapter(course.getLessons(), new LessonAdapter.OnItemClickListener() {
                    @Override public void onItemClick(final Lesson item) {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Intent intent = new Intent(getApplicationContext(), LessonActivity.class);
                                        intent.putExtra("idCourse", idCourse);
                                        intent.putExtra("idLesson", item.getId());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(intent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(String.format(getString(R.string.course_lesson_start_message), item.getDuration())).setPositiveButton(getString(R.string.misc_yes), dialogClickListener)
                                .setNegativeButton(getString(R.string.misc_no), dialogClickListener).show();

                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        },getApplicationContext()).execute(idCourse);


        /*TextView tvDescription = findViewById(R.id.tvCourseDescription);
        TextView tvOwner = findViewById(R.id.tvCourseOwner);
        TextView tvVisibility = findViewById(R.id.tvCourseVisibility);
        RatingBar ratingBar = findViewById(R.id.ratingBarCourseLevel);

        tvDescription.setText("Curso donde se explica el álgebra aplicada a problemas del mundo real desde un enfoque de un alumno de 4º de ESO.");
        //Warning
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvDescription.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        tvOwner.setText("Adrián López Fouz");
        tvVisibility.setText("Pública");
        ratingBar.setRating(2f);*/

        /*List<Lesson> lessonList = new ArrayList<Lesson>();
        lessonList.add(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true), "Introducción", "Breve introducción al álgebra", null, 3));
        lessonList.add(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true),"Ecuaciones", "Soluciones a ecuaciones básicas", null, 2));
        lessonList.add(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true),"Inecuaciones", "Aprendiendo sobre desigualdades", null, 5));
        lessonList.add(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true),"Sistemas de ecuaciones", "Resolver varias ecuaciones relacionadas", null, 6));
        lessonList.add(new Lesson(new Course("Números Naturales 4º ESO", null, 1f, 8, "Estructura de los números naturales en 4º ESO", true),"Teorema de Gauss", "Método para resolver sistemas de ecuaciones", null, 6));
        lessonList.get(0).setDone(true);
        lessonList.get(2).setDone(true);*/

        /*final Context context = this;
        mAdapter = new LessonAdapter(lessonList, new LessonAdapter.OnItemClickListener() {
            @Override public void onItemClick(final Lesson item) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(getApplicationContext(), LessonActivity.class);
                                intent.putExtra("idLesson", item.getId());
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(String.format(getString(R.string.course_lesson_start_message), item.getDuration())).setPositiveButton(getString(R.string.misc_yes), dialogClickListener)
                        .setNegativeButton(getString(R.string.misc_no), dialogClickListener).show();

            }
        });
        mRecyclerView.setAdapter(mAdapter);*/

    }
}
