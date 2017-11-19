package com.olegsagenadatrytwo.eventapplication.view.mainactivity;

import com.olegsagenadatrytwo.eventapplication.BasePresenter;
import com.olegsagenadatrytwo.eventapplication.BaseView;

public interface MainActivityContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
        void attachRemote();
        void getEvents(String query);
    }

}
