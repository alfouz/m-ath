package com.alfouz.tfm.tfm.Util;

public enum CourseType {
    Maths(1), Physics(2), Others(3);

    private int id;

    CourseType(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static CourseType getType(int id){
        for(CourseType ct : values()){
            if(ct.getId() == id){
                return ct;
            }
        }
        return CourseType.Others;
    }
}
