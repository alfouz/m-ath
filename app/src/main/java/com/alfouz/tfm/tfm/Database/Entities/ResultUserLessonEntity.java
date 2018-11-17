package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.alfouz.tfm.tfm.Database.Util.DateConverter;

import java.util.Date;

@Entity(tableName = "ResultUserLessons", foreignKeys = {@ForeignKey(entity = UserEntity.class,
        parentColumns = "id",
        childColumns = "user",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = LessonEntity.class,
                parentColumns = "id",
                childColumns = "lesson",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)
        },
        primaryKeys = {"user", "lesson", "timestamp"})
@TypeConverters(DateConverter.class)
public class ResultUserLessonEntity {

    @NonNull
    private long user;
    @NonNull
    private long lesson;
    @NonNull
    private Date timestamp;
    @NonNull
    private long percentCorrect;

    @NonNull
    public long getUser() {
        return user;
    }

    public void setUser(@NonNull long user) {
        this.user = user;
    }

    @NonNull
    public long getLesson() {
        return lesson;
    }

    public void setLesson(@NonNull long lesson) {
        this.lesson = lesson;
    }

    @NonNull
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull Date timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public long getPercentCorrect() {
        return percentCorrect;
    }

    public void setPercentCorrect(@NonNull long percentCorrect) {
        this.percentCorrect = percentCorrect;
    }
}
