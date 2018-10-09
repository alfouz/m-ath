package com.alfouz.tfm.tfm.DTOs;

import java.util.List;

public class Course {
    private long id;
    private String title;
    private long creator;
    //Necesario revisar esto
    private String creatorName;
    private List<Lesson> lessons;
    private Integer score;
    private Float level;
    private String description;
    private boolean isPublic;

    public Course(long id, String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic){
        this.id = id;
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;

    }

    public Course(String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic){
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getLevel() {
        return level;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}

