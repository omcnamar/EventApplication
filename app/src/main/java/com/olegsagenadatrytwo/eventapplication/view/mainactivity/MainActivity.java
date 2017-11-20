package com.olegsagenadatrytwo.eventapplication.view.mainactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.olegsagenadatrytwo.eventapplication.R;
import com.olegsagenadatrytwo.eventapplication.entities.Event;
import com.olegsagenadatrytwo.eventapplication.entities.Events;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivity.DaggerMainActivityComponent;
import com.olegsagenadatrytwo.eventapplication.injection.mainactivity.MainActivityModule;
import com.olegsagenadatrytwo.eventapplication.view.mainactivity.adapters.EventsAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "MainActivity";
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

        //make the rest call
        presenter.getEvents("music");

        //initialize adapter
        adapter = new EventsAdapter(this);
        mTvError.setVisibility(View.GONE);

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
}
