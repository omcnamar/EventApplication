package com.olegsagenadatrytwo.eventapplication.view.detailactivity;

import android.content.Context;

import com.olegsagenadatrytwo.eventapplication.BasePresenter;
import com.olegsagenadatrytwo.eventapplication.BaseView;

import java.util.List;

/**
 * Created by omcna on 11/20/2017.
 */

public interface DetailActivityContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<DetailActivityContract.View> {
        void saveEvent(String eventId, Context context);
        void removeEvent(String eventId, Context context);
        List<String> getSavedEvents(Context context);
    }
}
