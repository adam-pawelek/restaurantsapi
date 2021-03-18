package com.example.RestaurantsAPI;


import java.util.List;


/**
 * This class is created to holds in class data from JSON
 */
public class Restaurant {
    private String blurhash;
    private String launch_date;
    private List location;
    private String name;
    private Boolean online;
    private Double popularity;

    public Restaurant() {
    }

    /**
     * @param blurhash
     * @param launch_date -launch date of restaurant in yyyy-mm-dd format
     * @param location - this is an array of latitude and longitude
     * @param name - name of restaurant
     * @param online
     * @param popularity - How popular is restaurant
     */
    public Restaurant(String blurhash, String launch_date, List location, String name, Boolean online, Double popularity) {
        this.blurhash = blurhash;
        this.launch_date = launch_date;
        this.location = location;
        this.name = name;
        this.online = online;
        this.popularity = popularity;
    }

    public void setBlurhash(String blurhash) {
        this.blurhash = blurhash;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public void setLocation(List location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getBlurhash() {
        return blurhash;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public List getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Boolean getOnline() {
        return online;
    }

    public Double getPopularity() {
        return popularity;
    }
    @Override
    public String toString(){
        String result = new String();
        result ="";
        result = result + " " + this.blurhash.toString() + "\n";
        result = result + " " + this.launch_date.toString() + "\n";
        result = result + " " + this.location.get(0).toString() + " " + this.location.get(1).toString() + "\n";
        result = result + " " + this.name.toString() + "\n";
        result = result + " " + this.online.toString() + "\n";
        result = result + " " + this.popularity.toString() + "\n";
        return result;
    }
}
