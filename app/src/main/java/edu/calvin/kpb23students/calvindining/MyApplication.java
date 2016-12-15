package edu.calvin.kpb23students.calvindining;

import android.app.Application;
import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * This sets up things that need to be used throughout the whole application.
 * <p>
 *     This sets up the services that are used for connecting to the 2 servers.
 * </p>
 *
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    private Retrofit retrofit;
    private Retrofit retrofitJava;
    private CalvinDiningService calvinDiningService;
    private JavaService javaService;


    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;


        // My server

        String BASE_URL_CALVIN_DINING = "http://sam.ohnopub.net/~kpgbrink/CalvinDiningServer/server.cgi/";

        // http://stackoverflow.com/a/23503804/2948122
        File httpCacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_CALVIN_DINING)
                .client(new OkHttpClient.Builder()
                    .cache(new Cache(httpCacheDirectory, cacheSize))
                    .build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        calvinDiningService = new CalvinDiningService(retrofit);

        // Calvin's server

        String BASE_URL_JAVA_SERVICE = "http://cs262.cs.calvin.edu:8086/Dining/";

        File httpCacheDirectoryJava = new File(getApplicationContext().getCacheDir(), "responses");
        int cacheSizeJava = 10 * 1024 * 1024; // 10 MiB

        retrofitJava = new Retrofit.Builder()
                .baseUrl(BASE_URL_JAVA_SERVICE)
                .client(new OkHttpClient.Builder()
                        .cache(new Cache(httpCacheDirectoryJava, cacheSizeJava))
                        .build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        javaService = new JavaService(retrofitJava, getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE), getApplicationContext());
    }

    /**
     * @return a MyApplication singleton used for accessing the different services that get information from the servers.
     */
    public static MyApplication getMyApplication() {
        return myApplication;
    }

    /**
     * @return a Retrofit used for android's client
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     *
     * @return calvinDiningService that gets information from Calvin's api
     */
    public CalvinDiningService getCalvinDiningService() {
        return calvinDiningService;
    }

    /**
     *
     * @return javaService that is used for logging in for mealCount and voting
     */
    public JavaService getJavaService() { return javaService; }

}