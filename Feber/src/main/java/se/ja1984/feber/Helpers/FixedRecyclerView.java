package se.ja1984.feber.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Jack on 2015-07-15.
 */
public class FixedRecyclerView extends RecyclerView {
    public FixedRecyclerView(Context context) {
        super(context);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        // check if scrolling up
        if (direction < 1) {
            boolean original = super.canScrollVertically(direction);
            Log.d("GetTop","" + getChildAt(0).getTop() + " - PaddingTop:" + getPaddingTop() );
            return !original && getChildAt(0) != null && getChildAt(0).getTop() <= getPaddingTop() || original;
        }
        return super.canScrollVertically(direction);

    }
}
