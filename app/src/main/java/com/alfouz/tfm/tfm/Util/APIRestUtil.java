package com.alfouz.tfm.tfm.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alfouz.tfm.tfm.Adapters.CourseShopAdapter;
import com.alfouz.tfm.tfm.AsyncTasks.CallbackInterface;
import com.alfouz.tfm.tfm.AsyncTasks.CreateCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.CreateLessonDB;
import com.alfouz.tfm.tfm.AsyncTasks.CreateMathTaskDB;
import com.alfouz.tfm.tfm.AsyncTasks.CreateMathTaskOptionsDB;
import com.alfouz.tfm.tfm.AsyncTasks.DeleteCourseDB;
import com.alfouz.tfm.tfm.AsyncTasks.GetCourseByIdRemotoDB;
import com.alfouz.tfm.tfm.CourseActivity;
import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;
import com.alfouz.tfm.tfm.MyApplication;
import com.alfouz.tfm.tfm.R;
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

import java.util.ArrayList;
import java.util.List;

public class APIRestUtil {

    private static String url = "http://192.168.0.25:3000/api";
    private static String curses = "/course";
    private static String lessons = "/lesson";
    private static String tasks = "/task";
    private static String taskoptions = "/taskoption";

    private static String create = "/createall";

    public static String getUrl(){
        return url;
    }

    public static String getCurses(){
        return url+curses;
    }
    public static String getLessons(){
        return url+lessons;
    }
    public static String getTasks(){
        return url+tasks;
    }
    public static String getOptions(){
        return url+taskoptions;
    }



    public static void getCourseComplete(final long id, final Context context){
        final JSONHelper jsonHelper = new JSONHelper(MyApplication.getAppContext());

        final RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        //Prepare the Request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, //GET or POST
                getCurses() + "/" + Long.toString(id),
                null, //Parameters
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject responsePlaces) {
                        try {
                            //Obtenemos curso
                            final Course c = jsonHelper.getCourseFromJSON(responsePlaces);
                            new GetCourseByIdRemotoDB(new CallbackInterface<CourseEntity>() {
                                @Override
                                public void doCallback(final CourseEntity course) {
                                    if(course!=null){
                                        //*************************************************************************************
                                        //Borrar curso y volver a crearlo
                                        //*************************************************************************************
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setPositiveButton(R.string.misc_yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                new DeleteCourseDB(new CallbackInterface<Boolean>() {
                                                    @Override
                                                    public void doCallback(Boolean object) {
                                                        new CreateCourseDB(new CallbackInterface<CourseEntity>() {
                                                            @Override
                                                            public void doCallback(final CourseEntity courseEntity) {
                                                                //*****************
                                                                //Lecciones
                                                                //*****************
                                                                //Prepare the Request Lecciones
                                                                JsonArrayRequest requestLessons = new JsonArrayRequest(
                                                                        Request.Method.GET, //GET or POST
                                                                        getLessons() + curses + "/" + Long.toString(c.getIdRemote()), //URL
                                                                        null, //Parameters
                                                                        new Response.Listener<JSONArray>() { //Listener OK

                                                                            @Override
                                                                            public void onResponse(JSONArray responseLessons) {
                                                                                //Log.d("tst", responseLessons.toString());
                                                                                try {
                                                                                    final List<Lesson> lessonList = jsonHelper.getLessonsFromJSON(responseLessons);

                                                                                    for(final Lesson l : lessonList) {
                                                                                        new CreateLessonDB(new CallbackInterface<LessonEntity>() {
                                                                                            @Override
                                                                                            public void doCallback(final LessonEntity lessonEntity) {
                                                                                                //*****************
                                                                                                //Tareas
                                                                                                //*****************
                                                                                                JsonArrayRequest requestTasks = new JsonArrayRequest(
                                                                                                        Request.Method.GET, //GET or POST
                                                                                                        getTasks() + lessons + "/" + Long.toString(l.getIdRemote()), //URL
                                                                                                        null, //Parameters
                                                                                                        new Response.Listener<JSONArray>() { //Listener OK

                                                                                                            @Override
                                                                                                            public void onResponse(JSONArray responseTasks) {
                                                                                                                try {
                                                                                                                    final List<MathTask> mathTasks = jsonHelper.getTasksFromJSON(responseTasks);
                                                                                                                    for(final MathTask mt : mathTasks) {
                                                                                                                        new CreateMathTaskDB(new CallbackInterface<MathTaskEntity>() {
                                                                                                                            @Override
                                                                                                                            public void doCallback(final MathTaskEntity mathTaskEntity) {
                                                                                                                                //*****************
                                                                                                                                //Opciones
                                                                                                                                //*****************
                                                                                                                                JsonArrayRequest requestOptions = new JsonArrayRequest(
                                                                                                                                        Request.Method.GET, //GET or POST
                                                                                                                                        getOptions() + tasks + "/" + Long.toString(mt.getIdRemote()), //URL
                                                                                                                                        null, //Parameters
                                                                                                                                        new Response.Listener<JSONArray>() { //Listener OK

                                                                                                                                            @Override
                                                                                                                                            public void onResponse(JSONArray responseOptions) {
                                                                                                                                                try {
                                                                                                                                                    List<MathTaskOption> mathTaskOptions = jsonHelper.getOptionsFromJSON(responseOptions);

                                                                                                                                                    List<MathTaskOptionEntity> mathTaskOptionEntities = new ArrayList<>();
                                                                                                                                                    for(MathTaskOption mto : mathTaskOptions){
                                                                                                                                                        MathTaskOptionEntity mtoe = new MathTaskOptionEntity();
                                                                                                                                                        mtoe.setMathTask(mathTaskEntity.getId());
                                                                                                                                                        mtoe.setCorrect(mto.isCorrect());
                                                                                                                                                        mtoe.setText(mto.getText());
                                                                                                                                                        mtoe.setEcuation(mto.isEcuation());
                                                                                                                                                        mtoe.setIdRemote(mto.getIdRemote());
                                                                                                                                                        mathTaskOptionEntities.add(mtoe);
                                                                                                                                                    }
                                                                                                                                                    MathTaskOptionEntity[] mtoeArray = new MathTaskOptionEntity[mathTaskOptionEntities.size()];
                                                                                                                                                    mathTaskOptionEntities.toArray(mtoeArray);


                                                                                                                                                    new CreateMathTaskOptionsDB(new CallbackInterface() {
                                                                                                                                                        @Override
                                                                                                                                                        public void doCallback(Object object) {

                                                                                                                                                            //******************
                                                                                                                                                            //Final feliz
                                                                                                                                                            //******************
                                                                                                                                                            //Si es la última
                                                                                                                                                            if(mt.getIdRemote()==mathTasks.get(mathTasks.size()-1).getIdRemote() && l.getIdRemote()==lessonList.get(lessonList.size()-1).getIdRemote()){
                                                                                                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                                                                                                                                builder.setPositiveButton(R.string.shop_fragment_view_curse, new DialogInterface.OnClickListener() {
                                                                                                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                                                                                                        Intent intent = new Intent(context, CourseActivity.class);
                                                                                                                                                                        intent.putExtra("idCourse", courseEntity.getId());
                                                                                                                                                                        context.startActivity(intent);
                                                                                                                                                                    }
                                                                                                                                                                });
                                                                                                                                                                builder.setNegativeButton(R.string.misc_ok, new DialogInterface.OnClickListener() {
                                                                                                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                                                                                                        dialog.dismiss();
                                                                                                                                                                    }
                                                                                                                                                                });
                                                                                                                                                                builder.setTitle(R.string.shop_fragment_downloaded_course);
                                                                                                                                                                AlertDialog dialog = builder.create();
                                                                                                                                                                dialog.show();
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }, MyApplication.getAppContext()).execute(mtoeArray);



                                                                                                                                                } catch (JSONException e) {
                                                                                                                                                    e.printStackTrace();
                                                                                                                                                }

                                                                                                                                            }
                                                                                                                                        }, new Response.ErrorListener() { //Listener ERROR

                                                                                                                                    @Override
                                                                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                                                                        //There was an error :(
                                                                                                                                        Log.d("tst", error.toString());
                                                                                                                                    }
                                                                                                                                });

                                                                                                                                //Send the request to the requestQueue
                                                                                                                                requestQueue.add(requestOptions);
                                                                                                                                //*****************
                                                                                                                                //End Opciones
                                                                                                                                //*****************
                                                                                                                            }
                                                                                                                        }, MyApplication.getAppContext()).execute(Long.toString(lessonEntity.getId()),mt.getDescription(), mt.getEcuation(), Long.toString(mt.getIdRemote()));

                                                                                                                    }

                                                                                                                } catch (JSONException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }

                                                                                                            }
                                                                                                        }, new Response.ErrorListener() { //Listener ERROR

                                                                                                    @Override
                                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                                        //There was an error :(
                                                                                                        Log.d("tst", error.toString());
                                                                                                    }
                                                                                                });

                                                                                                //Send the request to the requestQueue
                                                                                                requestQueue.add(requestTasks);
                                                                                                //****************
                                                                                                // End Tareas
                                                                                                //****************
                                                                                            }
                                                                                        }, MyApplication.getAppContext()).execute(Long.toString(courseEntity.getId()), l.getTitle(), l.getDescription(), Integer.toString(l.getDuration()), l.isDone()?"t":"f", Long.toString(l.getIdRemote()));

                                                                                    }

                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                }

                                                                            }
                                                                        }, new Response.ErrorListener() { //Listener ERROR

                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        //There was an error :(
                                                                        Log.d("tst",error.toString());
                                                                    }
                                                                });

                                                                //Send the request to the requestQueue
                                                                requestQueue.add(requestLessons);
                                                            }
                                                        }, MyApplication.getAppContext()).execute(/*Long.toString(c.getCreator())*/"1", c.getTitle(), c.getDescription(), Float.toString(c.getLevel()), c.isPublic()?"t":"f", Integer.toString(c.getType().getId()), Long.toString(c.getIdRemote()));

                                                    }
                                                }, context).execute(course);

                                            }
                                        });
                                        builder.setNegativeButton(R.string.misc_no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.setTitle(R.string.shop_fragment_download_update);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }else{
                                        //Crear el curso desde cero
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setPositiveButton(R.string.misc_yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //*************************************************************************************
                                                //Crear curso desde cero
                                                //*************************************************************************************
                                                //Almacenamos curso
                                                new CreateCourseDB(new CallbackInterface<CourseEntity>() {
                                                    @Override
                                                    public void doCallback(final CourseEntity courseEntity) {
                                                        //*****************
                                                        //Lecciones
                                                        //*****************
                                                        //Prepare the Request Lecciones
                                                        JsonArrayRequest requestLessons = new JsonArrayRequest(
                                                                Request.Method.GET, //GET or POST
                                                                getLessons() + curses + "/" + Long.toString(c.getIdRemote()), //URL
                                                                null, //Parameters
                                                                new Response.Listener<JSONArray>() { //Listener OK

                                                                    @Override
                                                                    public void onResponse(JSONArray responseLessons) {
                                                                        //Log.d("tst", responseLessons.toString());
                                                                        try {
                                                                            final List<Lesson> lessonList = jsonHelper.getLessonsFromJSON(responseLessons);

                                                                            for(final Lesson l : lessonList) {
                                                                                new CreateLessonDB(new CallbackInterface<LessonEntity>() {
                                                                                    @Override
                                                                                    public void doCallback(final LessonEntity lessonEntity) {
                                                                                        //*****************
                                                                                        //Tareas
                                                                                        //*****************
                                                                                        JsonArrayRequest requestTasks = new JsonArrayRequest(
                                                                                                Request.Method.GET, //GET or POST
                                                                                                getTasks() + lessons + "/" + Long.toString(l.getIdRemote()), //URL
                                                                                                null, //Parameters
                                                                                                new Response.Listener<JSONArray>() { //Listener OK

                                                                                                    @Override
                                                                                                    public void onResponse(JSONArray responseTasks) {
                                                                                                        try {
                                                                                                            final List<MathTask> mathTasks = jsonHelper.getTasksFromJSON(responseTasks);
                                                                                                            for(final MathTask mt : mathTasks) {
                                                                                                                new CreateMathTaskDB(new CallbackInterface<MathTaskEntity>() {
                                                                                                                    @Override
                                                                                                                    public void doCallback(final MathTaskEntity mathTaskEntity) {
                                                                                                                        //*****************
                                                                                                                        //Opciones
                                                                                                                        //*****************
                                                                                                                        JsonArrayRequest requestOptions = new JsonArrayRequest(
                                                                                                                                Request.Method.GET, //GET or POST
                                                                                                                                getOptions() + tasks + "/" + Long.toString(mt.getIdRemote()), //URL
                                                                                                                                null, //Parameters
                                                                                                                                new Response.Listener<JSONArray>() { //Listener OK

                                                                                                                                    @Override
                                                                                                                                    public void onResponse(JSONArray responseOptions) {
                                                                                                                                        try {
                                                                                                                                            List<MathTaskOption> mathTaskOptions = jsonHelper.getOptionsFromJSON(responseOptions);

                                                                                                                                            List<MathTaskOptionEntity> mathTaskOptionEntities = new ArrayList<>();
                                                                                                                                            for(MathTaskOption mto : mathTaskOptions){
                                                                                                                                                MathTaskOptionEntity mtoe = new MathTaskOptionEntity();
                                                                                                                                                mtoe.setMathTask(mathTaskEntity.getId());
                                                                                                                                                mtoe.setCorrect(mto.isCorrect());
                                                                                                                                                mtoe.setText(mto.getText());
                                                                                                                                                mtoe.setEcuation(mto.isEcuation());
                                                                                                                                                mtoe.setIdRemote(mto.getIdRemote());
                                                                                                                                                mathTaskOptionEntities.add(mtoe);
                                                                                                                                            }
                                                                                                                                            MathTaskOptionEntity[] mtoeArray = new MathTaskOptionEntity[mathTaskOptionEntities.size()];
                                                                                                                                            mathTaskOptionEntities.toArray(mtoeArray);


                                                                                                                                            new CreateMathTaskOptionsDB(new CallbackInterface() {
                                                                                                                                                @Override
                                                                                                                                                public void doCallback(Object object) {

                                                                                                                                                    //******************
                                                                                                                                                    //Final feliz
                                                                                                                                                    //******************
                                                                                                                                                    //Si es la última
                                                                                                                                                    if(mt.getIdRemote()==mathTasks.get(mathTasks.size()-1).getIdRemote() && l.getIdRemote()==lessonList.get(lessonList.size()-1).getIdRemote()){
                                                                                                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                                                                                                                        builder.setPositiveButton(R.string.shop_fragment_view_curse, new DialogInterface.OnClickListener() {
                                                                                                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                                                                                                Intent intent = new Intent(context, CourseActivity.class);
                                                                                                                                                                intent.putExtra("idCourse", courseEntity.getId());
                                                                                                                                                                context.startActivity(intent);
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                        builder.setNegativeButton(R.string.misc_ok, new DialogInterface.OnClickListener() {
                                                                                                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                                                                                                dialog.dismiss();
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                                        builder.setTitle(R.string.shop_fragment_downloaded_course);
                                                                                                                                                        AlertDialog dialog = builder.create();
                                                                                                                                                        dialog.show();
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }, MyApplication.getAppContext()).execute(mtoeArray);



                                                                                                                                        } catch (JSONException e) {
                                                                                                                                            e.printStackTrace();
                                                                                                                                        }

                                                                                                                                    }
                                                                                                                                }, new Response.ErrorListener() { //Listener ERROR

                                                                                                                            @Override
                                                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                                                //There was an error :(
                                                                                                                                Log.d("tst", error.toString());
                                                                                                                            }
                                                                                                                        });

                                                                                                                        //Send the request to the requestQueue
                                                                                                                        requestQueue.add(requestOptions);
                                                                                                                        //*****************
                                                                                                                        //End Opciones
                                                                                                                        //*****************
                                                                                                                    }
                                                                                                                }, MyApplication.getAppContext()).execute(Long.toString(lessonEntity.getId()),mt.getDescription(), mt.getEcuation(), Long.toString(mt.getIdRemote()));

                                                                                                            }

                                                                                                        } catch (JSONException e) {
                                                                                                            e.printStackTrace();
                                                                                                        }

                                                                                                    }
                                                                                                }, new Response.ErrorListener() { //Listener ERROR

                                                                                            @Override
                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                //There was an error :(
                                                                                                Log.d("tst", error.toString());
                                                                                            }
                                                                                        });

                                                                                        //Send the request to the requestQueue
                                                                                        requestQueue.add(requestTasks);
                                                                                        //****************
                                                                                        // End Tareas
                                                                                        //****************
                                                                                    }
                                                                                }, MyApplication.getAppContext()).execute(Long.toString(courseEntity.getId()), l.getTitle(), l.getDescription(), Integer.toString(l.getDuration()), l.isDone()?"t":"f", Long.toString(l.getIdRemote()));

                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                    }
                                                                }, new Response.ErrorListener() { //Listener ERROR

                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                //There was an error :(
                                                                Log.d("tst",error.toString());
                                                            }
                                                        });

                                                        //Send the request to the requestQueue
                                                        requestQueue.add(requestLessons);
                                                    }
                                                }, MyApplication.getAppContext()).execute(/*Long.toString(c.getCreator())*/"1", c.getTitle(), c.getDescription(), Float.toString(c.getLevel()), c.isPublic()?"t":"f", Integer.toString(c.getType().getId()), Long.toString(c.getIdRemote()));

                                            }
                                        });
                                        builder.setNegativeButton(R.string.misc_no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.setTitle(R.string.shop_fragment_download_new);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            }, context).execute(c.getIdRemote());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
    };
}
