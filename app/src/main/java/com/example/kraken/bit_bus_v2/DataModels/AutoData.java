package com.example.kraken.bit_bus_v2.DataModels;

public class AutoData {

    // class variables
    private String name;
    private String phone;
    private String autoNumber;

    public AutoData(String name, String phone, String autoNumber) {
        this.name = name;
        this.phone = phone;
        this.autoNumber = autoNumber;
    }

    /*
    =====================================Getter and Setter==========================================
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAutoNumber() {
        return autoNumber;
    }

    public void setAutoNumber(String autoNumber) {
        this.autoNumber = autoNumber;
    }
}
