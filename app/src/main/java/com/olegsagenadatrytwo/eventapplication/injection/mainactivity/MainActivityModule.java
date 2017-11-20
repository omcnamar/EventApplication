package com.olegsagenadatrytwo.eventapplication.injection.mainactivity;

import com.olegsagenadatrytwo.eventapplication.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    public MainActivityModule() {
    }

    @Provides
    MainActivityPresenter providesMainActivityPresenter(){
        return new MainActivityPresenter();
    }
}
