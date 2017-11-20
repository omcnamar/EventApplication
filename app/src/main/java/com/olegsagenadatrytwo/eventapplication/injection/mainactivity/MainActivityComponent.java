package com.olegsagenadatrytwo.eventapplication.injection.mainactivity;

import com.olegsagenadatrytwo.eventapplication.view.mainactivity.MainActivity;

import dagger.Component;

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {

    void insert(MainActivity mainActivity);
}
