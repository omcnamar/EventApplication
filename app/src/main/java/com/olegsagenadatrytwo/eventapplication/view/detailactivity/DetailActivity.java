package com.olegsagenadatrytwo.eventapplication.view.detailactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.olegsagenadatrytwo.eventapplication.R;
import com.olegsagenadatrytwo.eventapplication.entities.Event;
import com.olegsagenadatrytwo.eventapplication.entities.SingleTonEvent;
import com.olegsagenadatrytwo.eventapplication.injection.detailactivity.DaggerDetailActivityComponent;
import com.olegsagenadatrytwo.eventapplication.injection.detailactivity.DetailActivityModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    @Inject
    DetailActivityPresenter presenter;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.ivImage)
    CircleImageView mIvImage;
    @BindView(R.id.tvDescription)
    TextView mTvDescription;
    private String eventId;
    private Menu menu;
    private boolean liked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Event Detail");
        //bind ButterKnife
        ButterKnife.bind(this);

        //init dagger
        setUpDagger();

        //get the event that was clicked
        SingleTonEvent singleTonEvent = SingleTonEvent.getInstance();
        Event event = singleTonEvent.getEvent();

        //update UI with the events info
        String title = event.getName().getText();
        String description = event.getDescription().getText();
        String imageURL = event.getLogo().getUrl();
        eventId = event.getId();

        mTvTitle.setText(title);
        mTvDescription.setText(description);
        Glide.with(this).load(imageURL).into(mIvImage);

    }

    /**
     * method to set Up Dagger
     */
    private void setUpDagger() {
        DaggerDetailActivityComponent.create().insert(this);
        DaggerDetailActivityComponent.builder().detailActivityModule(new DetailActivityModule()).build().insert(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_bar, menu);
        this.menu = menu;

        //when creating action bar check the shared preference if the user has liked this event
        List<String> savedIds = presenter.getSavedEvents(this);

        for(String id : savedIds){
            if(id.equals(eventId)){
                menu.findItem(R.id.actionLike).setIcon(R.drawable.ic_like_48dp);
                liked = true;
                break;
            }else{
                menu.findItem(R.id.actionLike).setIcon(R.drawable.ic_unlike);
                liked = false;
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.actionLike:
                if(!liked) {
                    presenter.saveEvent(eventId, this);
                    item.setIcon(R.drawable.ic_like_48dp);
                    liked = true;
                }else{
                    presenter.removeEvent(eventId, this);
                    item.setIcon(R.drawable.ic_unlike);
                    liked = false;
                }
                return true;
        }
        return true;
    }
}
