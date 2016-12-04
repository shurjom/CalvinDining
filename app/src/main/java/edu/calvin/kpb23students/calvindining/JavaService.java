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

    private interface InterfaceService {
        @GET("user/{user}")
        Call<User> user(@Path("user") String username);

        @POST("users")
        Call<User> createUser(@Body User user);

        @PUT("users/{id}/meals")
        Call<Integer> setMeal(@Path("id") int id, @Body int mealCount);
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
