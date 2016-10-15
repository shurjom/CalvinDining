package edu.calvin.kpb23students.calvindining;

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

/**
 * Created by Kristofer on 10/1/2016.
 */

public class TimeLabel extends RelativeLayout {
    private TextView name;
    private TextView beginTime;
    private TextView endTime;

    public TimeLabel(Context context) {
        super(context);
    }

    public TimeLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // http://stackoverflow.com/a/17277714/2948122
    private void setIsCurrent(Boolean isCurrent) {
        // Get theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true);
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
        this.name = (TextView)findViewById(R.id.name);
        this.beginTime = (TextView)findViewById(R.id.beginTime);
        this.endTime = (TextView)findViewById(R.id.endTime);
    }

    public void set(Boolean isCurrent, String name, String beginTime, String endTime) {
        setIsCurrent(isCurrent);
        this.name.setText(name);
        this.beginTime.setText(beginTime);
        this.endTime.setText(endTime);
    }
}
