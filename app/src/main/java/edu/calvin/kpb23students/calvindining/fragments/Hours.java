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
 * This fragment shows hours
 *
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class Hours extends Fragment {
    /**
     * Required empty public constructor
     */
    public Hours() {
        // Required empty public constructor
    }

    /**
     * This ties the layout of fragment_hours to this fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hours, container, false);
    }
}
