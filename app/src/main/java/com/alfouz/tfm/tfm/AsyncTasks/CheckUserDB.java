package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.alfouz.tfm.tfm.Util.JSONHelper;

public class CheckUserDB extends AsyncTask<String, Void, UserEntity> {
    CallbackInterface callback;
    AppDatabase db;

    public CheckUserDB(CallbackInterface callback, Context context) {
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
        }

        return user;
    }

    @Override
    protected void onPostExecute(UserEntity user) {
        callback.doCallback(user);
    }
}