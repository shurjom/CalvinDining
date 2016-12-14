package edu.calvin.kpb23students.calvindining;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kristofer on 2016-12-03.
 */

public class JavaService extends Observable{
    private final InterfaceService service;
    private List<CalvinDiningService.Venue> venues = new ArrayList<>();
    private User user = new User();
    private Poll commonsPoll = new Poll();
    private Poll knollPoll = new Poll();
    private String username, password;
    private final SharedPreferences sharedPreferences;
    private final String USERNAME_KEY = "username";
    private final String PASSWORD_KEY = "password";
    final private Context context;

    JavaService(Retrofit retrofit, SharedPreferences sharedPreferences, Context context) {
        service = retrofit.create(InterfaceService.class);
        this.sharedPreferences = sharedPreferences;
        this.username = sharedPreferences.getString(USERNAME_KEY, null);
        this.password = sharedPreferences.getString(PASSWORD_KEY, null);
        this.context = context;

        check();
    }

    public static class User {
        private String userName;
        private String password;
        private int mealCount;
        private int id;

        public User() {

        }

        public User(String userName, String password, int mealCount, int id) {
            this.userName = userName;
            this.password = password;
            this.mealCount = mealCount;
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public int getMealCount() {
            return mealCount;
        }

        public int getId() {
            return id;
        }

        public void setMealCount(int mealCount) {
            this.mealCount = mealCount;
        }
    }

    public static class Poll {
        private int id = 0;
        private String question = "";
        private String questionType = "";
        private String option1 = "";
        private String option2 = "";
        private String option3 = "";
        private String option4 = "";

        public Poll(){}

        public Poll(int id, String question, String option1, String option2, String option3, String option4) {
            this.id = id;
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
        }

        public int getId() { return id; }

        public String getQuestion() { return question; }
        public String getOption1() { return option1; }
        public String getOption2() { return option2; }
        public String getOption3() { return option3; }
        public String getOption4() { return option4; }
    }

    private interface InterfaceService {
        @GET("user/{user}")
        Call<User> user(@Path("user") String username);

        @POST("users")
        Call<User> createUser(@Body User user);

        @PUT("users/{id}/meals")
        Call<Integer> setMeal(@Path("id") int id, @Body int mealCount);

        @GET("polls/{venueName}/new")
        Call<List<Poll>> getPoll(@Path("venueName") String venueName);
    }

    public void setMeal(int mealCount) {
        if (user != null) {
            service.setMeal(user.getId(), mealCount).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            check();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }
    }

    public User getUser() {
        return user;
    }

    public Poll getCommonsPoll() { return commonsPoll; }

    public Poll getKnollPoll() { return knollPoll; }

    public void check() {
        Log.v("x", "userName: " + username);
        // retrofit does not support sending null
        // this makes it possible to log out
        if (username == null || password == null) {
            user = null;
            setChanged();
            notifyObservers();
            return;
        }
        service.user(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                Log.v("x", "requrl:" + call.request().url());
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        User updatedUser = response.body();
                        Log.v("x", "upsatedUser: " + updatedUser);
                        user = (updatedUser != null && updatedUser.getPassword().equals(password) && updatedUser.getUserName().equals(username)) ? updatedUser : null;

                        setChanged();
                        notifyObservers();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("x", "get User failed failed: " + t + " :oh no.");
            }
        });

        for (final String venueId : new String[]{ "commons", "knollcrest"}) {
            service.getPoll(venueId).enqueue(new Callback<List<Poll>>() {
                @Override
                public void onResponse(Call<List<Poll>> call, final Response<List<Poll>> response) {
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.body().size() < 1) {
                                return; // Don't crash if the server has no new polls
                            }
                            Poll poll = response.body().get(0);
                            if (venueId == "commons") {
                                commonsPoll = poll;
                            } else {
                                knollPoll = poll;
                            }

                            setChanged();
                            notifyObservers();
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Poll>> call, Throwable t) {
                    Log.v("x", "get Poll failed failed: " + t + " :oh no.");
                }
            });
        }
    }

    public void setLogin(String username, String password) {
        this.username = username;
        this.password = password;

        check();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }

    public void createUser(final String username, final String password) {
        service.createUser(new User(username, password, -1, 1)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        setLogin(username, password);
                    }});
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("x", "making User failed failed failed: " + t + " :oh no.");
            }
        });
    }
}
