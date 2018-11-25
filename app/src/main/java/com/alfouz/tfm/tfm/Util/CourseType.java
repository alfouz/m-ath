package com.alfouz.tfm.tfm.Util;

public enum CourseType {
    Maths(1), Science(2), Engineering(3), Technology(4), Others(5);

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
