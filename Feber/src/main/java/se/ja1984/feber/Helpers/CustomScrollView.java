package se.ja1984.feber.Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import se.ja1984.feber.Interface.OnScrollViewListener;

/**
 * Created by Jack on 2014-05-24.
 */
public class CustomScrollView extends ScrollView
{
    private OnScrollViewListener _mOnScrollViewListener;
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        _mOnScrollViewListener = onScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
