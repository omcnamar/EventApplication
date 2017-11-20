package com.olegsagenadatrytwo.eventapplication.model.remote;

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

    public static final String EVENTBRITE_TOKEN = "NTEQ4UDIBIRDQSFNDE7Q";
    public static final String EVENTBRITE_BASE_URL = "https://www.eventbriteapi.com/v3/";
    public static final String EVENTBRITE_EVENTS_PATH = "events/search/";

    private static Retrofit create(){

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

    static Call<Events> getEvents(String query){
        Retrofit retrofit = create();
        PostService eventService = retrofit.create(PostService.class);
        return eventService.getEvents("", EVENTBRITE_TOKEN);
    }

    interface PostService {

        @GET(EVENTBRITE_EVENTS_PATH)
        Call<Events> getEvents(@Query("categories") String category, @Query("token") String token);
    }

}
