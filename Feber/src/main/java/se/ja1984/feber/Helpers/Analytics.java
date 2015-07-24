package se.ja1984.feber.Helpers;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;

import se.ja1984.feber.R;

/**
 * Created by Jack on 2015-01-27.
 */
public class Analytics {

    private static Tracker sTracker;

    /**
     * Get the global {@link com.google.android.gms.analytics.Tracker} instance.
     */
    public static synchronized Tracker getTracker(Context context) {
        if (sTracker == null) {
            sTracker = GoogleAnalytics.getInstance(context.getApplicationContext())
                    .newTracker(R.xml.analytics);
        }
        return sTracker;
    }
    public static void trackView(Context context, String screenName) {
        Tracker tracker = getTracker(context);
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    /**
     * Track a custom event that does not fit the {@link #trackAction(android.content.Context,
     * String, String)}, {@link #trackContextMenu(android.content.Context, String, String)} or
     * {@link #trackClick(android.content.Context, String, String)} trackers. Commonly important
     * status information.
     */
    public static void trackCustomEvent(Context context, String tag, String action, String label) {
        getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    /**
     * Track an action event, e.g. when an action item is clicked.
     */
    public static void trackAction(Context context, String tag, String label) {
        getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction("Action Item")
                .setLabel(label)
                .build());
    }

    /**
     * Track a context menu event, e.g. when a context item is clicked.
     */
    public static void trackContextMenu(Context context, String tag, String label) {
        getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(tag)
                .setAction("Context Item")
                .setLabel(label)
                .build());
    }

    /**
     * Track a generic click that does not fit {@link #trackAction(android.content.Context, String,
     * String)} or {@link #trackContextMenu(android.content.Context, String, String)}.
     */
    public static void trackClick(Context context, String category, String label) {
        trackEvent(context,category,"Click",label);
    }

    public static void trackEvent(Context context, String category, String action, String label) {
        getTracker(context).send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public static void trackTiming(Context context, String category, Long value, String name, String label){
        getTracker(context).send(new HitBuilders.TimingBuilder()
                .setCategory(category)
                .setValue(value)
                .setLabel(label)
                .setVariable(name)
                .build());
    }

    public static void trackPurchase(Context context, String productName){
        getTracker(context).send(new HitBuilders.ScreenViewBuilder()
                .setProductAction(new ProductAction(ProductAction.ACTION_PURCHASE)).addProduct(new Product().setName(productName))
                .build());

    }

}
