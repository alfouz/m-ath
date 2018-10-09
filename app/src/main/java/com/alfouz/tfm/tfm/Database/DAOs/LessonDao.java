package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.LessonEntity;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM Lessons WHERE id = :id")
    LessonEntity getLessonById(long id);

    @Query("SELECT * FROM Lessons WHERE course = :course ORDER BY id DESC")
    List<LessonEntity> getLessonsByCourse(long course);

    @Insert
    long insertLesson(LessonEntity lesson);

    @Update
    void updateLesson(LessonEntity... lesson);

    @Delete
    void deleteLesson(LessonEntity... lesson);
}
