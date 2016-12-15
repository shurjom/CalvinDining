package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.database.DataSetObserver;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Handles Observers
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public abstract class ObservingFragmentPagerAdapter extends FragmentPagerAdapter {
    private int observerCount;

    /**
     *
     * @param fm
     */
    public ObservingFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     *
     * @param observer
     */
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        observerCount++;
        if (observerCount == 1) {
            gainedFirstDataSetObserver();
        }
    }

    /**
     *
     * @param observer
     */
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
        observerCount--;
        if (observerCount == 0) {
            lostLastDataSetObserver();
        } else if (observerCount < 0) {
            throw new RuntimeException("observerCount went below 0");
        }
    }

    /**
     * factory method to be overridden
     */
    protected void gainedFirstDataSetObserver() {} // to be overridden

    /**
     * facotry method to be overridden
     */
    protected void lostLastDataSetObserver() {} // to be overridden
}
