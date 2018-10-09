package com.alfouz.tfm.tfm.DTOs;

public class MathTaskOption {
    private long id;
    private String text;
    private boolean isCorrect;

    public MathTaskOption(String text, boolean isCorrect){
        this.text=text;
        this.isCorrect=isCorrect;
    }

    public MathTaskOption(long id, String text, boolean isCorrect){
        this.id = id;
        this.text=text;
        this.isCorrect=isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
