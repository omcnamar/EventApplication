package com.olegsagenadatrytwo.eventapplication.injection.detailactivity;

import com.olegsagenadatrytwo.eventapplication.view.detailactivity.DetailActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by omcna on 11/20/2017.
 */

@Module
public class DetailActivityModule {

    public DetailActivityModule() {
    }

    @Provides
    DetailActivityPresenter providesDetailActivityPresenter(){
        return new DetailActivityPresenter();
    }
}
