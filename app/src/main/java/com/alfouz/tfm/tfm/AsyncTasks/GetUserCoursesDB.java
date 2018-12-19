package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;
import com.alfouz.tfm.tfm.Util.CourseType;

import java.util.ArrayList;
import java.util.List;

public class GetUserCoursesDB extends AsyncTask<Long, Void, List<Course>> {
    CallbackInterface callback;
    AppDatabase db;

    public GetUserCoursesDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<Course> doInBackground(Long... integers) {
        Long idUser = integers[0];
        List<CourseEntity> list = db.courseDao().getCoursesByUser(idUser);
        List<Course> courses = new ArrayList<Course>();
        for(CourseEntity c : list){
            List<LessonEntity> listLessonEntity = db.lessonDao().getLessonsByCourse(c.getId());
            List<Lesson> listLesson = new ArrayList<Lesson>();
            int score = 0;
            for(LessonEntity l : listLessonEntity) {
                List<ResultUserLessonEntity> result = db.resultUserLessonDao().getResultUserLessonList(idUser, l.getId());
                ResultUserLessonEntity resultAct = null;
                for(ResultUserLessonEntity r : result){
                    if(resultAct == null){
                        resultAct=r;
                    }else{
                        if(resultAct.getPercentCorrect()<r.getPercentCorrect()){
                            resultAct=r;
                        }
                    }
                }
                score += resultAct!=null?resultAct.getPercentCorrect():0;
                listLesson.add(new Lesson(l.getId(), l.getTitle(), l.getDescription(), l.getDuration()));
            }

            courses.add(new Course(c.getId(), c.getTitle(), listLesson, c.getLevel(), (listLessonEntity.size()!=0)?(score/listLessonEntity.size()):0, c.getDescription(), c.isPublic(), CourseType.getType(c.getType()), c.getIdRemote()));
        }
        return courses;
    }

    @Override
    protected void onPostExecute(List<Course> courses) {
        callback.doCallback(courses);
    }
}
