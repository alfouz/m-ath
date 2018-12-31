package com.alfouz.tfm.tfm.Util;

import android.content.Context;
import android.util.Log;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.alfouz.tfm.tfm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    Context context;

    public JSONHelper(Context context){
        this.context = context;
    }

    public List<Course> getCoursesFromFile(int resId){
        List<Course> courseList = new ArrayList<>();

        InputStream inputStream = context.getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Obtenemos el array de cursos
            JSONObject jObject = new JSONObject(
                    byteArrayOutputStream.toString("UTF-8"));
            JSONArray jArray = jObject.getJSONArray("courses");
            Log.d("tst", jObject.toString());
            //Iteramos los cursos y vamos obteniendo lecciones, luego tareas y luego opciones y montamos el puzle
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject objCourse = jArray.getJSONObject(i);
                String title = objCourse.getString("title");
                Float level = Float.parseFloat(Double.toString(objCourse.getDouble("level")));
                String description = objCourse.getString("description");
                Boolean ispublic = objCourse.getBoolean("public");
                Integer type = objCourse.getInt("type");

                List<Lesson> lessonList = new ArrayList<>();
                JSONArray lessonsArray = objCourse.getJSONArray("lessons");
                for(int j = 0; j< lessonsArray.length(); j++){
                    JSONObject objLesson = lessonsArray.getJSONObject(j);

                    String titleLesson = objLesson.getString("title");
                    String descriptionLesson = objLesson.getString("description");
                    Integer durationLesson = objLesson.getInt("duration");

                    List<MathTask> mathTaskList = new ArrayList<>();
                    JSONArray mathTaskArray = objLesson.getJSONArray("tasks");
                    for(int k=0; k<mathTaskArray.length(); k++){
                        JSONObject objTask = mathTaskArray.getJSONObject(k);

                        String descriptionTask = objTask.getString("description");
                        String ecuationTask = "$$"+objTask.getString("ecuation")+"$$";

                        JSONArray answersArray = objTask.getJSONArray("answers");
                        List<MathTaskOption> optionList = new ArrayList<>();
                        for(int l=0; l<answersArray.length(); l++){
                            JSONObject objAnswer = answersArray.getJSONObject(l);
                            Boolean isEcuation = objAnswer.getBoolean("isEcuation");
                            String text = objAnswer.getString("text");
                            Boolean isCorrect = objAnswer.getBoolean("correct");
                            //Ojo con este paso en un parser automático de BBDD
                            if(isEcuation) {
                                optionList.add(new MathTaskOption("$$"+text+"$$", isCorrect, isEcuation));
                            }else{
                                optionList.add(new MathTaskOption(text, isCorrect, isEcuation));
                            }
                        }
                        mathTaskList.add(new MathTask(ecuationTask, descriptionTask, optionList));
                    }
                    lessonList.add(new Lesson(titleLesson, descriptionLesson, mathTaskList, durationLesson));
                }
                courseList.add(new Course(title, lessonList, level, 0, description, ispublic, CourseType.getType(type)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public JSONObject getJSONfromCourse(Course c) throws JSONException {
        JSONObject course = new JSONObject();

        course.put("id", c.getId());
        course.put("creator", c.getCreator());
        course.put("title", c.getTitle());
        course.put("description", c.getDescription());
        course.put("level", c.getLevel());
        course.put("public", c.isPublic());
        course.put("type", c.getType().getId());

        JSONArray lessons = new JSONArray();

        if(c.getLessons()!=null) {
            for (Lesson l : c.getLessons()) {
                JSONObject lessonObject = new JSONObject();
                lessonObject.put("id", l.getId());
                lessonObject.put("course", l.getCourse());
                lessonObject.put("title", l.getTitle());
                lessonObject.put("description", l.getDescription());
                lessonObject.put("duration", l.getDuration());

                JSONArray tasks = new JSONArray();

                if(l.getTasks()!=null) {
                    for (MathTask t : l.getTasks()) {
                        JSONObject taskObject = new JSONObject();
                        taskObject.put("id", t.getId());
                        taskObject.put("lesson", t.getLesson());
                        taskObject.put("description", t.getDescription());
                        taskObject.put("ecuation", t.getEcuation());

                        JSONArray options = new JSONArray();
                        if(t.getAnswers()!=null) {
                            for (MathTaskOption o : t.getAnswers()) {
                                JSONObject optionObject = new JSONObject();
                                optionObject.put("id", o.getId());
                                optionObject.put("text", o.getText());
                                optionObject.put("correct", o.isCorrect());
                                optionObject.put("isEcuation", o.isEcuation());
                                options.put(optionObject);
                            }
                        }
                        taskObject.put("answers", options);
                        tasks.put(taskObject);
                    }
                }
                lessonObject.put("tasks", tasks);
                lessons.put(lessonObject);
            }
        }

        course.put("lessons", lessons);

        return course;
    }

    public List<Course> getCoursesFromJSON(JSONArray json) throws JSONException {
        List<Course> courses = new ArrayList<>();

        for(int i = 0; i<json.length(); i++){
            JSONObject objCourse = json.getJSONObject(i);
            String title = objCourse.getString("titulo");
            Float level = Float.parseFloat(Double.toString(objCourse.getDouble("nivel")));
            String description = objCourse.getString("descripcion");
            Boolean ispublic = objCourse.getInt("publico")>0;
            Integer type = objCourse.getInt("tipo");
            Long idRemoto = objCourse.getLong("id");

            courses.add(new Course(title, null, level, 0, description, ispublic, CourseType.getType(type), idRemoto));
        }

        return courses;
    }

    public Course getCourseFromJSON(JSONObject json) throws JSONException {
        String title = json.getString("titulo");
        Float level = Float.parseFloat(Double.toString(json.getDouble("nivel")));
        String description = json.getString("descripcion");
        Boolean ispublic = json.getInt("publico")>0;
        Integer type = json.getInt("tipo");
        Long idRemoto = json.getLong("id");

        return new Course(title, null, level, 0, description, ispublic, CourseType.getType(type), idRemoto);
    }

    public List<Lesson> getLessonsFromJSON(JSONArray jsonArray) throws JSONException {
        List<Lesson> lessons = new ArrayList<>();

        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject leccionJSON = jsonArray.getJSONObject(i);
            Long idRemoto = leccionJSON.getLong("id");
            Long idCurso = leccionJSON.getLong("idcurso");
            String titulo = leccionJSON.getString("titulo");
            String descripcion = leccionJSON.getString("descripcion");
            Integer duracion = leccionJSON.getInt("duracion");
            Long idLocal = leccionJSON.getLong("idlocal");
            lessons.add(new Lesson(idCurso, titulo, descripcion, null, duracion, idRemoto));
        }
        return lessons;
    }

    public List<MathTask> getTasksFromJSON(JSONArray jsonArray) throws JSONException{
        List<MathTask> tasks = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++){
            JSONObject tareaJSON = jsonArray.getJSONObject(i);

            Long idRemoto = tareaJSON.getLong("id");
            Long idLeccion = tareaJSON.getLong("idleccion");
            String ecuacion = tareaJSON.getString("ecuacion");
            if(ecuacion.equals("null")){
                ecuacion="";
            }
            String descripcion = tareaJSON.getString("descripcion");
            Long idlocal = tareaJSON.getLong("idlocal");
            tasks.add(new MathTask(idLeccion, null, ecuacion, descripcion, null, idRemoto));
        }

        return tasks;
    }

    public List<MathTaskOption> getOptionsFromJSON(JSONArray jsonArray) throws JSONException{
        List<MathTaskOption> options = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject optionJSON = jsonArray.getJSONObject(i);

            Long idRemoto = optionJSON.getLong("id");
            Long idTarea = optionJSON.getLong("idtarea");
            Long idLocal = optionJSON.getLong("idlocal");
            Boolean ecuacion = optionJSON.getInt("ecuacion")>0;
            Boolean correcto = optionJSON.getInt("correcto")>0;
            String texto = optionJSON.getString("texto");

            options.add(new MathTaskOption(texto, correcto, ecuacion, idRemoto));
        }


        return options;
    }

    public UserEntity getUserEntityFromJSON(JSONObject jsonObject) throws JSONException {
        Long id = jsonObject.getLong("id");
        String idGoogle = jsonObject.getString("idgoogle");

        UserEntity user = new UserEntity();
        user.setId(id);
        user.setIdGoogle(idGoogle);
        return user;
    }
}
