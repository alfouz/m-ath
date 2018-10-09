package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

public class DeleteMathTaskDB extends AsyncTask<MathTaskEntity, Void, Boolean> {
    CallbackInterface callback;
    AppDatabase db;

    public DeleteMathTaskDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(MathTaskEntity... mathTaskEntities) {

        try {
            db.mathTaskDao().deleteMathTask(mathTaskEntities[0]);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean isDone) {
        callback.doCallback(isDone);
    }
}
