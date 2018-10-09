package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import java.util.List;

public class GetMathTaskOptionByMathTaskDB extends AsyncTask<Long, Void, List<MathTaskOptionEntity>> {

    CallbackInterface callback;
    AppDatabase db;

    public GetMathTaskOptionByMathTaskDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<MathTaskOptionEntity> doInBackground(Long... integers) {
        long idMathTask = integers[0];
        List<MathTaskOptionEntity> list = db.mathTaskOptionDao().getMathTaskOptionByMathTask(idMathTask);
        return list;
    }

    @Override
    protected void onPostExecute(List<MathTaskOptionEntity> options) {
        callback.doCallback(options);
    }

}
