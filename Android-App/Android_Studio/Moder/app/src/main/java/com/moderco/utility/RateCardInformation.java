package com.moderco.utility;

/**
 * Created by Ethan on 4/13/2015.
 */
public class RateCardInformation {
    private String id;
    private String description;

    public RateCardInformation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL(){
        return "http://www.moderapp.com/images/" + id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
