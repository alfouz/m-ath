package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.MathTaskEntity;

import java.util.List;

@Dao
public interface MathTaskDao {
    @Query("SELECT * FROM MathTasks WHERE id = :id")
    MathTaskEntity getMathTaskById(long id);

    @Query("SELECT * FROM MathTasks WHERE lesson = :lesson ORDER BY id DESC")
    List<MathTaskEntity> getMathTasksByLesson(long lesson);

    @Query("SELECT COUNT() FROM MathTasks WHERE lesson = :lesson")
    long countMathTasksByLesson(long lesson);

    @Insert
    long insertMathTask(MathTaskEntity MathTask);

    @Update
    void updateMathTask(MathTaskEntity... MathTask);

    @Delete
    void deleteMathTask(MathTaskEntity... MathTask);
}
