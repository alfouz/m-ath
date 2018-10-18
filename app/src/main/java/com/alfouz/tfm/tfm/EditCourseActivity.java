package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.EditCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseEntityDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

public class EditCourseActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private RatingBar ratingBar;
    private Switch switchPublic;

    private long idCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_course_title));

        title = findViewById(R.id.course_title_input);
        description = findViewById(R.id.course_description_input);
        ratingBar = findViewById(R.id.ratingBarCourseLevel);
        switchPublic = findViewById(R.id.course_public_switch);

        Intent intent = getIntent();
        idCourse = intent.getLongExtra("idCourse", -1);

        new GetCourseDB(new CallbackInterface<Course>() {
            @Override
            public void doCallback(Course course) {
                if(course!=null) {
                    title.setText(course.getTitle());
                    description.setText(course.getDescription());
                    ratingBar.setRating(course.getLevel());
                    switchPublic.setChecked(course.isPublic());
                }
            }
        }, this).execute(idCourse);

        Button buttonLessons = findViewById(R.id.buttonLessonsList);
        buttonLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditCourseLessonsActivity.class);
                intent.putExtra("idCourse", idCourse);
                startActivity(intent);
            }
        });

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_course, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.course_menu_update) {

            new GetCourseEntityDB(new CallbackInterface<CourseEntity>() {
                @Override
                public void doCallback(CourseEntity courseEntity) {
                    if(courseEntity!=null) {
                        courseEntity.setTitle(title.getText().toString());
                        courseEntity.setDescription(description.getText().toString());
                        courseEntity.setPublic(switchPublic.isChecked());
                        courseEntity.setLevel(ratingBar.getRating());
                        new EditCourseDB(new CallbackInterface<CourseEntity>() {
                            @Override
                            public void doCallback(CourseEntity course) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("goToFragment", 2);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }, getApplicationContext()).execute(courseEntity);
                    }
                }
            }, this).execute(idCourse);


        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("goToFragment", 2);
        startActivity(intent);
        //super.onBackPressed();
    }*/
}
