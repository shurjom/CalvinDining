package edu.calvin.kpb23students.calvindining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Added stuffs
        Log.v("X", "hi there !");

        // Make EventList
        EventListAdapter timesEventAdapter = new EventListAdapter(getApplicationContext(), getLayoutInflater());
        ((ListView)findViewById(R.id.times)).setAdapter(timesEventAdapter);
        // Test Events
        timesEventAdapter.setEvents(new EventListAdapter.Event[]{
                new EventListAdapter.Event("Beginning of Day", new GregorianCalendar(2016, 10, 1, 0, 1), new GregorianCalendar(2016, 10, 1, 1, 0)),
                new EventListAdapter.Event("Breakfast cool", new GregorianCalendar(2016, 10, 1, 6, 0), new GregorianCalendar(2016, 10, 1, 7, 0)),
                new EventListAdapter.Event("2nd Breakfast", new GregorianCalendar(2016, 10, 1, 8, 0), new GregorianCalendar(2016, 10, 1, 9, 0)),
                new EventListAdapter.Event("Lunch", new GregorianCalendar(2016, 10, 1, 11, 23), new GregorianCalendar(2016, 10, 1, 12, 0)),
                new EventListAdapter.Event("Dinner", new GregorianCalendar(2016, 10, 1, 17, 0), new GregorianCalendar(2016, 10, 1, 18, 0)),
                new EventListAdapter.Event("BQV", new GregorianCalendar(2016, 10, 1, 20, 0), new GregorianCalendar(2016, 10, 1, 21, 0)),
                new EventListAdapter.Event("End of day", new GregorianCalendar(2016, 10, 1, 22, 10), new GregorianCalendar(2016, 10, 1, 23, 0)),
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
