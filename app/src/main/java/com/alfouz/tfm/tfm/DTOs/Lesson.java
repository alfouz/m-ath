package com.alfouz.tfm.tfm.DTOs;

import java.util.List;

public class Lesson {
    private long id;
    private long course;
    private List<MathTask> tasks;
    private String title;
    private String description;
    private Integer duration;
    private boolean isDone;
    private long idRemote;


    public Lesson(String title, String description, List<MathTask> tasks, Integer duration){
        this.title = title;
        this.tasks = tasks;
        this.description = description;
        this.duration = duration;
    }

    public Lesson(long course, String title, String description, List<MathTask> tasks, Integer duration){
        this.course = course;
        this.title = title;
        this.tasks = tasks;
        this.description = description;
        this.duration = duration;
    }

    public Lesson(long course, String title, String description, List<MathTask> tasks, Integer duration, long idRemote){
        this.course = course;
        this.title = title;
        this.tasks = tasks;
        this.description = description;
        this.duration = duration;
        this.idRemote = idRemote;
    }

    public Lesson(long id, long course, String title, String description, List<MathTask> tasks, Integer duration){
        this.id = id;
        this.course = course;
        this.title = title;
        this.tasks = tasks;
        this.description = description;
        this.duration = duration;
    }

    public Lesson(long id, long course, String title, String description, List<MathTask> tasks, Integer duration, long idRemote){
        this.id = id;
        this.course = course;
        this.title = title;
        this.tasks = tasks;
        this.description = description;
        this.duration = duration;
        this.idRemote = idRemote;
    }

    public Lesson(long id, String title, String description, Integer duration){
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public List<MathTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MathTask> tasks) {
        this.tasks = tasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        this.course = course;
    }

    public long getIdRemote() {
        return idRemote;
    }

    public void setIdRemote(long idRemote) {
        this.idRemote = idRemote;
    }
}
