package com.olegsagenadatrytwo.eventapplication.view.mainactivity;

import com.olegsagenadatrytwo.eventapplication.BasePresenter;
import com.olegsagenadatrytwo.eventapplication.BaseView;
import com.olegsagenadatrytwo.eventapplication.entities.Events;

public interface MainActivityContract {

    interface View extends BaseView {
        void eventsLoadedUpdateUI(Events events);
    }

    interface Presenter extends BasePresenter<View> {
        void attachRemote();
        void getEvents(String query, String lat, String lon);
    }

}
