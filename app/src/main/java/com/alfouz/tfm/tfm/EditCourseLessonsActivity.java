package com.alfouz.tfm.tfm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alfouz.tfm.tfm.Adapters.LessonAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

import java.util.ArrayList;
import java.util.List;

public class EditCourseLessonsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LessonAdapter mAdapter;

    long idCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_lessons);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_course_lessons_title));


        Intent intent = getIntent();
        idCourse = intent.getLongExtra("idCourse", -1);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_lessons);



        FloatingActionButton fab = findViewById(R.id.id_floating_button_add_lesson);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewLessonActivity.class);
                intent.putExtra("idCourse", idCourse);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetCourseLessonsDB(new CallbackInterface<List<LessonEntity>>() {
            @Override
            public void doCallback(List<LessonEntity> listLessonentity) {
                List<Lesson> lessonList = new ArrayList<Lesson>();
                for(LessonEntity l : listLessonentity){
                    lessonList.add(new Lesson(l.getId(), l.getTitle(), l.getDescription(), l.getDuration()));
                }
                mAdapter = new LessonAdapter(lessonList, new LessonAdapter.OnItemClickListener() {
                    @Override public void onItemClick(final Lesson item) {
                        Intent intent = new Intent(getApplicationContext(), EditLessonActivity.class);
                        intent.putExtra("idCourse", idCourse);
                        intent.putExtra("idLesson", item.getId());
                        startActivity(intent);

                    }
                });
                mRecyclerView.setAdapter(mAdapter);
            }
        }, this).execute(idCourse);
    }
}
