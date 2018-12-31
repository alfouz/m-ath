package com.alfouz.tfm.tfm;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    private static long idUser;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static long getIdUser(){
        return idUser;
    }

    public static void setIdUser(long newIdUser){
        idUser = newIdUser;
    }
}