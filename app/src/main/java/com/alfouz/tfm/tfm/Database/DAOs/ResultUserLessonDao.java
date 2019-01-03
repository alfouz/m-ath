package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.ResultUserLessonEntity;

import java.util.List;

@Dao
public interface ResultUserLessonDao {

    @Query("SELECT * FROM ResultUserLessons WHERE user= :user AND lesson= :lesson")
    ResultUserLessonEntity getLastResultUserLesson(long user, long lesson);

    @Query("SELECT * FROM ResultUserLessons WHERE user = :user AND lesson= :lesson")
    List<ResultUserLessonEntity> getResultUserLessonList(long user, long lesson);

    @Query("SELECT * FROM ResultUserLessons WHERE user= :user ORDER BY timestamp desc")
    ResultUserLessonEntity getLastResultUser(long user);

    @Query("SELECT * FROM ResultUserLessons WHERE user= :user AND timestamp>:secs ORDER BY timestamp desc")
    List<ResultUserLessonEntity> getResultsAlongTime(long user, long secs);

    //Posiblemente es necesario implementar una función que devuelva el mayor score de una lección por usuario

    @Insert
    long insertResultUserLesson(ResultUserLessonEntity resultUserLesson);

    @Update
    void updateResultUserLesson(ResultUserLessonEntity... resultUserLesson);

    @Delete
    void deleteResultUserLesson(ResultUserLessonEntity... resultUserLesson);
}
