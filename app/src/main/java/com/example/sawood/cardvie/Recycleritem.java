package com.example.sawood.cardvie;

public class Recycleritem {

    private  String item;
    private String decription;
    private String days;
    private  String seh_time;
    private String ift_time;
    private String mark;

    public Recycleritem(String item, String decription,String days,String seh,String ift,String mar) {
        this.item = item;
        this.decription = decription;
        this.days=days;
        this.seh_time=seh;
        this.ift_time=ift;
        this.mark=mar;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSeh_time() {
        return seh_time;
    }

    public void setSeh_time(String seh_time) {
        this.seh_time = seh_time;
    }

    public String getIft_time() {
        return ift_time;
    }

    public void setIft_time(String ift_time) {
        this.ift_time = ift_time;
    }



}
