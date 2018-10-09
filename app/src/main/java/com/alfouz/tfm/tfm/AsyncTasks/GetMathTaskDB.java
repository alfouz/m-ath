package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import java.util.ArrayList;
import java.util.List;

public class GetMathTaskDB extends AsyncTask<Long, Void, MathTask> {
    CallbackInterface callback;
    AppDatabase db;

    public GetMathTaskDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected MathTask doInBackground(Long... integers) {
        long idMathTask = integers[0];
        MathTaskEntity mathTaskEntity = db.mathTaskDao().getMathTaskById(idMathTask);
        List<MathTaskOptionEntity> listMathTaskOptionEntities = db.mathTaskOptionDao().getMathTaskOptionByMathTask(idMathTask);

        List<MathTaskOption> listMathTaskOption = new ArrayList<MathTaskOption>();
        for(MathTaskOptionEntity mteo : listMathTaskOptionEntities){
            listMathTaskOption.add(new MathTaskOption(mteo.getId(), mteo.getText(), mteo.isCorrect()));
        }


        MathTask mathTask = new MathTask(mathTaskEntity.getId(), mathTaskEntity.getLesson(), null, mathTaskEntity.getEcuation(), mathTaskEntity.getDescription(), listMathTaskOption);

        return mathTask;
    }

    @Override
    protected void onPostExecute(MathTask mathTask) {
        callback.doCallback(mathTask);
    }
}
