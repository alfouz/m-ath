package com.alfouz.tfm.tfm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.alfouz.tfm.tfm.DTOs.MathTask;
import com.alfouz.tfm.tfm.Database.AppDatabase;
import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;
import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;
import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;
import com.alfouz.tfm.tfm.Database.Entities.UserEntity;
import com.alfouz.tfm.tfm.Util.CourseType;

public class InsertDemoDataDB extends AsyncTask {
    CallbackInterface callback;
    AppDatabase db;

    public InsertDemoDataDB(CallbackInterface callback, Context context){
        this.callback = callback;
        this.db = AppDatabase.getInstance(context);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //UserEntity u = new UserEntity();
        //u.setIdGoogle("-1");
        //db.userDao().insertUser(u);
        //TODO ojo con el id de usuario y con la escritura directa
        CourseEntity c1 = new CourseEntity();
        c1.setTitle("Curso de prueba 1");
        c1.setDescription("descripción prueba 1");
        c1.setCreator(1);
        c1.setScore(5);
        c1.setLevel(2);
        c1.setType(CourseType.Others.getId());
        CourseEntity c2 = new CourseEntity();
        c2.setTitle("Matemáticas de prueba");
        c2.setDescription("descripción matemáticas prueba");
        c2.setCreator(1);
        c2.setScore(0);
        c2.setLevel(3);
        c2.setType(CourseType.Maths.getId());
        CourseEntity c3 = new CourseEntity();
        c3.setTitle("Física de prueba");
        c3.setDescription("descripción física prueba");
        c3.setCreator(1);
        c3.setScore(5);
        c3.setLevel(5);
        c3.setType(CourseType.Physics.getId());

        c1.setId(db.courseDao().insertCourse(c1));
        c2.setId(db.courseDao().insertCourse(c2));
        c3.setId(db.courseDao().insertCourse(c3));

        LessonEntity l1c1 = new LessonEntity();
        l1c1.setCourse(c1.getId());
        l1c1.setTitle("Lección de prueba 1");
        l1c1.setDescription("Lección de prueba para el curso de prueba");
        l1c1.setDuration(5);
        l1c1.setDone(false);
        LessonEntity l2c1 = new LessonEntity();
        l2c1.setCourse(c1.getId());
        l2c1.setTitle("Lección de prueba 2");
        l2c1.setDescription("Lección de prueba 2 para el curso de prueba");
        l2c1.setDuration(2);
        l2c1.setDone(false);
        LessonEntity l3c1 = new LessonEntity();
        l3c1.setCourse(c1.getId());
        l3c1.setTitle("Lección de prueba 3");
        l3c1.setDescription("Lección de prueba 3 para el curso de prueba");
        l3c1.setDuration(3);
        l3c1.setDone(true);
        LessonEntity l4c1 = new LessonEntity();
        l4c1.setCourse(c1.getId());
        l4c1.setTitle("Lección de prueba 4");
        l4c1.setDescription("Lección de prueba 4 para el curso de prueba");
        l4c1.setDuration(3);
        l4c1.setDone(false);
        LessonEntity l1c2 = new LessonEntity();
        l1c2.setCourse(c2.getId());
        l1c2.setTitle("Lección de prueba 1");
        l1c2.setDescription("Lección de prueba para el curso de prueba");
        l1c2.setDuration(5);
        l1c2.setDone(false);
        LessonEntity l2c2 = new LessonEntity();
        l2c2.setCourse(c2.getId());
        l2c2.setTitle("Lección de prueba 2");
        l2c2.setDescription("Lección de prueba 2 para el curso de prueba");
        l2c2.setDuration(2);
        l2c2.setDone(false);
        LessonEntity l3c2 = new LessonEntity();
        l3c2.setCourse(c2.getId());
        l3c2.setTitle("Lección de prueba 3");
        l3c2.setDescription("Lección de prueba 3 para el curso de prueba");
        l3c2.setDuration(3);
        l3c2.setDone(true);
        LessonEntity l4c2 = new LessonEntity();
        l4c2.setCourse(c2.getId());
        l4c2.setTitle("Lección de prueba 4");
        l4c2.setDescription("Lección de prueba 4 para el curso de prueba");
        l4c2.setDuration(3);
        l4c2.setDone(false);

        l1c1.setId(db.lessonDao().insertLesson(l1c1));
        l2c1.setId(db.lessonDao().insertLesson(l2c1));
        l3c1.setId(db.lessonDao().insertLesson(l3c1));
        l4c1.setId(db.lessonDao().insertLesson(l4c1));
        l1c2.setId(db.lessonDao().insertLesson(l1c2));
        l2c2.setId(db.lessonDao().insertLesson(l2c2));
        l3c2.setId(db.lessonDao().insertLesson(l3c2));
        l4c2.setId(db.lessonDao().insertLesson(l4c2));

        MathTaskEntity mte1l1c1 = new MathTaskEntity();
        mte1l1c1.setLesson(l1c1.getId());
        mte1l1c1.setDescription("Resuelve la siguiente ecuación");
        mte1l1c1.setEcuation("$$x^2 = 9$$");
        MathTaskEntity mte2l1c1 = new MathTaskEntity();
        mte2l1c1.setLesson(l1c1.getId());
        mte2l1c1.setDescription("Resuelve la siguiente ecuación");
        mte2l1c1.setEcuation("$$x^3 = 8$$");
        mte1l1c1.setId(db.mathTaskDao().insertMathTask(mte1l1c1));
        mte2l1c1.setId(db.mathTaskDao().insertMathTask(mte2l1c1));

        MathTaskOptionEntity mtoe1mte1l1c1 = new MathTaskOptionEntity();
        mtoe1mte1l1c1.setMathTask(mte1l1c1.getId());
        mtoe1mte1l1c1.setText("$$1$$");
        mtoe1mte1l1c1.setCorrect(false);
        MathTaskOptionEntity mtoe2mte1l1c1 = new MathTaskOptionEntity();
        mtoe2mte1l1c1.setMathTask(mte1l1c1.getId());
        mtoe2mte1l1c1.setText("$$2$$");
        mtoe2mte1l1c1.setCorrect(false);
        MathTaskOptionEntity mtoe3mte1l1c1 = new MathTaskOptionEntity();
        mtoe3mte1l1c1.setMathTask(mte1l1c1.getId());
        mtoe3mte1l1c1.setText("$$3$$");
        mtoe3mte1l1c1.setCorrect(true);
        MathTaskOptionEntity mtoe4mte1l1c1 = new MathTaskOptionEntity();
        mtoe4mte1l1c1.setMathTask(mte1l1c1.getId());
        mtoe4mte1l1c1.setText("$$4$$");
        mtoe4mte1l1c1.setCorrect(false);

        db.mathTaskOptionDao().insertMathTaskOption(mtoe1mte1l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe2mte1l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe3mte1l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe4mte1l1c1);

        MathTaskOptionEntity mtoe1mte2l1c1 = new MathTaskOptionEntity();
        mtoe1mte2l1c1.setMathTask(mte2l1c1.getId());
        mtoe1mte2l1c1.setText("$$1$$");
        mtoe1mte2l1c1.setCorrect(false);
        MathTaskOptionEntity mtoe2mte2l1c1 = new MathTaskOptionEntity();
        mtoe2mte2l1c1.setMathTask(mte2l1c1.getId());
        mtoe2mte2l1c1.setText("$$2$$");
        mtoe2mte2l1c1.setCorrect(false);
        MathTaskOptionEntity mtoe3mte2l1c1 = new MathTaskOptionEntity();
        mtoe3mte2l1c1.setMathTask(mte2l1c1.getId());
        mtoe3mte2l1c1.setText("$$3$$");
        mtoe3mte2l1c1.setCorrect(false);
        MathTaskOptionEntity mtoe4mte2l1c1 = new MathTaskOptionEntity();
        mtoe4mte2l1c1.setMathTask(mte2l1c1.getId());
        mtoe4mte2l1c1.setText("$$4$$");
        mtoe4mte2l1c1.setCorrect(true);

        db.mathTaskOptionDao().insertMathTaskOption(mtoe1mte2l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe2mte2l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe3mte2l1c1);
        db.mathTaskOptionDao().insertMathTaskOption(mtoe4mte2l1c1);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        callback.doCallback(o);
    }
}
