package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import java.util.ArrayList;
import java.util.List;

public class GetLessonDB extends AsyncTask<Long, Void, Lesson> {
    CallbackInterface callback;
    AppDatabase db;

    public GetLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Lesson doInBackground(Long... integers) {
        long idLesson = integers[0];
        LessonEntity lessonEntity = db.lessonDao().getLessonById(idLesson);
        List<MathTaskEntity> listMathTaskEntities = db.mathTaskDao().getMathTasksByLesson(idLesson);

        List<MathTask> listMathTask = new ArrayList<MathTask>();
        for(MathTaskEntity mte : listMathTaskEntities){
            List<MathTaskOptionEntity> optionsEntity = db.mathTaskOptionDao().getMathTaskOptionByMathTask(mte.getId());
            List<MathTaskOption> options = new ArrayList<>();
            for(MathTaskOptionEntity mtoe : optionsEntity){
                options.add(new MathTaskOption(mtoe.getId(), mtoe.getText(), mtoe.isCorrect()));
            }
            listMathTask.add(new MathTask(mte.getId(), idLesson, null, mte.getEcuation() ,mte.getDescription(), options));

        }

        Lesson lesson = new Lesson(lessonEntity.getId(), lessonEntity.getCourse(), lessonEntity.getTitle(), lessonEntity.getDescription(), listMathTask, lessonEntity.getDuration(), lessonEntity.getIdRemote());

        return lesson;
    }

    @Override
    protected void onPostExecute(Lesson lesson) {
        callback.doCallback(lesson);
    }
}
