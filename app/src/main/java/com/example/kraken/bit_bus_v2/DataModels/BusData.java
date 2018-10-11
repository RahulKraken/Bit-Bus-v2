package com.example.kraken.bit_bus_v2.DataModels;

public class BusData {

    // class variables
    private String from;
    private String type;
    private String day;
    private Double time;

    public BusData(String from, String type, String day, Double time) {
        this.from = from;
        this.type = type;
        this.day = day;
        this.time = time;
    }

    /*
    =========================================Getter and Setter======================================
     */

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
