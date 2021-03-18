package com.example.RestaurantsAPI;



import org.apache.tomcat.jni.Local;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * This class is created for Reading JSON from file and to perform operations on data from a JSON file.
 */
public class ReadJsonFile {
    private double x  ;
    private double y ;
    private String first = "src/JSONN/restaurants.json";
    private String contents;
    private JSONObject myJson;
    private JSONArray restaurantsJSON;
    private Restaurant restaurant;
    private List restaurantList;

    /**
     * @param x  latitude
     * @param y  Longitude
     */
    public ReadJsonFile(double x, double y) {
        this.x = x;
        this.y = y;
        restaurantList = new LinkedList<Restaurant>();
        try{
            this.contents = new String (Files.readAllBytes(Paths.get(first)));
            this.myJson = new JSONObject(contents);
            this.restaurantsJSON = myJson.getJSONArray("restaurants");

        } catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }


    /**
     * This function tells is resteurant from JSONOBJECT is
     * closer than 1.5 km from us
     * @param eatery Restaurant JSON Object
     * @return True if restaurant is closer than 1.5 km
     * @throws JSONException
     */
    public boolean isNearLocation(JSONObject eatery) throws JSONException {
        JSONArray locations = eatery.getJSONArray("location");
        double myX = (double) locations.get(0);
        double myY = (double) locations.get(1);
        if (distance(myX,x,myY,y,0.00 ,0.00) > 1500 * 1500 ){
            return false;
        }
        else {
            return true;
        }


    }


    /**
     *
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * I returned result without square root to increase the accuracy of calculations
     *
     * @param lat1 Start latitude
     * @param lat2 End latitude
     * @param lon1 Start longitude
     * @param lon2 End longitude
     * @param el1 Start altitude
     * @param el2 End altitude
     * @return  I returned result without square root to increase the accuracy of calculations (meters)
     */
    public  double distance(double lat1, double lat2, double lon1,
                            double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return distance;
    }


    /**
     *This function find all Restaurants that are closer than 1.5 km from your place
     * @return List of all restaurants that are closer than 1.5 km
     * @throws JSONException
     */
   public List<Restaurant> findAllNear() throws JSONException {
        restaurantList = new LinkedList();
        JSONObject helpMe;
        for (int i = 0; i < restaurantsJSON.length(); i++){

            if (isNearLocation(restaurantsJSON.getJSONObject(i))) {
                helpMe = restaurantsJSON.getJSONObject(i);
                restaurant = new Restaurant();
                restaurant.setBlurhash((String) helpMe.get("blurhash"));
                restaurant.setLaunch_date((String) helpMe.get("launch_date"));
                restaurant.setName((String) helpMe.get("name"));
                restaurant.setOnline((Boolean) helpMe.get("online"));
                restaurant.setPopularity((Double) helpMe.get("popularity"));
                JSONArray helpLocation = (JSONArray) helpMe.get("location");
                double myX = (double) helpLocation.get(0);
                double myY = (double) helpLocation.get(1);
                ArrayList <Double> helpPutInList = new ArrayList<Double>();
                helpPutInList.add(myX);
                helpPutInList.add(myY);
                restaurant.setLocation(helpPutInList);
                restaurantList.add(restaurant);

            }
        }
        return restaurantList;
    }

    /**
     * This function find 10 most popular restaurants on the list near Restorations
     * @param nearRestaurants List of all restaurants that are closer than 1.5 km
     * @return 10 most popular restaurant in near Restaurants list
     * @throws JSONException
     */
  public   List<Restaurant> findPopularRestaurants(List<Restaurant> nearRestaurants) throws JSONException {
        List<Restaurant> popularRestaurants = new LinkedList<Restaurant>();
        ArrayList<Restaurant> jsonValues = new ArrayList<Restaurant>();
        for (int i = 0; i < nearRestaurants.size(); i++ ){
            jsonValues.add(nearRestaurants.get(i));
        }
        Collections.sort( jsonValues, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant a, Restaurant b) {
                double valA = 0;
                double valB = 0;
                valA = (double) a.getPopularity();
                valB = (double) b.getPopularity();
                if( valA > valB){
                    return  -1;
                }
                else {
                    return 1;
                }

            }
        });
        for (int i = 0; i < 10 && i < jsonValues.size(); i++){
            popularRestaurants.add(jsonValues.get(i));
        }
        return popularRestaurants;
    }


    /**
     * This method finds 10 newest restaurants on near Restaurants list but there is one condition aunch_date must be no older than 4 months.
     * @param nearRestaurants List of all restaurants that are closer than 1.5 km
     * @return 10 newest  restaurant in near Restaurants list
     * @throws JSONException
     */
   public List<Restaurant> findNewRestaurants(List<Restaurant> nearRestaurants) throws JSONException {
        List<Restaurant> newRestaurants = new LinkedList<Restaurant>();
        ArrayList<Restaurant> jsonValues = new ArrayList<Restaurant>();

        for (int i = 0; i < nearRestaurants.size(); i++ ){
            jsonValues.add(nearRestaurants.get(i));
        }
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        Collections.sort( jsonValues, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant a, Restaurant b) {
                String sDateA = new String();
                String sDateB = new String();
                sDateA = (String) a.getLaunch_date();
                sDateB = (String) b.getLaunch_date();

                Date dateA = new Date();
                Date dateB = new Date();
                try {
                    dateA = formatter.parse(sDateA);
                    dateB = formatter.parse(sDateB);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if( dateA.compareTo(dateB) > 0){
                    return  -1;
                }
                else if ( dateA.compareTo(dateB) == 0){
                    return 0;
                }
                else {
                    return 1;
                }
            }
        });

        Date dateNow = new Date();
        try {
            dateNow  =  formatter.parse(String.valueOf(LocalDateTime.now()));
        }catch (Exception e){
            System.out.println("Date Now is not working");
        }

        String sArrayDate = new String(); //This is for next for
        Date arrayDate = new Date();

        Date currentDate = new Date();    //This part is for current date
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.MONTH, -4);
        Date currentDateMinusFour =  c.getTime();


        for (int i = 0; i < 10 && i < jsonValues.size() ; i++){
            sArrayDate = (String) jsonValues.get(i).getLaunch_date();
            try {
                arrayDate = formatter.parse(sArrayDate);
            }
            catch (Exception e){

            }
            if (currentDateMinusFour.compareTo(arrayDate) < 0) {
                newRestaurants.add(jsonValues.get(i));
            }
        }
        return newRestaurants;
    }

    /**
     * This method finds 10 closets restaurants in the Near Restaurants list.
     * @param nearRestaurants  List of all restaurants that are closer than 1.5 km
     * @return 10 closest restaurants in the Near Restaurants list.
     * @throws JSONException
     */
  public   List<Restaurant> findClosestRestaurants(List<Restaurant> nearRestaurants) throws JSONException {
        List<Restaurant> closestRestaurants = new LinkedList<Restaurant>();
        ArrayList<Restaurant> jsonValues = new ArrayList<Restaurant>();
        for (int i = 0; i < nearRestaurants.size(); i++ ){
            jsonValues.add(nearRestaurants.get(i));
        }
        Collections.sort( jsonValues, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant a, Restaurant b) {
                double valAX = 0;
                double valAY = 0;
                double valBX = 0;
                double valBY = 0;
                valAX = (double) a.getLocation().get(0);
                valAY = (double) a.getLocation().get(1);
                valBX = (double) b.getLocation().get(0);
                valBY = (double) b.getLocation().get(1);

                if( distance(valAX,x,valAY,y,0 ,0) < distance(valBX,x,valBY,y,0,0)){
                    return  -1;
                }
                else {
                    return 1;
                }
            }
        });
        for (int i = 0; i < 10 && i < jsonValues.size(); i++){
            closestRestaurants.add(jsonValues.get(i));
        }
        return closestRestaurants;
    }



}
