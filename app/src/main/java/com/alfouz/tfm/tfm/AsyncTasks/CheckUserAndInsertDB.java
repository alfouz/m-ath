package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.alfouz.tfm.tfm.MyApplication;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.alfouz.tfm.tfm.Util.JSONHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckUserAndInsertDB extends AsyncTask<String, Void, UserEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CheckUserAndInsertDB(CallbackInterface callback, Context context) {
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    protected UserEntity doInBackground(String... strings) {
        final String idGoogle = strings[0];

        UserEntity user = db.userDao().getUserByIdGoogle(idGoogle);


        if(user == null){
            user = new UserEntity();
            user.setIdGoogle(idGoogle);
            db.userDao().insertUser(user);
            user = db.userDao().getUserByIdGoogle(idGoogle);
        }

        return user;
    }

    @Override
    protected void onPostExecute(UserEntity user) {
        callback.doCallback(user);
    }
}
