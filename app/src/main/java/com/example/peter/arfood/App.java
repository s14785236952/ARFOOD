package com.example.peter.arfood;

public class App {

    private int mDrawable;
    private int mDrawable2;
    private int mDrawable3;
    private String mName;
    private float mRating;

    public App(String name, int drawable, float rating) {
        mName = name;
        mDrawable = drawable;
        mRating = rating;
    }

    public App(String name, int drawable,int drawable2, int drawable3,float rating) {
        mName = name;
        mDrawable = drawable;
        mDrawable2 = drawable2;
        mDrawable3 = drawable3;
        mRating = rating;
    }

    public float getRating() {
        return mRating;
    }

    public int getDrawable() {
        return mDrawable;
    }
    public int getDrawable2() {
        return mDrawable2;
    }
    public int getDrawable3() {
        return mDrawable3;
    }

    public String getName() {
        return mName;
    }
}

