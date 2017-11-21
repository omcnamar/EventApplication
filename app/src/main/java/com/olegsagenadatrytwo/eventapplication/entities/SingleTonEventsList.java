package com.olegsagenadatrytwo.eventapplication.entities;

import java.util.List;

/**
 * Created by omcna on 11/20/2017.
 */

public class SingleTonEventsList {
    private static SingleTonEventsList mSingleTonEvent;
    private List<Event> events;

    private SingleTonEventsList(){
    }

    public static SingleTonEventsList getInstance(){
        if(mSingleTonEvent == null){
            mSingleTonEvent = new SingleTonEventsList();
            return mSingleTonEvent;
        }else{
            return mSingleTonEvent;
        }
    }

    public void setEvents(List<Event> events){
        this.events = events;
    }

    public List<Event> getEvents(){
        return events;
    }
}
