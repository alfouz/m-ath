package com.alfouz.tfm.tfm.Util;

import android.content.Context;
import android.util.Log;

import com.alfouz.tfm.tfm.DTOs.Course;
import com.alfouz.tfm.tfm.DTOs.Lesson;
import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.DTOs.MathTaskOption;
import com.alfouz.tfm.tfm.R;

import org.json.JSONArray;
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

                            String text = "$$"+objAnswer.getString("text")+"$$";
                            Boolean isCorrect = objAnswer.getBoolean("correct");

                            optionList.add(new MathTaskOption(text,isCorrect));
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
}
