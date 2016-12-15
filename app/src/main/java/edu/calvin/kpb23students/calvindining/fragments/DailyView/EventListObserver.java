package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.database.DataSetObserver;
import android.widget.BaseAdapter;

/**
 * <p>
 * This handles Observers for daily view.
 * <p/>
 *
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public abstract class EventListObserver extends BaseAdapter {
    private int observerCount;

    /**
     * registers a new Observer.
     * @param observer Observer to be registered
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
     * unregisters an Observer
     * @param observer Observer to be unregistered
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
     * Factory method to be overridden
     */
    protected void gainedFirstDataSetObserver() {} // to be overridden

    /**
     * Factory method to be overriden
     */
    protected void lostLastDataSetObserver() {} // to be overridden
}
