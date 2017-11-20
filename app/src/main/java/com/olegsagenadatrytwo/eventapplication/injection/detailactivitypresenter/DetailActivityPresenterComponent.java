package com.olegsagenadatrytwo.eventapplication.injection.detailactivitypresenter;

import com.olegsagenadatrytwo.eventapplication.view.detailactivity.DetailActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by omcna on 11/20/2017.
 */

@Singleton
@Component(modules = {DetailActivityPresenterModule.class})
public interface DetailActivityPresenterComponent {

    void insert(DetailActivityPresenter detailActivityPresenter);

}
