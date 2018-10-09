package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import java.util.List;

public class GetMathTaskOptionDB extends AsyncTask<Long, Void, MathTaskOptionEntity> {

    CallbackInterface callback;
    AppDatabase db;

    public GetMathTaskOptionDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected MathTaskOptionEntity doInBackground(Long... integers) {
        long idMathTaskOption = integers[0];
        MathTaskOptionEntity mathTaskOption = db.mathTaskOptionDao().getMathTaskOptionById(idMathTaskOption);
        return mathTaskOption;
    }

    @Override
    protected void onPostExecute(MathTaskOptionEntity option) {
        callback.doCallback(option);
    }

}
