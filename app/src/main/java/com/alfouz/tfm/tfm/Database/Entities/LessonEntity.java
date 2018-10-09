package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Lessons", foreignKeys = @ForeignKey(  entity = CourseEntity.class,
        parentColumns = "id",
        childColumns = "course",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE))

public class LessonEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long course;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int duration;
    @NonNull
    private boolean isDone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        this.course = course;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public int getDuration() {
        return duration;
    }

    public void setDuration(@NonNull int duration) {
        this.duration = duration;
    }

    @NonNull
    public boolean isDone() {
        return isDone;
    }

    public void setDone(@NonNull boolean done) {
        isDone = done;
    }
}
