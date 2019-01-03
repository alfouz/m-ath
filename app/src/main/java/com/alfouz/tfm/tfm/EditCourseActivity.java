package com.alfouz.tfm.tfm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;

import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.EditCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.EditLessonDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseCompleteDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseEntityDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseLessonsDB;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Util.APIRestUtil;
import com.alfouz.tfm.tfm.Util.JSONHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private RatingBar ratingBar;
    private Switch switchPublic;

    private Course courseAct;

    private long idCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // Setting action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        title = findViewById(R.id.course_title_input);
        description = findViewById(R.id.course_description_input);
        ratingBar = findViewById(R.id.ratingBarCourseLevel);
        switchPublic = findViewById(R.id.course_public_switch);

        Intent intent = getIntent();
        idCourse = intent.getLongExtra("idCourse", -1);

        new GetCourseDB(new CallbackInterface<Course>() {
            @Override
            public void doCallback(Course course) {
                if(course!=null) {
                    title.setText(course.getTitle());
                    description.setText(course.getDescription());
                    ratingBar.setRating(course.getLevel());
                    switchPublic.setChecked(course.isPublic());

                    courseAct = course;
                    getSupportActionBar().setTitle(String.format(getResources().getString(R.string.edit_course_title), course.getTitle()));
                }
            }
        }, this).execute(idCourse);

        Button buttonLessons = findViewById(R.id.buttonLessonsList);
        buttonLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditCourseLessonsActivity.class);
                intent.putExtra("idCourse", idCourse);
                intent.putExtra("nameCourse", title.getText().toString());
                startActivity(intent);
            }
        });

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_course, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.course_menu_update) {

            new GetCourseEntityDB(new CallbackInterface<CourseEntity>() {
                @Override
                public void doCallback(CourseEntity courseEntity) {
                    if(courseEntity!=null) {
                        courseEntity.setTitle(title.getText().toString());
                        courseEntity.setDescription(description.getText().toString());
                        courseEntity.setPublic(switchPublic.isChecked());
                        courseEntity.setLevel(ratingBar.getRating());
                        new EditCourseDB(new CallbackInterface<CourseEntity>() {
                            @Override
                            public void doCallback(CourseEntity course) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("goToFragment", 2);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }, getApplicationContext()).execute(courseEntity);
                    }
                }
            }, this).execute(idCourse);


        }
        if(id == R.id.course_menu_upload){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseActivity.this);
            builder.setPositiveButton(R.string.misc_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new GetCourseCompleteDB(new CallbackInterface<Course>() {
                        @Override
                        public void doCallback(Course courset) {
                            //try {
                            //Log.d("tst", new JSONHelper(getApplicationContext()).getJSONfromCourse(courset).toString());

                            String PLACES_URL = APIRestUtil.getCurses()+"/createall";
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                            JSONObject params = new JSONObject();
                            try {
                                Log.d("tst", new JSONHelper(getApplicationContext()).getJSONfromCourse(courset).toString());
                                params.put("idlocal", courseAct.getId());
                                params.put("iduser", courseAct.getCreator());
                                params.put("titulo", courseAct.getTitle());
                                params.put("descripcion", courseAct.getDescription());
                                params.put("tipo", courseAct.getType().getId());
                                params.put("nivel", courseAct.getLevel());
                                params.put("publico", courseAct.isPublic());
                                params.put("courseJSON", new JSONHelper(getApplicationContext()).getJSONfromCourse(courset));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Prepare the Request
                            JsonObjectRequest request = new JsonObjectRequest(
                                    Request.Method.POST, //GET or POST
                                    PLACES_URL, //URL
                                    params, //Parameters
                                    new Response.Listener<JSONObject>() { //Listener OK

                                        @Override
                                        public void onResponse(final JSONObject responsePlaces) {
                                            new GetCourseEntityDB(new CallbackInterface<CourseEntity>() {
                                                @Override
                                                public void doCallback(final CourseEntity courseEntity) {
                                                    if(courseEntity!=null && courseEntity.getIdRemote()<=0l) {
                                                        courseEntity.setTitle(title.getText().toString());
                                                        courseEntity.setDescription(description.getText().toString());
                                                        courseEntity.setPublic(switchPublic.isChecked());
                                                        courseEntity.setLevel(ratingBar.getRating());
                                                        try {
                                                            courseEntity.setIdRemote(responsePlaces.getLong("id"));
                                                            courseAct.setIdRemote(courseEntity.getIdRemote());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        new EditCourseDB(new CallbackInterface<CourseEntity>() {
                                                            @Override
                                                            public void doCallback(CourseEntity course) {
                                                                //Obtener ids remotos de cada una de las lecciones enviadas
                                                                new GetCourseLessonsDB(new CallbackInterface<List<LessonEntity>>() {
                                                                    @Override
                                                                    public void doCallback(List<LessonEntity> lessons) {
                                                                        RequestQueue requestQueuelessons = Volley.newRequestQueue(MyApplication.getAppContext());
                                                                        for(final LessonEntity l : lessons){
                                                                            try {
                                                                                JSONObject params2 = new JSONObject();
                                                                                //TODO Puede que esto genere problemas o sea necesario hacerlo en más sitios
                                                                                params2.put("idcourse", courseEntity.getIdRemote());
                                                                                params2.put("idlocal", l.getId());
                                                                                Log.d("tst",params2.toString());
                                                                                JsonObjectRequest requestLesson = new JsonObjectRequest(
                                                                                        Request.Method.GET, //GET or POST
                                                                                        APIRestUtil.getLessons() + "/courseid/" +Long.toString(courseEntity.getIdRemote()) + "/" + Long.toString(l.getId()),
                                                                                        null, //Parameters
                                                                                        new Response.Listener<JSONObject>() { //Listener OK

                                                                                            @Override
                                                                                            public void onResponse(JSONObject responsePlaces) {
                                                                                                try {
                                                                                                    l.setIdRemote(responsePlaces.getLong("id"));
                                                                                                    //Creado resultado
                                                                                                    new EditLessonDB(new CallbackInterface() {
                                                                                                        @Override
                                                                                                        public void doCallback(Object object) {
                                                                                                            //Editada leccion
                                                                                                        }
                                                                                                    }, getApplicationContext()).execute(l);
                                                                                                } catch (JSONException e) {
                                                                                                    e.printStackTrace();
                                                                                                }

                                                                                            }
                                                                                        }, new Response.ErrorListener() { //Listener ERROR

                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                        //error
                                                                                    }
                                                                                });


                                                                                //Send the request to the requestQueue
                                                                                requestQueuelessons.add(requestLesson);
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }
                                                                }, getApplicationContext()).execute(courseEntity.getId());
                                                            }
                                                        }, getApplicationContext()).execute(courseEntity);
                                                    }else{
                                                        Log.d("tst", "idremote " + Long.toString(courseEntity.getIdRemote()));

                                                    }
                                                    AlertDialog.Builder builderneutral = new AlertDialog.Builder(EditCourseActivity.this);
                                                    builderneutral.setNeutralButton(R.string.misc_ok, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    builderneutral.setTitle(R.string.edit_course_upload_correct);
                                                    AlertDialog dialogNeutral = builderneutral.create();
                                                    dialogNeutral.show();
                                                }
                                            }, getApplicationContext()).execute(idCourse);
                                        }
                                    }, new Response.ErrorListener() { //Listener ERROR

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //There was an error :(
                                    Log.d("tst",error.toString());
                                }
                            });
                            //Send the request to the requestQueue
                            requestQueue.add(request);



                            /*} catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }
                    }, getApplicationContext()).execute(idCourse);

                }
            });
            builder.setNegativeButton(R.string.misc_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            Log.d("tst", Long.toString(courseAct.getIdRemote()));
            if(courseAct.getIdRemote()>0){
                builder.setTitle(R.string.edit_course_upload_yet);
            }else {
                builder.setTitle(R.string.edit_course_upload);
            }
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("goToFragment", 2);
        startActivity(intent);
        //super.onBackPressed();
    }*/
}
