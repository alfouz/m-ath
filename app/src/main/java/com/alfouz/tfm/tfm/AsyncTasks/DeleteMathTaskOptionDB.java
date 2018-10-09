package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

public class DeleteMathTaskOptionDB extends AsyncTask<MathTaskOptionEntity, Void, Boolean> {
    CallbackInterface callback;
    AppDatabase db;

    public DeleteMathTaskOptionDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected Boolean doInBackground(MathTaskOptionEntity... mathTaskOptionEntities) {

        try {
            db.mathTaskOptionDao().deleteMathTaskOption(mathTaskOptionEntities[0]);
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
