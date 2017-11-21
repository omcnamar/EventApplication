package com.olegsagenadatrytwo.eventapplication.view.mainactivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.olegsagenadatrytwo.eventapplication.R;
import com.olegsagenadatrytwo.eventapplication.entities.Event;
import com.olegsagenadatrytwo.eventapplication.entities.Events;
import com.olegsagenadatrytwo.eventapplication.entities.SingleTonEventsList;
import com.olegsagenadatrytwo.eventapplication.entities.SingleTonMyLocation;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivity.DaggerMainActivityComponent;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivity.MainActivityModule;
import com.olegsagenadatrytwo.eventapplication.view.mainactivity.adapters.EventsAdapter;
import com.olegsagenadatrytwo.eventapplication.view.mapactivity.MapsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 20;
    private static final String TAG = "MainActivity";
    @BindView(R.id.btnMap)
    Button mBtnMap;
    private String query;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.btnSearch)
    Button mBtnSearch;
    @BindView(R.id.tvError)
    TextView mTvError;
    @BindView(R.id.rvRecyclerView)
    RecyclerView mRvRecyclerView;

    @Inject
    MainActivityPresenter presenter;
    private EventsAdapter adapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bind ButterKnife
        ButterKnife.bind(this);

        //perform set up
        setUpDagger();
        initRecyclerView();
        initPresenter();

        //initialize adapter
        adapter = new EventsAdapter(this);
        mTvError.setVisibility(View.GONE);

        //ask for location permission
        boolean granted = checkLocationPermission();
        if (granted) {

            //if the current lat and lon are known get events else getLocation
            if (lat != null && lon != null) {
                presenter.getEvents("sports", lat, lon);
            } else {
                getLocation();
            }
        }

    }

    /**
     * method to set Up Dagger
     */
    private void setUpDagger() {
        DaggerMainActivityComponent.create().insert(this);
        DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule()).build().insert(this);
    }

    /**
     * method that initializes Recycler View
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRvRecyclerView.getContext(), layoutManager.getOrientation());
        mRvRecyclerView.setLayoutManager(layoutManager);
        mRvRecyclerView.addItemDecoration(dividerItemDecoration);
        mRvRecyclerView.setItemAnimator(itemAnimator);
    }

    /**
     * method that initializes Presenter
     */
    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRemote();
    }

    @Override
    public void showError(String error) {
        mTvError.setVisibility(View.VISIBLE);
        mTvError.setText(error);
    }

    @Override
    public void eventsLoadedUpdateUI(final Events events) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvError.setVisibility(View.GONE);
                if (events != null) {
                    SingleTonEventsList singleTonEventsList = SingleTonEventsList.getInstance();
                    singleTonEventsList.setEvents(events.getEvents());
                    if (events.getEvents().size() == 0) {
                        adapter.setEvents(new ArrayList<Event>());
                        adapter.notifyDataSetChanged();
                        showError("No Results");
                    } else {
                        adapter.setEvents(events.getEvents());
                        mRvRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    adapter.setEvents(new ArrayList<Event>());
                    adapter.notifyDataSetChanged();
                    showError("No results");
                }
            }
        });
    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        query = mSearchView.getQuery().toString();
        if (lat != null && lon != null) {
            presenter.getEvents(query.trim(), lat, lon);
        } else {
            getLocation();
        }
    }

    @OnClick(R.id.btnMap)
    public void onMapViewClicked() {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        getLocation();
                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    public void getLocation() {

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        lat = String.valueOf(location.getLatitude());
                        lon = String.valueOf(location.getLongitude());
                        SingleTonMyLocation singleTonMyLocation = SingleTonMyLocation.getInstance();
                        singleTonMyLocation.setLat(lat);
                        singleTonMyLocation.setLon(lon);
                        presenter.getEvents("sports", lat, lon);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 300, this);

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
