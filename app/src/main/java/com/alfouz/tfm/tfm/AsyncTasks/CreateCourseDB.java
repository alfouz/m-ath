package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

public class CreateCourseDB extends AsyncTask<String, Void, CourseEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateCourseDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected CourseEntity doInBackground(String... strings) {
        int idUser = Integer.parseInt(strings[0]);
        String title = strings[1];
        String description = strings[2];
        float level = Float.parseFloat(strings[3]);
        boolean isPublic;

        if(strings[4].equals("t")){
            isPublic = true;
        }else{
            isPublic = false;
        }
        int type = Integer.parseInt(strings[5]);

        CourseEntity course = new CourseEntity();

        course.setCreator(idUser);
        course.setLevel(level);
        course.setTitle(title);
        course.setDescription(description);
        course.setPublic(isPublic);
        course.setScore(0);
        course.setType(type);

        long id = db.courseDao().insertCourse(course);

        course.setId(id);
        return course;
    }

    @Override
    protected void onPostExecute(CourseEntity course) {
        callback.doCallback(course);
    }
}
