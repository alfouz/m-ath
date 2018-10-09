package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM Courses WHERE id = :id")
    CourseEntity getCourseById(long id);

    @Query("SELECT * FROM Courses WHERE creator = :creator ORDER BY id DESC")
    List<CourseEntity> getCoursesByUser(long creator);

    @Insert
    long insertCourse(CourseEntity course);

    @Update
    void updateCourse(CourseEntity... course);

    @Delete
    void deleteCourse(CourseEntity... course);
}
