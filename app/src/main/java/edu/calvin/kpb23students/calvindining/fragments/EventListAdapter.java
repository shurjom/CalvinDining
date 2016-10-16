package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.Comparator;

import edu.calvin.kpb23students.calvindining.R;

/**
 * Created by Kristofer on 9/30/2016.
 */

public class EventListAdapter extends BaseAdapter {
    static class Event {
        public final String name;
        public final String description;
        public final GregorianCalendar beginTime;
        public final GregorianCalendar endTime;

        public Event(String name, String description, GregorianCalendar beginTime, GregorianCalendar endTime) {
            this.name = name;
            this.description = description;
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }
    // Only difference is from times gregorian calendar to string.
    static class DisplayItem {
        public final String beginTime;
        public final String endTime;
        public final Event event;
        public String duration = "";
        public DisplayItem(String beginTime, String endTime, Event event){
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.event = event;
        }
        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    final Context context;
    final LayoutInflater layoutInflater;
    final ArrayList<DisplayItem> displayItems;

    public EventListAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        displayItems = new ArrayList<DisplayItem>();
        // Set up events
        setEvents(new Event[]{});
    }

    public void setEvents(final Event[] events) {
        // http://stackoverflow.com/a/21862750/2948122
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                displayItems.clear();
                Log.d("X", "run: setevents " + events.length);
                if (events.length > 0) {
                    // Sort events by start time -------------------------------------------------------------------------------
                    Arrays.sort(events, new Comparator<Event>() {
                        @Override
                        public int compare(Event o1, Event o2) {
                            return o1.beginTime.compareTo(o2.beginTime);
                        }
                    });
                    int startHour = events[0].beginTime.get(Calendar.HOUR_OF_DAY);

                    // Set displayItems
                    DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

                    // TODO Make this better. It looks terrible right now
                    // Version that does it per hour
                    // Add events that are empty so it can fill in the gaps this assumes events don't overlap.
                    // TODO what if events overlap? oh no
                    GregorianCalendar beginOverlap = (GregorianCalendar) events[0].beginTime.clone();
                    beginOverlap.set(Calendar.HOUR_OF_DAY, startHour);
                    beginOverlap.set(Calendar.MINUTE, 0);
                    GregorianCalendar endOverlap;

                    // TODO make this not use DisplayItem
                    DisplayItem betweenEvents = new DisplayItem(
                            null,
                            null,
                            null
                    );

                    for (Event event: events) {
                        // Make time between events
                        betweenEvents.setDuration("DURATION"); // TODO duration

                        // Make event display item
                        DisplayItem displayItem = new DisplayItem(
                                timeFormat.format(event.beginTime.getTime()),
                                timeFormat.format(event.endTime.getTime()),
                                event
                        );
                        displayItems.add(betweenEvents);
                        displayItems.add(displayItem);
                    }
                    betweenEvents.setDuration("DURATION");
                    displayItems.add(betweenEvents);
                }
                notifyDataSetChanged(); // rerun getView to notice new changes
            }
        });
    }

    @Override
    public int getCount() {
        return this.displayItems.size();
    }

    @Override
    public Object getItem(int position) {
        return displayItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("X", "Changing text " + String.valueOf(position));

        DisplayItem displayItem = this.displayItems.get(position);

        // TODO use something better than an if else
        if (displayItem.event != null) {
            // If event
            TimeLabel timeLabel = (TimeLabel)layoutInflater.inflate(R.layout.time_label, parent, false);
            timeLabel.set(true, displayItem.event.name, displayItem.beginTime, displayItem.endTime, displayItem.event.description);
            return timeLabel;
        } else {
            // BetweenEvents
            TimeLabelBetween timeLabelBetween = (TimeLabelBetween) layoutInflater.inflate(R.layout.time_label_between, parent, false);
            timeLabelBetween.set(false, displayItem.duration);
            return timeLabelBetween;
        }
    }
}
