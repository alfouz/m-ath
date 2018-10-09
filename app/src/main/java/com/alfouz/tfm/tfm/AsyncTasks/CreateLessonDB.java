package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;


public class CreateLessonDB extends AsyncTask<String, Void, LessonEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected LessonEntity doInBackground(String... strings) {
        int idCourse = Integer.parseInt(strings[0]);
        String title = strings[1];
        String description = strings[2];
        int duration = Integer.parseInt(strings[3]);
        boolean isDone;

        if(strings[4].equals("t")){
            isDone = true;
        }else{
            isDone = false;
        }

        LessonEntity lesson = new LessonEntity();

        lesson.setCourse(idCourse);
        lesson.setDuration(duration);
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setDone(isDone);

        long id = db.lessonDao().insertLesson(lesson);

        lesson.setId(id);
        return lesson;
    }

    @Override
    protected void onPostExecute(LessonEntity course) {
        callback.doCallback(course);
    }
}
