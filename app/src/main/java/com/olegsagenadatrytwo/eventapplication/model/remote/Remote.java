package com.olegsagenadatrytwo.eventapplication.model.remote;


import android.support.annotation.NonNull;

import com.olegsagenadatrytwo.eventapplication.entities.Events;

public class Remote {

    private IRemote iremote;
    private static final String TAG = "Remote";

    public Remote(IRemote iremote){
        this.iremote = iremote;
    }

    public void getEvents(String query){
        retrofit2.Call<Events> postsDataCall = RetrofitHelper.getEvents(query);
        postsDataCall.enqueue(new retrofit2.Callback<Events>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Events> call, @NonNull retrofit2.Response<Events> response) {
                Events events = response.body();
                iremote.sendData(events);
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Events> call, @NonNull Throwable t) {
                iremote.sendData(null);
            }
        });
    }

}
