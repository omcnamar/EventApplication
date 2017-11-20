package com.olegsagenadatrytwo.eventapplication.view.mainactivity.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.olegsagenadatrytwo.eventapplication.R;
import com.olegsagenadatrytwo.eventapplication.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<Event> events = new ArrayList<>();
    private Context context;

    public EventsAdapter(Context context) {
        this.context = context;
    }

    public void setEvents(List<Event> events){
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_initial_info_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String title = events.get(position).getName().getText();
        String description = events.get(position).getDescription().getText();
        if(description != null) {
            if (description.length() > 100) {
                description = description.substring(0, 100) + "...";
            }
        }

        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);

        final String imageURL = events.get(position).getLogo().getUrl();
        if(imageURL != null){
            Glide.with(context).load(imageURL).into(holder.ivImage);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultimage, context.getApplicationContext().getTheme()));
            } else {
                holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defaultimage));
            }
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private ImageView ivImage;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

}