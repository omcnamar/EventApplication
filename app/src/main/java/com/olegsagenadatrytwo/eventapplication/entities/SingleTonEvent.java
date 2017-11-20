package com.olegsagenadatrytwo.eventapplication.entities;

/**
 * Created by omcna on 11/20/2017.
 */

public class SingleTonEvent {
    private static SingleTonEvent mSingleTonEvent;
    private Event event;

    private SingleTonEvent(){
    }

    public static SingleTonEvent getInstance(){
        if(mSingleTonEvent == null){
            mSingleTonEvent = new SingleTonEvent();
            return mSingleTonEvent;
        }else{
            return mSingleTonEvent;
        }
    }

    public void setEvent(Event event){
        this.event = event;
    }

    public Event getEvent(){
        return event;
    }
}
