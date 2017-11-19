package com.olegsagenadatrytwo.eventapplication;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);
    void removeView();

}
