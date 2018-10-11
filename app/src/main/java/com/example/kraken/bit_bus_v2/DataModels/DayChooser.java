package com.example.kraken.bit_bus_v2.DataModels;

public class DayChooser {

    private String day;
    private String label;

    public DayChooser() {
    }

    public DayChooser(String day, String label) {
        this.day = day;
        this.label = label;
    }

    /*
    =====================================Getter and Setter==========================================
     */

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
