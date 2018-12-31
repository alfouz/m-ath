package com.alfouz.tfm.tfm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.LessonAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateCourseDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Util.CourseType;

import java.util.ArrayList;
import java.util.List;

public class NewCourseActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private RatingBar ratingBar;
    private Switch switchPublic;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        //ActionBar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_course_title));

        //Get Elements
        title = findViewById(R.id.course_title_input);
        description = findViewById(R.id.course_description_input);
        ratingBar = findViewById(R.id.ratingBarCourseLevel);
        switchPublic = findViewById(R.id.course_public_switch);
        spinner = findViewById(R.id.spType);

        spinner.setAdapter(new ArrayAdapter<CourseType>(this, android.R.layout.simple_spinner_dropdown_item, CourseType.values()));

        //Set Default values
        ratingBar.setRating(2f);
    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_course, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.course_menu_save) {

            if(!title.getText().toString().equals("")) {
                new CreateCourseDB(new CallbackInterface<CourseEntity>() {
                    @Override
                    public void doCallback(CourseEntity course) {
                        Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
                        intent.putExtra("idCourse", course.getId());
                        startActivity(intent);
                        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("goToFragment", 2);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/
                    }
                }, this).execute(Long.toString(MyApplication.getIdUser()), title.getText().toString(), description.getText().toString(), Float.toString(ratingBar.getRating()), switchPublic.isActivated() ? "t" : "f", Integer.toString(((CourseType)spinner.getSelectedItem()).getId()), "-1");
            }else{
                Toast.makeText(getApplicationContext(),R.string.request_insert_data,Toast.LENGTH_SHORT).show();
            }
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
