package edu.calvin.kpb23students.calvindining;

import android.util.Log;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Handles getting the venues from the server
 *
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class CalvinDiningService extends Observable{
    private final InterfaceService service;
    private List<Venue> venues = new ArrayList<>();
    CalvinDiningService(Retrofit retrofit) {
        service = retrofit.create(InterfaceService.class);
        check();
    }
    /**
     * You must call this from the UI thread.
     * @return venues
     */
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     * Used to get events from the venues
     * @param venueName String of the venue
     * @return List<Meal> of the events that comes from the venueName
     */
    public List<Meal> getEvents(String venueName) {
        for (Venue venue : venues) {
            if (venueName.equals(venue.name)) {
                return venue.events;
            }
        }
        return new ArrayList<Meal>();
    }

    /**
     * Used by retrofit for making the venues.
     */
    public static class Venue {
        private String name;
        private List<Meal> events;

        /**
         *
         * @return String the name of the venue
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @return String the display name of a venue. This cuts out parts of the display name.
         */
        public String getDisplayName() {
            String cutName = name;
            cutName = cutName.replaceAll("Dining Hall", "");
            cutName = cutName.replaceAll("Cafe", "");
            return cutName;
        }
    }

    /**
     * Used by retrofit for making the meals which is used by the venues.
     */
    public static class Meal {

        private String name;
        private String startTime;
        private String endTime;
        private String description;
        private transient GregorianCalendar gregStartTime;
        private transient GregorianCalendar gregEndTime;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        private GregorianCalendar updateGregTime(GregorianCalendar gregTime, String strTime) {
            if (gregTime == null) {
                gregTime = DateTimeUtils.toGregorianCalendar(ZonedDateTime.parse(strTime));
            }
            return gregTime;
        }
        public GregorianCalendar getGregStartTime() { return updateGregTime(gregStartTime, startTime); }
        public GregorianCalendar getGregEndTime() {
            return updateGregTime(gregEndTime, endTime);
        }
    }

    /**
     * Retrofite interface for getting venues
     */
    private interface InterfaceService {
        @GET("venues") // TODO make this venues on server and here
        Call<List<Venue>> venues();
    }

    /**
     * gets data from server
     */
    public void check() {
        service.venues().enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                venues = response.body();
                setChanged();
                notifyObservers();
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Log.v("x", "Venue failed: " + t + " :oh no.");
            }
        });
    }
}
