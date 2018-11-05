package com.alfouz.tfm.tfm;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfouz.tfm.tfm.DTOs.Lesson;

public class DialogEndLesson extends Dialog implements
        android.view.View.OnClickListener{

    public Activity activity;
    public Dialog dialog;
    public Button confirm;
    public TextView content;
    private long idCourse;
    public Lesson lesson;
    public ImageView image;
    public int score;

    public DialogEndLesson(Activity a, long idCourse, Lesson lesson, int score){
        super(a);
        this.activity = a;
        this.idCourse = idCourse;
        this.lesson = lesson;
        this.score = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_end_lesson);
        confirm = (Button) findViewById(R.id.btnEndLessonConfirm);
        content = (TextView) findViewById(R.id.lessonEndDialogText);
        image = findViewById(R.id.lessonEndDialogImage);
        if(score>80){
            image.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
            content.setText(String.format(activity.getString(R.string.lesson_result_excelent),score));
        }else{
            if(score>50){
                image.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
                content.setText(String.format(activity.getString(R.string.lesson_result_good),score));

            }else{
                if(score>30){
                    image.setImageResource(R.drawable.ic_sentiment_neutral_black_24dp);
                    content.setText(String.format(activity.getString(R.string.lesson_result_regular),score));

                }else{
                    if (score > 10) {
                        image.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                        content.setText(String.format(activity.getString(R.string.lesson_result_bad),score));

                    }else{
                        image.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
                        content.setText(String.format(activity.getString(R.string.lesson_result_horrible),score));

                    }
                }
            }
        }
        confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        activity.finish();
    }
}
