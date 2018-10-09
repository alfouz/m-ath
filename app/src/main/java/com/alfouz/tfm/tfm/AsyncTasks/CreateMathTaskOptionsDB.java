package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

public class CreateMathTaskOptionsDB extends AsyncTask<MathTaskOptionEntity, Void, MathTaskOptionEntity[]> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateMathTaskOptionsDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected MathTaskOptionEntity[] doInBackground(MathTaskOptionEntity... mathTaskOptionEntities) {

        for(MathTaskOptionEntity m : mathTaskOptionEntities){
            long id = db.mathTaskOptionDao().insertMathTaskOption(m);
            m.setId(id);
        }

        return mathTaskOptionEntities;
    }

    @Override
    protected void onPostExecute(MathTaskOptionEntity... mathTaskOptionEntities) {
        callback.doCallback(mathTaskOptionEntities);
    }
}
