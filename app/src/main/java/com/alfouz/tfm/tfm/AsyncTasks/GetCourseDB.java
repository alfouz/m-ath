package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Util.CourseType;

import java.util.ArrayList;
import java.util.List;

public class GetCourseDB extends AsyncTask<Long, Void, Course> {
    CallbackInterface callback;
    AppDatabase db;

    public GetCourseDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Course doInBackground(Long... integers) {
        long idCourse = integers[0];
        CourseEntity courseEntity = db.courseDao().getCourseById(idCourse);
        List<LessonEntity> listLessonEntities = db.lessonDao().getLessonsByCourse(idCourse);

        List<Lesson> listLessons = new ArrayList<Lesson>();
        for(LessonEntity le : listLessonEntities){
            listLessons.add(new Lesson(le.getId(), le.getTitle(), le.getDescription(), le.getDuration()));
        }
        Course course = new Course(courseEntity.getId(), courseEntity.getCreator(), courseEntity.getTitle(), listLessons, courseEntity.getLevel(), courseEntity.getScore(), courseEntity.getDescription(), courseEntity.isPublic(), CourseType.getType(courseEntity.getType()), courseEntity.getIdRemote());

        return course;
    }

    @Override
    protected void onPostExecute(Course course) {
        callback.doCallback(course);
    }
}
