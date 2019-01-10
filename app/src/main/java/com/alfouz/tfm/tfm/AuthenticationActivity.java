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
import com.alfouz.tfm.tfm.AsyncTasks.CheckUserDB;
import com.alfouz.tfm.tfm.AsyncTasks.InsertUserDB;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.alfouz.tfm.tfm.Util.JSONHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener, CallbackInterface<UserEntity>{

    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;


    public String personName;
    public String personPhotoUrl;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
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

            //Log.d("tst", account.getIdToken() + " - " + account.getId());
            //new CheckUserDB(this, getApplicationContext()).execute(account.getId());
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

        new CheckUserDB(this, getApplicationContext()).execute(account.getId()); //TODO Dangerous thing
        /*new CheckUserAndInsertBD(this, getApplicationContext()).execute(account.getId());*/
    }


    @Override
    public void doCallback(final UserEntity user) {
        /*if(user.getId() > 0){
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
        }*/

        if(user.getId() > 0){
            Log.e("tst", "Existe: Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            // Signed in successfully, show next UI.
            Intent intent = new Intent(this, InitialActivity.class);
            intent.putExtra("idUser", user.getId());
            intent.putExtra("personName", personName);
            intent.putExtra("personPhotoUrl", personPhotoUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            final JSONHelper jsonHelper = new JSONHelper(getApplicationContext());
            final RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
            //BUSCANDO SI EXISTE EN REMOTO ---------------------------------------------------------
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, //GET or POST
                    APIRestUtil.getUsers() + "/idgoogle/" + user.getIdGoogle(), //URL
                    null, //Parameters
                    new Response.Listener<JSONObject>() { //Listener OK

                        @Override
                        public void onResponse(JSONObject responsePlaces) {
                            //Existe en remoto
                            final UserEntity userRemote;
                            try {
                                userRemote = jsonHelper.getUserEntityFromJSON(responsePlaces);
                                new InsertUserDB(new CallbackInterface<UserEntity>() {
                                    @Override
                                    public void doCallback(UserEntity userEntity) {
                                        //Insertado
                                        Log.e("tst", "Insertado: Name: " + personName + ", email: " + email
                                                + ", Image: " + personPhotoUrl);
                                        // Signed in successfully, show next UI.
                                        Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                                        intent.putExtra("idUser", userEntity.getId());
                                        intent.putExtra("personName", personName);
                                        intent.putExtra("personPhotoUrl", personPhotoUrl);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }, getApplicationContext()).execute(Long.toString(userRemote.getId()), userRemote.getIdGoogle());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() { //Listener ERROR

                @Override
                public void onErrorResponse(VolleyError error) {
                    //error

                    JSONObject params = new JSONObject();

                    try {
                        params.put("idgoogle", user.getIdGoogle());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Prepare the Request
                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST, //GET or POST
                            APIRestUtil.getUsers() + "/create", //URL
                            params, //Parameters
                            new Response.Listener<JSONObject>() { //Listener OK

                                @Override
                                public void onResponse(JSONObject responsePlaces) {
                                    try {
                                        long id = responsePlaces.getLong("id");
                                        new InsertUserDB(new CallbackInterface<UserEntity>() {
                                            @Override
                                            public void doCallback(UserEntity userEntity) {
                                                //Insertado
                                                Log.e("tst", "Creado: Name: " + personName + ", email: " + email
                                                        + ", Image: " + personPhotoUrl);
                                                // Signed in successfully, show next UI.
                                                Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                                                intent.putExtra("idUser", userEntity.getId());
                                                intent.putExtra("personName", personName);
                                                intent.putExtra("personPhotoUrl", personPhotoUrl);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        }, getApplicationContext()).execute(Long.toString(id), user.getIdGoogle());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() { //Listener ERROR

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //error
                            Log.d("tst", "error creando");
                            Toast.makeText(getApplicationContext(), R.string.auth_intro_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Send the request to the requestQueue
                    requestQueue.add(request);
                }
            });


            //Send the request to the requestQueue
            requestQueue.add(request);
        }
    }
}
