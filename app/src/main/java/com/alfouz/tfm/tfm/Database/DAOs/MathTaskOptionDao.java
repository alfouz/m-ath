package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.MathTaskOptionEntity;

import java.util.List;

@Dao
public interface MathTaskOptionDao {
    @Query("SELECT * FROM MathTaskOptions WHERE id = :id")
    MathTaskOptionEntity getMathTaskOptionById(long id);

    @Query("SELECT * FROM MathTaskOptions WHERE mathTask = :mathTask ORDER BY id DESC")
    List<MathTaskOptionEntity> getMathTaskOptionByMathTask(long mathTask);

    @Insert
    long insertMathTaskOption(MathTaskOptionEntity mathTaskOption);

    @Update
    void updateMathTaskOption(MathTaskOptionEntity... mathTaskOption);

    @Delete
    void deleteMathTaskOption(MathTaskOptionEntity... mathTaskOption);
}
