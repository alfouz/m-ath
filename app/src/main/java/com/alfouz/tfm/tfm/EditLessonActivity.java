package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.EditLessonDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetLessonDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetLessonEntityDB;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

public class EditLessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        final long idLesson = intent.getLongExtra("idLesson", -1);
        final long idCourse = intent.getLongExtra("idCourse", -1);
        final String nameLesson = intent.getStringExtra("nameLesson");

        getSupportActionBar().setTitle(String.format(getResources().getString(R.string.edit_lesson_title),nameLesson));

        new GetLessonDB(new CallbackInterface<Lesson>() {
            @Override
            public void doCallback(Lesson lesson) {
                if(lesson!=null) {
                    TextInputEditText title = findViewById(R.id.eT_edit_lesson_title);
                    TextInputEditText description = findViewById(R.id.eT_edit_lesson_description);
                    SeekBar seekBar = findViewById(R.id.sb_duration);

                    title.setText(lesson.getTitle());
                    description.setText(lesson.getDescription());
                    seekBar.setProgress(lesson.getDuration());

                    getSupportActionBar().setTitle(lesson.getTitle());

                }
            }
        }, this).execute(idLesson);

        Button buttonMathTaskList = findViewById(R.id.mathtask_list_button);
        buttonMathTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MathTaskListActivity.class);
                intent.putExtra("idLesson", idLesson);
                intent.putExtra("nameLesson", nameLesson);
                startActivity(intent);
            }
        });


    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_lesson, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.lesson_menu_update) {


            Intent intent = getIntent();
            final long idLesson = intent.getLongExtra("idLesson", -1);
            final long idCourse = intent.getLongExtra("idCourse", -1);

            new GetLessonEntityDB(new CallbackInterface<LessonEntity>() {
                @Override
                public void doCallback(LessonEntity lessonEntity) {
                    if(lessonEntity!=null) {
                        TextInputEditText title = findViewById(R.id.eT_edit_lesson_title);
                        TextInputEditText description = findViewById(R.id.eT_edit_lesson_description);
                        SeekBar seekBar = findViewById(R.id.sb_duration);

                        lessonEntity.setTitle(title.getText().toString());
                        lessonEntity.setDescription(description.getText().toString());
                        lessonEntity.setDuration(seekBar.getProgress());
                        new EditLessonDB(new CallbackInterface<LessonEntity>() {
                            @Override
                            public void doCallback(LessonEntity course) {
                                Intent intent = new Intent(getApplicationContext(), EditCourseLessonsActivity.class);
                                intent.putExtra("idCourse", idCourse);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }, getApplicationContext()).execute(lessonEntity);
                    }
                }
            }, this).execute(idLesson);


        }
        return super.onOptionsItemSelected(item);
    }
}
