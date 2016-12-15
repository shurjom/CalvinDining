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
 * @author Kyle Harkema
 * @version Fall, 2016
 */
public class Help extends Fragment {
    /**
     * Required empty constructor
     */
    public Help() {
        // Required empty public constructor
    }

    /**
     * This ties the layout of fragment_help to this fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}
