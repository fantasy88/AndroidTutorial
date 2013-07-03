package com.example.TimeTracker;

/**
 * Created with IntelliJ IDEA.
 * User: Fantasy
 * Date: 1/13/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeRecord {
    private String note;
    private String times;

    public TimeRecord(String times,String note) {
        this.note = note;
        this.times = times;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
