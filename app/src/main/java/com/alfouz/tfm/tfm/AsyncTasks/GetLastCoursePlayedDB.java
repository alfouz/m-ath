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

public class GetLastCoursePlayedDB extends AsyncTask<Long, Void, Course> {

    CallbackInterface callback;
    AppDatabase db;

    public GetLastCoursePlayedDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Course doInBackground(Long... integers) {
        long idUser = integers[0];
        ResultUserLessonEntity result = db.resultUserLessonDao().getLastResultUser(idUser);
        if(result!=null) {
            CourseEntity courseEntity = db.courseDao().getCourseById(db.lessonDao().getLessonById(result.getLesson()).getCourse());
            List<LessonEntity> listLessonEntities = db.lessonDao().getLessonsByCourse(courseEntity.getId());

            List<Lesson> listLessons = new ArrayList<Lesson>();

            List<LessonEntity> listLessonEntity = db.lessonDao().getLessonsByCourse(courseEntity.getId());
            List<Lesson> listLesson = new ArrayList<Lesson>();
            int score = 0;
            for(LessonEntity l : listLessonEntity) {
                List<ResultUserLessonEntity> result2 = db.resultUserLessonDao().getResultUserLessonList(idUser, l.getId());
                ResultUserLessonEntity resultAct = null;
                for(ResultUserLessonEntity r : result2){
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
            Course course = new Course(courseEntity.getId(), courseEntity.getCreator(), courseEntity.getTitle(), listLessons, courseEntity.getLevel(),  (listLessonEntity.size()!=0)?(score/listLessonEntity.size()):0, courseEntity.getDescription(), courseEntity.isPublic(), CourseType.getType(courseEntity.getType()), courseEntity.getIdRemote());
            return course;
        }else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(Course course) {
        callback.doCallback(course);
    }
}
