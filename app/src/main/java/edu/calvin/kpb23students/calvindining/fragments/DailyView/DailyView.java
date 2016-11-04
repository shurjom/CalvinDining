package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.calvin.kpb23students.calvindining.MyApplication;
import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
 *     This class handles the daily view. This will show important things per day for the user.
 * </p>
 */
public class DailyView extends Fragment {
    public DailyView() {
        // Required empty public constructor
    }

    /**
     * Take data and show it to the user using a linear list.
     * @param inflater inflater to make scrolling possible
     * @param container container to put things in
     * @param savedInstanceState for saving states. Not used yet.
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {Log.d("x", "container is null :-/"); throw new RuntimeException("Expected non-null container!");}
        View view = inflater.inflate(R.layout.fragment_daily_view, container, false);

        // Added stuffs
        Log.v("X", "hi there !");

        // Make EventList
        // http://stackoverflow.com/a/25329322/2948122
        EventListAdapter timesEventAdapter = new EventListAdapter(container.getContext(), inflater, MyApplication.getMyApplication().getCalvinDiningService());
        ((ListView)view.findViewById(R.id.fragment_daily_view_times)).setAdapter(timesEventAdapter);
        // Test Events
        /*
        timesEventAdapter.setEvents(new EventListAdapter.Event[]{
                new EventListAdapter.Event("Beginning of Day", "I love the beginning of the day. It is so nice and the sun is amazing when it goes up. It is something that I care about.", new GregorianCalendar(2016, 10, 1, 0, 1), new GregorianCalendar(2016, 10, 1, 1, 0)),
                new EventListAdapter.Event("Breakfast cool", "I need to have breakfast to feel like a person who will have a good day.", new GregorianCalendar(2016, 10, 1, 6, 0), new GregorianCalendar(2016, 10, 1, 7, 0)),
                new EventListAdapter.Event("2nd Breakfast", "As the hobbits always say, you can't skip second breakfast", new GregorianCalendar(2016, 10, 1, 8, 0), new GregorianCalendar(2016, 10, 1, 9, 0)),
                new EventListAdapter.Event("Lunch", "I like to eat lunch alone", new GregorianCalendar(2016, 10, 1, 11, 23), new GregorianCalendar(2016, 10, 1, 12, 0)),
                new EventListAdapter.Event("Dinner", "dafdsafddasffdadasffdas", new GregorianCalendar(2016, 10, 1, 17, 0), new GregorianCalendar(2016, 10, 1, 18, 0)),
                new EventListAdapter.Event("BQV", "dsafsdfdasfdfsdsadasfdasfdfsafdfsadsfdsfadfsdsfdfsfdfdfsadsfdsafdfsdfsadfasdfasdsdfsadsfdfsdsfa", new GregorianCalendar(2016, 10, 1, 20, 0), new GregorianCalendar(2016, 10, 1, 21, 0)),
                new EventListAdapter.Event("End of day", "adfdsa\n fdsafdas\nd adsffdasf\n", new GregorianCalendar(2016, 10, 1, 22, 10), new GregorianCalendar(2016, 10, 1, 23, 0)),
        });
        */
        // Inflate the layout for this fragment
        return view;
    }
}
