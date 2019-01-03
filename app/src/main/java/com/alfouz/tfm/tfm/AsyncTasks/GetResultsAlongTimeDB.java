package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetResultsAlongTimeDB extends AsyncTask<Long, Void, List<Integer>> {

    CallbackInterface callback;
    AppDatabase db;

    public GetResultsAlongTimeDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected List<Integer> doInBackground(Long... integers) {
        long idUser = integers[0];
        long secsByDay = 86400000;
        long days = integers[1]; //días que queremos buscar
        //one day = 86400 secs - 7 days = 604800
        Log.d("tst", Long.toString(secsByDay*days));
        Log.d("tst", Long.toString(days));
        List<ResultUserLessonEntity> list = db.resultUserLessonDao().getResultsAlongTime(idUser, days*secsByDay);
        List<Integer> results = new ArrayList<>();
        for(int i=0; i<days; i++){
            results.add(0);
        }
        Date actDate = new Date();
        for(ResultUserLessonEntity r : list){
            long actTimestamp = (actDate.getTime())-(r.getTimestamp().getTime()); //Segundos de diferencia desde el actual
            int index = (int)Math.floor(actTimestamp/secsByDay);
            Integer valueAct = results.get(index);
            results.set(index, valueAct+1);

        }
        return results;
    }

    @Override
    protected void onPostExecute(List<Integer> options) {
        callback.doCallback(options);
    }
}