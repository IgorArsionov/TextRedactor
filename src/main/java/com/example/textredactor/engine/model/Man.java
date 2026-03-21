package com.example.textredactor.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Man {
    private int id;
    private String name;
    private String country;
    private String city;
    private String description;

    private final List<ManRecord> manRecordList = new ArrayList<>();

    public Man() {
    }
    public Man(int id, String name, String country, String city, String description) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.description = description;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ManRecord> getManRecordList() {
        return manRecordList;
    }

    @Override
    public String toString() {
        return "Man{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", manRecordList=" + manRecordList +
                '}';
    }
}
