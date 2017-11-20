package com.olegsagenadatrytwo.eventapplication.model.remote;


import com.olegsagenadatrytwo.eventapplication.entities.Events;

public interface IRemote {
    void sendData(Events events);
}
