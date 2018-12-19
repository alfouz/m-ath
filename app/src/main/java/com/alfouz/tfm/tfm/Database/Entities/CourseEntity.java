package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.alfouz.tfm.tfm.Util.CourseType;

@Entity(tableName = "Courses"/*, foreignKeys = @ForeignKey(  entity = UserEntity.class,
        parentColumns = "id",
        childColumns = "creator",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)*/)
//OJO con los borrados de base de datos de usuarios y entidades
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String title;
    private long creator;
    @NonNull
    private int score;
    @NonNull
    private float level;
    @NonNull
    private String description;
    @NonNull
    private boolean isPublic;

    private long idRemote;

    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    @NonNull
    public int getScore() {
        return score;
    }

    public void setScore(@NonNull int score) {
        this.score = score;
    }

    @NonNull
    public float getLevel() {
        return level;
    }

    public void setLevel(@NonNull float level) {
        this.level = level;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(@NonNull boolean aPublic) {
        isPublic = aPublic;
    }

    @NonNull
    public int getType() {
        return type;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }

    public long getIdRemote() {
        return idRemote;
    }

    public void setIdRemote(long idRemote) {
        this.idRemote = idRemote;
    }
}
