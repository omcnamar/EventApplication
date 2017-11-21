package com.olegsagenadatrytwo.eventapplication.entities;

/**
 * Created by omcna on 11/20/2017.
 */

public class SingleTonMyLocation {
    private static SingleTonMyLocation sSingleTonMyLocation;
    private String lat;
    private String lon;


    private SingleTonMyLocation(){
    }

    public static SingleTonMyLocation getInstance(){
        if(sSingleTonMyLocation == null){
            sSingleTonMyLocation = new SingleTonMyLocation();
            return sSingleTonMyLocation;
        }else{
            return sSingleTonMyLocation;
        }
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
