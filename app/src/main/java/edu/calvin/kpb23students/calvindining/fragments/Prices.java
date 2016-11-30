package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
 *     This fragment shows prices for guests and students
 * </p>
 */
public class Prices extends Fragment {
    public Prices() {
        // Required empty public constructor
    }
    // Required empty public constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_prices, container, false);

        // onCampus
        Button onCampus = (Button) linearLayout.findViewById(R.id.onCampus);
        onCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openLink("https://calvin.edu/offices-services/dining-services/meal-plans/standard.html");
            }
        });

        // ofCampus
        Button offCampus = (Button) linearLayout.findViewById(R.id.offCampus);
        offCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://calvin.edu/offices-services/dining-services/meal-plans/community-dining/");
            }
        });

        // Inflate the layout for this fragment
        return linearLayout;
    }

    private void openLink(String link) {
        // http://stackoverflow.com/a/4930319/2948122
        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}


