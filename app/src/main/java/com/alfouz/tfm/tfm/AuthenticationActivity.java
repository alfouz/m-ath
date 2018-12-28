package com.alfouz.tfm.tfm;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Toast;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CheckUserAndInsertDB;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener, CallbackInterface<UserEntity>{

    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9002;


    public String personName;
    public String personPhotoUrl;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        Task<GoogleSignInAccount> silentSignIn = mGoogleSignInClient.silentSignIn();

        if (silentSignIn.isSuccessful()) {
            initializeData(silentSignIn.getResult());
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:

                /*new CheckUserAndInsertDB(new CallbackInterface() {
                    @Override
                    public void doCallback(Object object) {
                        Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                }, getApplicationContext()).execute("1");*/
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                signIn();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Task<GoogleSignInAccount> silentSignIn = mGoogleSignInClient.silentSignIn();

        if (silentSignIn.isSuccessful()) {
            initializeData(silentSignIn.getResult());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            initializeData(account);

            new CheckUserAndInsertDB(this, getApplicationContext()).execute(account.getId());
            /*new CheckUserAndInsertBD(this, getApplicationContext()).execute(account.getId());*/
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void initializeData(GoogleSignInAccount account){
        personName = account.getDisplayName();
        if(account.getPhotoUrl() != null){
            personPhotoUrl = account.getPhotoUrl().toString();
        }
        email = account.getEmail();

        new CheckUserAndInsertDB(this, getApplicationContext()).execute(account.getId());
        /*new CheckUserAndInsertBD(this, getApplicationContext()).execute(account.getId());*/
    }


    @Override
    public void doCallback(UserEntity user) {
        if(user.getId() > 0){
            Log.e("tst", "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            // Signed in successfully, show next UI.
            Intent intent = new Intent(this, InitialActivity.class);
            intent.putExtra("idUser", user.getId());
            intent.putExtra("personName", personName);
            intent.putExtra("personPhotoUrl", personPhotoUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
