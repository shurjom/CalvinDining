package edu.calvin.kpb23students.calvindining.fragments.DailyView;

import android.content.Context;
import android.content.res.Resources;
import android.media.Rating;
import android.os.Build;
import android.text.Html;
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
 * <p>
 * Handles displaying display items
 * <p/>
 *
 * @author Kristofer Brink
 * @version Fall, 2016
 */
public class TimeLabel extends RelativeLayout {
    private TextView name;
    private TextView beginTime;
    private TextView endTime;
    private TextView description;

    /**
     * makesTimeLabel
     * @param context
     */
    public TimeLabel(Context context) {
        super(context);
    }

    /**
     *makesTimeLabel
     * @param context
     * @param attrs
     */
    public TimeLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *makesTimeLabel
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * makes timeLabel
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public TimeLabel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // http://stackoverflow.com/a/17277714/2948122
    /**
     * changes colors on weather or not it is current.
     * @param isCurrent
     */
    private void setIsCurrent(Boolean isCurrent) {
        // Get theme
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        int color = typedValue.data;
        theme.resolveAttribute(android.R.attr.colorActivatedHighlight, typedValue, true);
        int colorPrimary = typedValue.data;
        // TODO make own color for this http://stackoverflow.com/a/30905173/2948122
        if (isCurrent) { // Highlight block
            setBackgroundColor(0xffeffafa);
        } else { // Dehighlight blockdfd
            setBackgroundColor(colorPrimary);
        }
    }

    /**
     * handles inflation
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.name = (TextView)findViewById(R.id.name);
        this.beginTime = (TextView)findViewById(R.id.beginTime);
        this.endTime = (TextView)findViewById(R.id.endTime);
        this.description = (TextView)findViewById(R.id.description);
    }

    /**
     * set the Label
     * @param isCurrent boolean true if current
     * @param name String name of the Label
     * @param beginTime String of the starting time of the meal
     * @param endTime String of the ending time of the meal
     * @param description String of the description of the meal
     */
    public void set(Boolean isCurrent, String name, String beginTime, String endTime, String description) {
        setIsCurrent(isCurrent);
        this.name.setText(name);
        this.beginTime.setText(beginTime);
        this.endTime.setText(endTime);

        // Use depricated if older version
        if (((int) Build.VERSION.SDK_INT) >= 24) {
            this.description.setText(Html.fromHtml(description, 0));
        } else {
            this.description.setText(Html.fromHtml(description));
        }
    }
}
