package com.example.RestaurantsAPI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * This class is to store sections that will be returned as an api request.
 */
public class Sections {



    private LinkedList sections;

    /**
     *
     * @param sections - List of ChosenRestaurants objects
     */

    public Sections(LinkedList sections) {
        this.sections = sections;
    }


    public void setSections(LinkedList sections) {
        this.sections = sections;
    }

    public LinkedList getSections() {
        return sections;
    }


}
