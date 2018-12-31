package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Users")
public class UserEntity {
    @PrimaryKey(autoGenerate = false)
    private long id;

    @NonNull
    private String idGoogle;

    public void setId(long id) {
        this.id = id;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public long getId() {
        return id;
    }

    public String getIdGoogle() {
        return idGoogle;
    }
}