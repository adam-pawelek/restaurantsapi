package com.example.RestaurantsAPI;


import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is created to hold data that will be sent as a JSON.
 * First parameter of class will be title of List and the second will be list of restaurant objects
 */
public class ChosenRestaurants {


    private String title;
    private LinkedList restaurants;

    /**
     * @param title - Title of returned List of restaurants
     * @param restaurants - list of restaurants objects (meeting the requirements of the task)
     */
    public ChosenRestaurants(String title, LinkedList<Restaurant> restaurants){
        this.restaurants = restaurants;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRestaurants(LinkedList restaurants) {
        this.restaurants = restaurants;
    }

    public LinkedList getRestaurants() {
        return restaurants;
    }


}
