package com.olegsagenadatrytwo.eventapplication.view.detailactivity;

import com.olegsagenadatrytwo.eventapplication.BasePresenter;
import com.olegsagenadatrytwo.eventapplication.BaseView;

/**
 * Created by omcna on 11/20/2017.
 */

public interface DetailActivityContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<DetailActivityContract.View> {
    }
}
