package com.olegsagenadatrytwo.eventapplication.injection.mainactivitypresenter;

import com.olegsagenadatrytwo.eventapplication.model.remote.Remote;
import com.olegsagenadatrytwo.eventapplication.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityPresenterModule {

    private MainActivityPresenter mainActivityPresenter;

    public MainActivityPresenterModule(MainActivityPresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;
    }

    @Provides
    Remote providesRemote(){
        return new Remote(mainActivityPresenter);
    }
}