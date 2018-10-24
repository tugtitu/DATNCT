package com.example.tug_pc.restaurantmanagermini.model;

import java.io.Serializable;

public class WorkingTime implements Serializable {
    private int id = -1;
    private String name;
    private String weekdays;
    private String from_hour;
    private String come_hour;
    private int staff_id;

    public WorkingTime(int id, String name, String weekdays, String from_hour, String come_hour, int staff_id) {
        this.id = id;
        this.name = name;
        this.weekdays = weekdays;
        this.from_hour = from_hour;
        this.come_hour = come_hour;
        this.staff_id = staff_id;
    }

    public WorkingTime() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(String from_hour) {
        this.from_hour = from_hour;
    }

    public String getCome_hour() {
        return come_hour;
    }

    public void setCome_hour(String come_hour) {
        this.come_hour = come_hour;
    }

    @Override
    public String toString() {
        return "WorkingTime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weekdays='" + weekdays + '\'' +
                ", from_hour='" + from_hour + '\'' +
                ", come_hour='" + come_hour + '\'' +
                '}';
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }
}
