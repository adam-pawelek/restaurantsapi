package com.example.RestaurantsAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for
 * api query handling
 */
@RestController
@RequestMapping(path = "discovery")


public class RestaurantsController {

    /**
     * This is method responsible for response to get discovery requests
     * @param lat  latitude
     * @param lon  Longitude
     * @return List of Resteurants meeting the requirements of the task
     * @throws JSONException
     */
    @GetMapping
    public Sections getDiscovery(Double lat, Double lon) throws JSONException {

        JSONArray  jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sections", jsonArray);
        }catch (Exception e){

        }


        ReadJsonFile readJsonFile = new ReadJsonFile(lon,lat);
        LinkedList<Restaurant> nearRestaurants = new LinkedList<Restaurant>();
        nearRestaurants = (LinkedList<Restaurant>) readJsonFile.findAllNear();


        // Popular Restaurants
        LinkedList<Restaurant> popularRestaurantsList = (LinkedList<Restaurant>) readJsonFile.findPopularRestaurants(nearRestaurants);
        ChosenRestaurants popularRestaurants = new ChosenRestaurants("Popular Restaurants", popularRestaurantsList );
        //najnowsze
        LinkedList<Restaurant> newRestaurantsList = (LinkedList<Restaurant>) readJsonFile.findNewRestaurants(nearRestaurants);
        ChosenRestaurants newRestaurants = new ChosenRestaurants("New Restaurants", newRestaurantsList);
        //najblizsze
        LinkedList<Restaurant> nearbyRestaurantsList = (LinkedList<Restaurant>) readJsonFile.findClosestRestaurants(nearRestaurants);
        ChosenRestaurants nearbyRestaurants = new ChosenRestaurants("Nearby Restaurants", nearbyRestaurantsList);


        LinkedList<ChosenRestaurants> ChosenRestaurantsList = new LinkedList<ChosenRestaurants>();
        ChosenRestaurantsList.add(popularRestaurants);
        ChosenRestaurantsList.add(newRestaurants);
        ChosenRestaurantsList.add(nearbyRestaurants);

        Sections result = new Sections(ChosenRestaurantsList);

        return result ;
    }

}
