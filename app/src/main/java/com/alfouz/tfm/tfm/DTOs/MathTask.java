package com.alfouz.tfm.tfm.DTOs;

import java.io.File;
import java.util.List;

public class MathTask {
    private long id;
    private long lesson;
    private File image;
    private String ecuation;
    private String description;
    private List<MathTaskOption> answers;

    private boolean answered;
    private boolean answeredCorrectly;

    public MathTask(long lesson, File image, String ecuation, String description, List<MathTaskOption> answers){
        this.lesson = lesson;
        this.image = image;
        this.ecuation = ecuation;
        this.description = description;
        this.answers = answers;
        this.answered = false;
        this.answeredCorrectly = false;
    }

    public MathTask(long id, long lesson, File image, String ecuation, String description, List<MathTaskOption> answers){
        this.id = id;
        this.lesson = lesson;
        this.image = image;
        this.ecuation = ecuation;
        this.description = description;
        this.answers = answers;
        this.answered = false;
        this.answeredCorrectly = false;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getEcuation() {
        return ecuation;
    }

    public void setEcuation(String ecuation) {
        this.ecuation = ecuation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MathTaskOption> getAnswers() {
        return answers;
    }

    public void setAnswers(List<MathTaskOption> answers) {
        this.answers = answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public long getLesson() {
        return lesson;
    }

    public void setLesson(long lesson) {
        this.lesson = lesson;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
