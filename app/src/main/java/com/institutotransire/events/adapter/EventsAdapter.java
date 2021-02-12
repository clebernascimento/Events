package com.institutotransire.events.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.institutotransire.events.R;
import com.institutotransire.events.controller.DateTime;
import com.institutotransire.events.databinding.ItemEventsBinding;
import com.institutotransire.events.services.model.Contains;
import com.institutotransire.events.services.model.Events;
import com.institutotransire.events.util.Formatters;
import com.institutotransire.events.util.ImgUtil;
import com.institutotransire.events.util.ScreenWidth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import lombok.SneakyThrows;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    private ArrayList<Events> mEventsArrayList;
    private final Context context;
    public OnClick listener;
    private NumberFormat nf = Formatters.numFormat();

    public interface OnClick {
        void selectEvents(Events events, int position);
    }

    public class EventsHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout cardEvents;
        public ImageView mImageEvents;
        public TextView mNameEvents;
        public TextView mDateEvents;
        public TextView mPriceEvents;
        public TextView mLocation;
        public TextView mDescription;

        public EventsHolder(@NonNull View itemView) {
            super(itemView);
            cardEvents = itemView.findViewById(R.id.cardLayout);
            mImageEvents = itemView.findViewById(R.id.imgEvents);
            mNameEvents = itemView.findViewById(R.id.nameEvents);
            mDateEvents = itemView.findViewById(R.id.dateEvents);
            mPriceEvents = itemView.findViewById(R.id.priceEvents);
            mLocation = itemView.findViewById(R.id.loactionEvents);
            mDescription = itemView.findViewById(R.id.descriptionEvents);
        }

        public void bindClick(Events events, int position, OnClick listener) {
            itemView.setOnClickListener(view -> listener.selectEvents(events, position));
        }
    }

    public EventsAdapter(Context c, ArrayList<Events> eventsArrayList, OnClick _Click) {
        mEventsArrayList = eventsArrayList;
        this.context = c;
        listener = _Click;
    }

    @NonNull
    @Override
    public EventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events, parent, false);
        EventsHolder eventsHolder = new EventsHolder(view);
        return eventsHolder;
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull EventsHolder holder, int position) {
        Events events = mEventsArrayList.get(position);
        ImgUtil.requestImg(context, holder.mImageEvents, events.getImage());
        holder.mNameEvents.setText(events.getTitle());
        holder.mDateEvents.setText("Data: " + DateTime.getDate(events.getDate()));
        holder.mPriceEvents.setText("R$ " + nf.format(events.getPrice()));
        if (events.getLatitude() != null && events.getLongitude() != null) {
            holder.mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("http://maps.google.com/maps?daddr=" + events.getLatitude() + "," + events.getLongitude());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(mapIntent);
                    } else {
                        Toast.makeText(context, "Erro ao localizar evento", Toast.LENGTH_SHORT);
                    }
                }
            });
        } else {
            Toast.makeText(context, "Erro ao localizar evento", Toast.LENGTH_SHORT);
        }
        holder.bindClick(events, position, listener);
    }

    @Override
    public int getItemCount() {
        return mEventsArrayList.size();
    }
}
