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
 * This is used to get information from the server that handles login, mealCount, and polls.
 *
 * @author Kristofer Brink
 * @version Fall, 2016
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

    /**
     * The user class is used by retrofit in order to make a user from the information given by the server
     */
    public static class User {
        private String userName;
        private String password;
        private int mealCount;
        private int id;

        /**
         * empty User constructor
         */
        public User() {

        }

        /**
         *
         * @param userName name of the user
         * @param password password of the user
         * @param mealCount how many meals the user has left
         * @param id the id of the user. This is used for polls and mealCounts.
         */
        public User(String userName, String password, int mealCount, int id) {
            this.userName = userName;
            this.password = password;
            this.mealCount = mealCount;
            this.id = id;
        }

        /**
         *
         * @return String name of the user
         */
        public String getUserName() {
            return userName;
        }

        /**
         *
         * @return String password of the user
         */
        public String getPassword() {
            return password;
        }

        /**
         *
         * @return int the amount of meals the user has
         */
        public int getMealCount() {
            return mealCount;
        }

        /**
         *
         * @return int id of the user
         */
        public int getId() {
            return id;
        }

        /**
         * This is for updating the meal count
         * @param mealCount the new mealCount integer
         */
        public void setMealCount(int mealCount) {
            this.mealCount = mealCount;
        }
    }

    /**
     * This poll class is used for retrofit to make new polls
     */
    public static class Poll {
        private int id = 0;
        private String question = "";
        private String questionType = "";
        private String option1 = "";
        private String option2 = "";
        private String option3 = "";
        private String option4 = "";

        /**
         * empty class constructor
         */
        public Poll(){}

        /**
         * Make a new Poll
         * @param id of the new user
         * @param question String of the question the user is asking
         * @param option1 boolean true if user selected
         * @param option2 boolean true if user selected
         * @param option3 boolean true if user selected
         * @param option4 boolean true if user selected
         */
        public Poll(int id, String question, String option1, String option2, String option3, String option4) {
            this.id = id;
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
        }

        /**
         *
         * @return int id of the user
         */
        public int getId() { return id; }

        /**
         *
         * @return question of the user
         */
        public String getQuestion() { return question; }

        /**
         *
         * @return String of option 1
         */
        public String getOption1() { return option1; }

        /**
         *
         * @return String of option 2
         */
        public String getOption2() { return option2; }

        /**
         *
         * @return String of option 3
         */
        public String getOption3() { return option3; }

        /**
         *
         * @return String of option 4
         */
        public String getOption4() { return option4; }

        /**
         *
         * @param value String of option1
         */
        public void setOption1(String value) { option1 = value; }

        /**
         *
         * @param value String of option2
         */
        public void setOption2(String value) { option2 = value; }

        /**
         *
         * @param value String of option3
         */
        public void setOption3(String value) { option3 = value; }

        /**
         *
         * @param value String of option4
         */
        public void setOption4(String value) { option4 = value; }
    }

    /**
     * This PollResponse is used by retrofit to send a poll response to the server
     */
    private static class PollResponse {
        private int pollID, personID;
        private boolean answer1, answer2, answer3, answer4;

        public PollResponse(int pollID, int personID, boolean answer1, boolean answer2, boolean answer3, boolean answer4) {
            this.pollID = pollID;
            this.personID = personID;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.answer4 = answer4;
        }
    }

    /**
     * Retrofit interface for communicating with the server.
     */
    private interface InterfaceService {
        @GET("user/{user}")
        Call<User> user(@Path("user") String username);

        @POST("users")
        Call<User> createUser(@Body User user);

        @PUT("users/{id}/meals")
        Call<Integer> setMeal(@Path("id") int id, @Body int mealCount);

        @GET("polls/{venueName}/new")
        Call<List<Poll>> getPoll(@Path("venueName") String venueName);

        @GET("responses/{id}/stats")
        Call<double[]> getResponsesStats(@Path("id") int pollId);

        @POST("responses")
        Call<Void> postPoll(@Body PollResponse response);
    }

    /**
     * Sends a put request to the server to update the mealCount
     * @param mealCount integer that shows how many meals a user has left
     */
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

    /**
     *
     * @return User the user that the server returns.
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return Poll
     */
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

                            if (response.body().size() < 1) {
                                return; // Don't crash if the server has no new polls
                            }

                            final Poll poll = response.body().get(0);
                            service.getResponsesStats(poll.id).enqueue(new Callback<double[]>() {
                                @Override
                                public void onResponse(Call<double[]> call, Response<double[]> response) {
                                    double[] stats = response.body();
                                    if (stats == null) {
                                        stats = new double[]{0,0,0,0};
                                    }
                                    poll.setOption1(poll.getOption1() + " " + ((int)stats[0]) + "%");
                                    poll.setOption2(poll.getOption2() + " " + ((int)stats[1]) + "%");
                                    poll.setOption3(poll.getOption3() + " " + ((int)stats[2]) + "%");
                                    poll.setOption4(poll.getOption4() + " " + ((int)stats[3]) + "%");

                                    new Handler(context.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
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
                                public void onFailure(Call<double[]> call, Throwable t) {
                                    Log.v("x", "get Poll failed failed: " + t + " :oh no.");
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

    /**
     * Stores the username and password into the preferences so other parts of the app can use the logged in user
     * @param username username of the user that is logged in
     * @param password password of the user that is logged in
     */
    public void setLogin(String username, String password) {
        this.username = username;
        this.password = password;

        check();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }

    /**
     * Makes a new user if the user presses sign in and the username has not already been used.
     * @param username username of the new user
     * @param password password of the new user
     */
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

    /**
     * This sends poll responses and runs check to update the percentages displayed on the vote page.
     * @param poll Poll that the user is submitting to
     * @param answer1 boolean for option 1
     * @param answer2 boolean for option 2
     * @param answer3 boolean for option 3
     * @param answer4 boolean for option 4
     */
    public void submitPollResponse(Poll poll, boolean answer1, boolean answer2, boolean answer3, boolean answer4) {
        PollResponse myResponse = new PollResponse(poll.getId(), user.getId(), answer1, answer2, answer3, answer4);
        service.postPoll(myResponse).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        check();
                    }});
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("x", "posting poll failed failed failed: " + t + " :oh no.");
            }
        });
    }
}
