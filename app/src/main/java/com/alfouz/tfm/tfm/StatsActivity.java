package com.alfouz.tfm.tfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long idCourse = getIntent().getLongExtra("idCourse", -1);

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, //GET or POST
                APIRestUtil.getResults() + "/user/" + MyApplication.getIdUser(), //URL
                null, //Parameters
                new Response.Listener<JSONObject>() { //Listener OK

                    @Override
                    public void onResponse(JSONObject responsePlaces) {
                        //Creado resultado
                        Log.d("tst", responsePlaces.toString());
                    }
                }, new Response.ErrorListener() { //Listener ERROR

            @Override
            public void onErrorResponse(VolleyError error) {
                //error
            }
        });


        //Send the request to the requestQueue
        requestQueue.add(request);

        new GetCourseLessonsDB(new CallbackInterface<List<LessonEntity>>() {
            @Override
            public void doCallback(List<LessonEntity> lessons) {
                RequestQueue requestQueue2 = Volley.newRequestQueue(MyApplication.getAppContext());

                for(LessonEntity lesson : lessons) {
                    JsonObjectRequest request2 = new JsonObjectRequest(
                            Request.Method.GET, //GET or POST
                            APIRestUtil.getResults() + "/lesson/" + lesson.getIdRemote(), //URL
                            null, //Parameters
                            new Response.Listener<JSONObject>() { //Listener OK

                                @Override
                                public void onResponse(JSONObject responsePlaces) {
                                    //Creado resultado
                                    Log.d("tst", responsePlaces.toString());
                                }
                            }, new Response.ErrorListener() { //Listener ERROR

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //error
                        }
                    });

                    //Send the request to the requestQueue
                    requestQueue2.add(request2);
                }
            }
        }, getApplicationContext()).execute(idCourse);


    }
}
