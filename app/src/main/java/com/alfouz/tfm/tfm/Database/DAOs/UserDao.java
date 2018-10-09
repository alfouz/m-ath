package com.alfouz.tfm.tfm.Database.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alfouz.tfm.tfm.Database.Entities.UserEntity;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users WHERE idGoogle = :idGoogle")
    UserEntity getUserByIdGoogle(String idGoogle);

    @Insert
    long insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity... user);

    @Delete
    void deleteUser(UserEntity... user);
}
