package edu.calvin.kpb23students.calvindining;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ListIterator;

/**
 * Created by Kristofer on 9/30/2016.
 */

public class EventListAdapter extends BaseAdapter {
    static class Event {
        public final String name;
        public final GregorianCalendar beginTime;
        public final GregorianCalendar endTime;

        public Event(String name, GregorianCalendar beginTime, GregorianCalendar endTime) {
            this.name = name;
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
                    // Sort events by end time ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    Arrays.sort(events, new Comparator<Event>() {
                        @Override
                        public int compare(Event o1, Event o2) {
                            return o1.endTime.compareTo(o2.endTime);
                        }
                    });
                    // Store latest end time into variable
                    int endHour = events[events.length - 1].endTime.get(Calendar.HOUR_OF_DAY);
                    // Set endHour to be the latest hour + 1 hour
                    endHour = Math.min(23, endHour + 1); // TODO fix if latest thing in schedule goes to 11pm or 23HH because hour 24 isn't allowed

                    // Sort events by start time -------------------------------------------------------------------------------
                    Log.d("X", "endHour comes from " + events[events.length - 1].name + " and is " + endHour);
                    // Sort events by begin time
                    Arrays.sort(events, new Comparator<Event>() {
                        @Override
                        public int compare(Event o1, Event o2) {
                            return o1.beginTime.compareTo(o2.beginTime);
                        }
                    });
                    int startHour = events[0].beginTime.get(Calendar.HOUR_OF_DAY);
                    // Set startHour to be first hour - 1 hour
                    startHour = Math.max(0, startHour - 1);

                    // Set displayItems
                    DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
                    Log.d("X", "startHour comes from " + events[0].name + " and is " + startHour);


                    // GregorianCalendar formattingCalendar = (GregorianCalendar) events[0].beginTime.clone();
                    //for (int fieldId = Calendar.MINUTE; fieldId <= Calendar.MILLISECOND; fieldId++) {
                    //    formattingCalendar.set(fieldId, 0);
                    //}
                    Log.v("X", "startHour = " + startHour + ", endHour = " + endHour);

                    // TODO Make this better. It looks terrible right now
                    // Version that does it per hour
                    /*
                    for (int i = startHour; i < endHour; i++) {
                        Log.v("X", "Running for loop");
                        Event displayEvent = null;

                        // Check to see if each hour is in the event.
                        for (Event event : events) {
                            if (event.beginTime.get(Calendar.HOUR_OF_DAY) <= i && i <= event.endTime.get(Calendar.HOUR_OF_DAY)) {
                                displayEvent = event;
                            }
                        }
                        formattingCalendar.set(Calendar.HOUR_OF_DAY, i);
                        DisplayItem displayItem = new DisplayItem(
                                timeFormat.format(formattingCalendar.getTime()), // startTime
                                timeFormat.format(formattingCalendar.getTime()), // endTime
                                displayEvent); // event

                        displayItems.add(displayItem);
                    }*/
                    // Add events that are empty so it can fill in the gaps this assumes events don't overlap.
                    // TODO what if events overlap? oh no
                    GregorianCalendar beginOverlap = (GregorianCalendar) events[0].beginTime.clone();
                    beginOverlap.set(Calendar.HOUR_OF_DAY, startHour);
                    beginOverlap.set(Calendar.MINUTE, 0);
                    GregorianCalendar endOverlap;

                    for (Event event: events) {
                        endOverlap = event.beginTime;
                        // Make time between events
                        DisplayItem betweenEvents = new DisplayItem(
                                timeFormat.format(beginOverlap.getTime()),
                                timeFormat.format(endOverlap.getTime()),
                                null
                        );
                        beginOverlap = event.endTime;
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
        TimeLabel tl = (TimeLabel)layoutInflater.inflate(R.layout.time_label, parent, false);
        DisplayItem displayItem = this.displayItems.get(position);

        // TODO use something better than an if else
        if (displayItem.event != null) {
            tl.set(true, displayItem.event.name, displayItem.beginTime, displayItem.endTime);
        } else {
            tl.set(false, displayItem.duration, displayItem.beginTime, displayItem.endTime);
        }
        return tl;
    }
}