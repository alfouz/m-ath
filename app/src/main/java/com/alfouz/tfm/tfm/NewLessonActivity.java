package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateLessonDB;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

public class NewLessonActivity extends AppCompatActivity {

    private TextInputEditText title;
    private TextInputEditText description;
    private SeekBar duration;

    private long idCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lesson);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_lesson_title));

        title = findViewById(R.id.eT_new_lesson_title);
        description = findViewById(R.id.eT_new_lesson_description);
        duration = findViewById(R.id.sb_duration);

        Intent intent = getIntent();
        idCourse = intent.getLongExtra("idCourse", -1);



        /*Button acceptButton = findViewById(R.id.new_lesson_button);
        acceptButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );*/
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_lesson, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.lesson_menu_create) {
            String titleStr = title.getText().toString();
            String descriptionStr = description.getText().toString();
            String durationStr = Integer.toString(duration.getProgress());
            new CreateLessonDB(new CallbackInterface<LessonEntity>() {
                @Override
                public void doCallback(LessonEntity lesson) {
                    Intent intent = new Intent(getApplicationContext(), EditCourseLessonsActivity.class);
                    intent.putExtra("idCourse", idCourse);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }, getApplicationContext()).execute(Long.toString(idCourse), titleStr, descriptionStr, durationStr, "f");

        }
        return super.onOptionsItemSelected(item);
    }

}
