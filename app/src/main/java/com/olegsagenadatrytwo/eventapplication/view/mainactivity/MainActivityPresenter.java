package com.olegsagenadatrytwo.eventapplication.view.mainactivity;


public class MainActivityPresenter implements MainActivityContract.Presenter {

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

    }

    @Override
    public void getEvents(String query) {
    }
}
