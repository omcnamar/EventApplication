package com.olegsagenadatrytwo.eventapplication.view.mainactivity;


import android.util.Log;

import com.olegsagenadatrytwo.eventapplication.entities.Events;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivitypresenter.DaggerMainActivityPresenterComponent;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivitypresenter.MainActivityPresenterModule;
import com.olegsagenadatrytwo.eventapplication.model.remote.IRemote;
import com.olegsagenadatrytwo.eventapplication.model.remote.Remote;

import javax.inject.Inject;

public class MainActivityPresenter implements MainActivityContract.Presenter, IRemote {

    public static final String TAG = "MainActivityPresenter";
    @Inject
    Remote remote;
    private MainActivityContract.View view;

    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        this.view = null;
    }

    @Override
    public void attachRemote(){
        DaggerMainActivityPresenterComponent
                .builder()
                .mainActivityPresenterModule(new MainActivityPresenterModule(this))
                .build()
                .insert(this);
    }

    @Override
    public void getEvents(String query, String lat, String lon) {
        remote.getEvents(query, lat, lon);
    }

    @Override
    public void sendData(Events events) {
        Log.d(TAG, "sendData: " + events.getEvents().size());
        view.eventsLoadedUpdateUI(events);
    }
}
