package com.alfouz.tfm.tfm.DTOs;

public class MathTaskOption {
    private long id;
    private String text;
    private boolean isCorrect;
    private boolean isEcuation;

    private long idRemote;

    public MathTaskOption(String text, boolean isCorrect){
        this.text=text;
        this.isCorrect=isCorrect;
    }

    public MathTaskOption(String text, boolean isCorrect, boolean isEcuation){
        this.text=text;
        this.isCorrect=isCorrect;
        this.isEcuation=isEcuation;
    }

    public MathTaskOption(String text, boolean isCorrect, boolean isEcuation, long idRemote){
        this.text=text;
        this.isCorrect=isCorrect;
        this.isEcuation=isEcuation;
        this.idRemote = idRemote;
    }

    public MathTaskOption(long id, String text, boolean isCorrect){
        this.id = id;
        this.text=text;
        this.isCorrect=isCorrect;
    }

    public MathTaskOption(long id, String text, boolean isCorrect, boolean isEcuation){
        this.id = id;
        this.text=text;
        this.isCorrect=isCorrect;
        this.isEcuation=isEcuation;
    }

    public MathTaskOption(long id, String text, boolean isCorrect, boolean isEcuation, long idRemote){
        this.id = id;
        this.text=text;
        this.isCorrect=isCorrect;
        this.isEcuation=isEcuation;
        this.idRemote = idRemote;
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

    public boolean isEcuation() {
        return isEcuation;
    }

    public void setEcuation(boolean ecuation) {
        isEcuation = ecuation;
    }

    public long getIdRemote() {
        return idRemote;
    }

    public void setIdRemote(long idRemote) {
        this.idRemote = idRemote;
    }
}
