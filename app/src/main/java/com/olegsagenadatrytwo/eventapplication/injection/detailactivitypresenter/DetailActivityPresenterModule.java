package com.olegsagenadatrytwo.eventapplication.injection.detailactivitypresenter;

import com.olegsagenadatrytwo.eventapplication.view.detailactivity.DetailActivityPresenter;

import dagger.Module;

/**
 * Created by omcna on 11/20/2017.
 */

@Module
public class DetailActivityPresenterModule {
    private DetailActivityPresenter detailActivityPresenter;

    public DetailActivityPresenterModule(DetailActivityPresenter detailActivityPresenter) {
        this.detailActivityPresenter = detailActivityPresenter;
    }
}
