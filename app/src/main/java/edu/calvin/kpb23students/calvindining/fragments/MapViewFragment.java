package edu.calvin.kpb23students.calvindining.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import edu.calvin.kpb23students.calvindining.R;


/**
 ** This uses google maps to display locations of food places at Calvin
 * <p>
 *     This uses google maps and an Icon generator to display locations from the google map view without having to click on the locations.
 * </p>
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class MapViewFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    MapView mMapView;
    private GoogleMap googleMap;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    /**
     * when the view is created this makes google maps work
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);


        // Perform any camera updates here
        return v;
    }

    /**
     * handles when resumed
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * handles on pause
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * handles on destroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * handles on low memory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /**
     * When the map is ready this displays the locations and moves the map to look at Calvin college.
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {

        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Log.v("x", e.getMessage());
        }
        Log.v("x", "LOCATION");
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.930962, -85.587413), 16));

        makeMarker(map, "Johnny's", new LatLng(42.930962, -85.587413));
        makeMarker(map, "Fish House", new LatLng(42.931116, -85.587272));
        makeMarker(map, "Commons", new LatLng(42.931302, -85.587479));
        makeMarker(map, "Spoelhof Cafe", new LatLng(42.930131, -85.589509));
        makeMarker(map, "Knollcrest", new LatLng(42.933169, -85.586144));
        makeMarker(map, "Devos Grab n Go", new LatLng(42.930051, -85.583731));
        makeMarker(map, "Knight Way Cafe", new LatLng(42.933223, -85.589551));
    }

    /**
     * makes the markers
     * @param map GoogleMap to make the marker on
     * @param title String of the title of the marker
     * @param position LatLng position to place the marker
     */
    private void makeMarker(GoogleMap map, String title, LatLng position) {
        TextView text = new TextView(getContext());
        text.setTextColor(Color.parseColor(("#ffffff")));
        text.setText(title);
        IconGenerator generator = new IconGenerator(getContext());
        generator.setBackground(getContext().getDrawable(R.drawable.bubble_mask));
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        MarkerOptions tp = new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromBitmap(icon));
        map.addMarker(tp);
    }

    /**
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    /**
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

    }
}