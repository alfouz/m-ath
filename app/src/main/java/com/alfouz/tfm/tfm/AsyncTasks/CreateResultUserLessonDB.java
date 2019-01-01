package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;
import com.alfouz.tfm.tfm.MyApplication;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CreateResultUserLessonDB extends AsyncTask<Long, Void, ResultUserLessonEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CreateResultUserLessonDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }


    @Override
    protected ResultUserLessonEntity doInBackground(Long... longs) {
        long idUser = longs[0];
        long idLesson = longs[1];
        long percentCorrect = longs[2];
        Date tryNumber = new Date();

        ResultUserLessonEntity result = new ResultUserLessonEntity();

        result.setUser(idUser);
        result.setLesson(idLesson);
        result.setTimestamp(tryNumber);
        result.setPercentCorrect(percentCorrect);

        db.resultUserLessonDao().insertResultUserLesson(result);

        try {
            LessonEntity l = db.lessonDao().getLessonById(idLesson);
            sendResults(idUser, l.getIdRemote(), tryNumber.getTime(), percentCorrect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ResultUserLessonEntity result) {
        callback.doCallback(result);
    }

    private void sendResults(long user, long lesson, long timestamp, long percentcorrect) throws JSONException {
        final RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        //BUSCANDO SI EXISTE EN REMOTO ---------------------------------------------------------
        JSONObject params = new JSONObject();

        params.put("user", user);
        params.put("lesson", lesson);
        params.put("timestamp", timestamp);
        params.put("percentcorrect", percentcorrect);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, //GET or POST
                APIRestUtil.getResults() + "/create", //URL
                params, //Parameters
                new Response.Listener<JSONObject>() { //Listener OK

                    @Override
                    public void onResponse(JSONObject responsePlaces) {
                        //Creado resultado
                    }
                }, new Response.ErrorListener() { //Listener ERROR

            @Override
            public void onErrorResponse(VolleyError error) {
                //error
            }
        });

        //Send the request to the requestQueue
        requestQueue.add(request);

    }
}
