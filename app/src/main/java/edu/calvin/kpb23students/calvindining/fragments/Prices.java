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
 * This fragment shows prices for guests and students
 * <p>
 *      This shows prices for guests, children, and it links user to the different meal plans.
 * </p>
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class Prices extends Fragment {
    /**
     * Required empty constructor
     */
    public Prices() {
        // Required empty public constructor
    }


    /**
     * Sets up links to the buttons at the bottom of the page.
     * <p>
     *     The links link the user to important pages on Calvin's website. It links to meal plans for on-campus and off campus students.
     * </p>
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     * Directs a user to a link given by the app.
     * @param link String of the link that the app will send the user to
     */
    private void openLink(String link) {
        // http://stackoverflow.com/a/4930319/2948122
        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}


