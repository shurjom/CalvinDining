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
 * Created by Kristofer on 2016-10-22.
 */
public class CalvinDiningService extends Observable{
    private final InterfaceService service;
    private List<Meal> today = new ArrayList<>();
    CalvinDiningService(Retrofit retrofit) {
        service = retrofit.create(InterfaceService.class);
        check();
    }

    /**
     * You must call this from the UI thread.
     * @return today
     */
    public List<Meal> getToday() {
        return today;
    }

    public static class Meal {
        String name;
        String startTime;
        String endTime;
        String description;
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
        public GregorianCalendar getGregStartTime() {
            return updateGregTime(gregStartTime, startTime);
        }
        public GregorianCalendar getGregEndTime() {
            return updateGregTime(gregEndTime, endTime);
        }
    }

    private interface InterfaceService {
        @GET("today")
        Call<List<Meal>> today();
    }

    public void check() {
        service.today().enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                today = response.body();
                setChanged();
                notifyObservers();
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Log.v("x", "Today failed: " + t + " :oh no.");
            }
        });
    }
}
