package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.app.ActionBar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.calvin.kpb23students.calvindining.R;


public class DailyViewTabber extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_view_tabber, container, false);

        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        // When using fragment nesting, be sure to use the right fragment manager! http://stackoverflow.com/a/13466829/2948122
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));



        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);

        return view;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return new DailyView();
                case 1: return new DailyView();
                case 2: return new DailyView();
                case 3: return new DailyView();
            }
            throw new RuntimeException("index out of range");
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Hello";
        }
    }
}