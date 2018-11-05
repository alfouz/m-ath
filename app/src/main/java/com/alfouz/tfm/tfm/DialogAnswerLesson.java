package com.alfouz.tfm.tfm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfouz.tfm.tfm.DTOs.Lesson;

public class DialogAnswerLesson extends Dialog {

    public Activity activity;
    public Dialog dialog;
    public Button next;
    public TextView content;
    public boolean isCorrect;
    public ImageView image;
    public View.OnClickListener callback;


    public DialogAnswerLesson(Activity a, boolean isCorrect){//, View.OnClickListener callback){
        super(a);
        this.activity = a;
        //this.callback = callback;
        this.isCorrect = isCorrect;
        //this.idCourse = idCourse;
        //this.lesson = lesson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_lesson_answer);
        next = (Button) findViewById(R.id.btnAnswerLessonNext);
        content = (TextView) findViewById(R.id.lessonAnswerDialogText);
        image = findViewById(R.id.lessonAnswerDialogImage);
        image.setImageResource(isCorrect?R.drawable.ic_sentiment_very_satisfied_black_24dp:R.drawable.ic_sentiment_dissatisfied_black_24dp);
        content.setText(isCorrect?R.string.lesson_correct_answer:R.string.lesson_incorrect_answer);
        //next.setOnClickListener(callback);
    }

    public Button getButtonNext(){
        return next;
    }

}
