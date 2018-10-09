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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alfouz.tfm.tfm.Adapters.LessonAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateCourseDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

import java.util.ArrayList;
import java.util.List;

public class NewCourseActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private RatingBar ratingBar;
    private Switch switchPublic;

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
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("goToFragment", 2);
                        startActivity(intent);
                    }
                }, this).execute("1", title.getText().toString(), description.getText().toString(), Float.toString(ratingBar.getRating()), switchPublic.isActivated() ? "t" : "f");
            }else{
                Toast.makeText(getApplicationContext(),R.string.request_insert_data,Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("goToFragment", 2);
        startActivity(intent);
        //super.onBackPressed();
    }
}
