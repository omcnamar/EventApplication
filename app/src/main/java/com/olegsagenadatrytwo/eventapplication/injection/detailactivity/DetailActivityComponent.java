package com.olegsagenadatrytwo.eventapplication.injection.detailactivity;

import com.olegsagenadatrytwo.eventapplication.view.detailactivity.DetailActivity;

import dagger.Component;

/**
 * Created by omcna on 11/20/2017.
 */

@Component(modules = DetailActivityModule.class)
public interface DetailActivityComponent {

    void insert(DetailActivity detailActivity);

}
