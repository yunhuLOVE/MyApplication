package com.augur.zongyang.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yunhu on 2017-12-19.
 */

public class DateBaseInfo implements Serializable{

    private int date;
    private int day;
    private int hours;
    private int minutes;
    private int month;
    private int nanos;
    private int seconds;
    private long time;
    private int timezoneOffset;
    private int year;

    public Date getMDate(){
        Date mDate = new Date();
        mDate.setDate(date);
        mDate.setDate(day);
        mDate.setHours(hours);
        mDate.setMinutes(minutes);
        mDate.setMonth(month);
        mDate.setSeconds(seconds);
        mDate.setTime(time);
        mDate.setYear(year);
        return mDate;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNanos() {
        return nanos;
    }

    public void setNanos(int nanos) {
        this.nanos = nanos;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
