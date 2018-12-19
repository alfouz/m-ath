package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;

public class CreateMathTaskDB extends AsyncTask<String, Void, MathTaskEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateMathTaskDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected MathTaskEntity doInBackground(String... strings) {
        int idLesson = Integer.parseInt(strings[0]);
        String description = strings[1];
        String ecuation = strings[2];
        long idRemote = Long.parseLong(strings[3]);

        MathTaskEntity mathTaskEntity = new MathTaskEntity();

        mathTaskEntity.setLesson(idLesson);
        mathTaskEntity.setEcuation(ecuation);
        mathTaskEntity.setDescription(description);
        mathTaskEntity.setIdRemote(idRemote);

        long id = db.mathTaskDao().insertMathTask(mathTaskEntity);

        mathTaskEntity.setId(id);
        return mathTaskEntity;
    }

    @Override
    protected void onPostExecute(MathTaskEntity mathTaskEntity) {
        callback.doCallback(mathTaskEntity);
    }
}
