package edu.calvin.kpb23students.calvindining.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.media.Rating;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.calvin.kpb23students.calvindining.R;

/**
 * Created by Kristofer on 2016-10-15.
 */

public class TimeLabelBetween extends RelativeLayout{
    private TextView duration;

    public TimeLabelBetween(Context context) {
        super(context);
    }

    public TimeLabelBetween(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLabelBetween(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeLabelBetween(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // http://stackoverflow.com/a/17277714/2948122
    private void setIsCurrent(Boolean isCurrent) {
        // Get theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
        int color = typedValue.data;

        // TODO make own color for this http://stackoverflow.com/a/30905173/2948122
        if (isCurrent) { // Highlight block
            setBackgroundColor(0xff24ff12);
        } else { // Dehighlight block
            setBackgroundColor(color);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.duration = (TextView)findViewById(R.id.duration);
    }

    public void set(Boolean isCurrent, String duration) {
        setIsCurrent(isCurrent);
        this.duration.setText(duration);
    }
}

