package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CheckUserAndInsertDB;
import com.google.android.gms.common.SignInButton;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:

                new CheckUserAndInsertDB(new CallbackInterface() {
                    @Override
                    public void doCallback(Object object) {
                        Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                        startActivity(intent);
                    }
                }, getApplicationContext()).execute("1");
                break;
        }
    }
}
