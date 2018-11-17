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

    @Insert
    long insertResultUserLesson(ResultUserLessonEntity resultUserLesson);

    @Update
    void updateResultUserLesson(ResultUserLessonEntity... resultUserLesson);

    @Delete
    void deleteResultUserLesson(ResultUserLessonEntity... resultUserLesson);
}
