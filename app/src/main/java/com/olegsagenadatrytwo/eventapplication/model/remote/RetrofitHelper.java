package com.olegsagenadatrytwo.eventapplication.model.remote;

import com.google.android.gms.common.api.GoogleApiClient;
import com.olegsagenadatrytwo.eventapplication.entities.Events;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


class RetrofitHelper {

    //https://www.eventbriteapi.com/v3/events/search/?q=sports&location.within=1000mi&location.latitude=34.2408520&location.longitude=-84.3608580&token=NTEQ4UDIBIRDQSFNDE7Q&expand=venue
    private static final String EVENTBRITE_TOKEN = "NTEQ4UDIBIRDQSFNDE7Q";
    private static final String EVENTBRITE_BASE_URL = "https://www.eventbriteapi.com/";
    private static final String EVENTBRITE_EVENTS_PATH = "v3/events/search/";
    private static GoogleApiClient googleApiClient;

    private static Retrofit create() {
        //create a logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //create a custom client to add the interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(EVENTBRITE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    static Call<Events> getEvents(String query, String lat, String lon) {
        Retrofit retrofit = create();
        PostService eventService = retrofit.create(PostService.class);
        return eventService.getEvents(query, "1000mi", lat, lon, EVENTBRITE_TOKEN, "venue");
    }

    interface PostService {

        @GET(EVENTBRITE_EVENTS_PATH)
        Call<Events> getEvents(@Query("q") String q,
                               @Query("location.within") String locationWidth,
                               @Query("location.latitude") String lat,
                               @Query("location.longitude") String lon,
                               @Query("token") String token,
                               @Query("expand") String expand);
    }

}
