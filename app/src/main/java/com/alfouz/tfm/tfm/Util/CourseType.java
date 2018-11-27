package com.alfouz.tfm.tfm.Util;


import com.alfouz.tfm.tfm.MyApplication;
import com.alfouz.tfm.tfm.R;

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

    @Override
    public String toString() {
        switch (id){
            case 1:
                return MyApplication.getAppContext().getString(R.string.course_type_maths);
            case 2:
                return MyApplication.getAppContext().getString(R.string.course_type_science);
            case 3:
                return MyApplication.getAppContext().getString(R.string.course_type_engineering);
            case 4:
                return MyApplication.getAppContext().getString(R.string.course_type_technology);
            case 5:
                return MyApplication.getAppContext().getString(R.string.course_type_others);
        }
        return super.toString();
    }
}
