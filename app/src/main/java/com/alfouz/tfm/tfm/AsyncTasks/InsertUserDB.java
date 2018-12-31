package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;

public class InsertUserDB extends AsyncTask<String, Void, UserEntity> {
        CallbackInterface callback;
        AppDatabase db;

        public InsertUserDB(CallbackInterface callback, Context context) {
            this.callback = callback;
            this.db = AppDatabase.getInstance(context);
        }

        @Override
        protected UserEntity doInBackground(String... strings) {
            final long id = Long.parseLong(strings[0]);
            final String idGoogle = strings[1];

            UserEntity user = db.userDao().getUserByIdGoogle(idGoogle);

            if(user == null){
                user = new UserEntity();
                user.setIdGoogle(idGoogle);
                user.setId(id);
                db.userDao().insertUser(user);
            }

            return user;
        }

        @Override
        protected void onPostExecute(UserEntity user) {
            callback.doCallback(user);
        }
    }
