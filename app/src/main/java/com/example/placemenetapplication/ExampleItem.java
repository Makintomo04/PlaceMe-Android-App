package com.example.placemenetapplication;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int placementID;

    public ExampleItem(int imageResource, String text1, String text2, int ID) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        placementID = ID;
    }

    public void changeText1(String text) {
        mText1 = text;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public int getID() {
        return placementID;
    }
}