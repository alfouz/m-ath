package com.alfouz.tfm.tfm.Database.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "MathTaskOptions", foreignKeys = @ForeignKey(  entity = MathTaskEntity.class,
        parentColumns = "id",
        childColumns = "mathTask",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE))
public class MathTaskOptionEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long mathTask;
    @NonNull
    private String text;
    @NonNull
    private boolean isCorrect;
    @NonNull
    private boolean isEcuation;

    private long idRemote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMathTask() {
        return mathTask;
    }

    public void setMathTask(long mathTask) {
        this.mathTask = mathTask;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(@NonNull boolean correct) {
        isCorrect = correct;
    }

    @NonNull
    public boolean isEcuation() {
        return isEcuation;
    }

    public void setEcuation(@NonNull boolean ecuation) {
        isEcuation = ecuation;
    }

    public long getIdRemote() {
        return idRemote;
    }

    public void setIdRemote(long idRemote) {
        this.idRemote = idRemote;
    }
}
