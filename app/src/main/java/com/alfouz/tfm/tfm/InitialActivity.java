package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.InsertDemoDataDB;
import com.alfouz.tfm.tfm.Util.PrefManager;

public class InitialActivity extends AppCompatActivity {

    private Button btnBasicConfig;
    private Button btnNoConfig;
    private TextView tvInfo;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            //finish();
        }

        setContentView(R.layout.activity_initial);


        btnBasicConfig = findViewById(R.id.butBasicAppConfig);
        btnNoConfig = findViewById(R.id.butEmptyAppConfig);
        tvInfo = findViewById(R.id.tvInfoInitial);

        //Warning
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvInfo.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        btnBasicConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateInitialData();
                launchHomeScreen();
            }
        });

        btnNoConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //finish();
    }

    private void CreateInitialData(){
        //TODO revisar inserción inicial
        new InsertDemoDataDB(new CallbackInterface() {
            @Override
            public void doCallback(Object object) {
                launchHomeScreen();
            }
        }, getApplicationContext()).execute();
    }
}
