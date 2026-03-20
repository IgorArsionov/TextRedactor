package com.example.textredactor.engine.model;

public class Man {
    private String name;
    private String country;
    private String city;
    private String description;

    public Man() {
    }
    public Man(String name, String country, String city, String description) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.description = description;
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

    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
