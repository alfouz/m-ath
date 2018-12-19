package com.alfouz.tfm.tfm.DTOs;

import com.alfouz.tfm.tfm.Util.CourseType;

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
    private CourseType type;
    private long idRemote;

    public Course(long id, String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic, CourseType type){
        this.id = id;
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;

    }

    public Course(long id, String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic, CourseType type, long idRemote){
        this.id = id;
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;
        this.idRemote = idRemote;

    }

    public Course(String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic, CourseType type){
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;

    }

    public Course(String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic, CourseType type, long idRemote){
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;
        this.idRemote = idRemote;

    }

    public Course(String title, List<Lesson> lessons, Float level, Integer score, String description, boolean isPublic, CourseType type, long idRemote, long idUser){
        this.title = title;
        this.lessons = lessons;
        this.score = score;
        this.level = level;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;
        this.idRemote = idRemote;
        this.creator = idUser;

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

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public long getIdRemote() {
        return idRemote;
    }

    public void setIdRemote(long idRemote) {
        this.idRemote = idRemote;
    }
}

