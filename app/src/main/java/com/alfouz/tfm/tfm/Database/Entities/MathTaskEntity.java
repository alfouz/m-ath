package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "MathTasks", foreignKeys = @ForeignKey(  entity = LessonEntity.class,
        parentColumns = "id",
        childColumns = "lesson",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE))
public class MathTaskEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long lesson;
    //private File image;
    private String ecuation;
    @NonNull
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLesson() {
        return lesson;
    }

    public void setLesson(long lesson) {
        this.lesson = lesson;
    }

    public String getEcuation() {
        return ecuation;
    }

    public void setEcuation(String ecuation) {
        this.ecuation = ecuation;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
