package com.alfouz.tfm.tfm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.alfouz.tfm.tfm.DTOs.Lesson;

public class DialogInitLesson extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public Button yes, no;
    public TextView content;
    private long idCourse;
    public Lesson lesson;

    public DialogInitLesson(Activity a, long idCourse, Lesson lesson){
        super(a);
        this.activity = a;
        this.idCourse = idCourse;
        this.lesson = lesson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_init_lesson);
        yes = (Button) findViewById(R.id.btnInitLessonYes);
        no = (Button) findViewById(R.id.btnInitLessonNo);
        content = (TextView) findViewById(R.id.idInitLessonDialogText);
        content.setText(String.format(activity.getString(R.string.course_lesson_start_message),lesson.getTasks().size()));
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInitLessonYes:
                Intent intent = new Intent(activity.getApplicationContext(), LessonActivity.class);
                intent.putExtra("idCourse", idCourse);
                intent.putExtra("idLesson", lesson.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                activity.startActivity(intent);
                break;
            case R.id.btnInitLessonNo:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
