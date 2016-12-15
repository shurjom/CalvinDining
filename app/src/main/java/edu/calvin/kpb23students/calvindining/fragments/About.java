package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.calvin.kpb23students.calvindining.R;


/**
 * <p>
 *     This fragment shows the about page
 * </p>
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class About extends Fragment {
    /**
     * required empty constructor
     */
    public About() {
        // Required empty public constructor
    }

    /**
     * This ties the layout of fragment_about to this fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
