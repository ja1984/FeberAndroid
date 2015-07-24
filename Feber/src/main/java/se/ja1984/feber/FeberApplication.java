package se.ja1984.feber;

import android.app.Application;

import se.ja1984.feber.Helpers.Analytics;

/**
 * Created by Jack on 2015-07-22.
 */
public class FeberApplication extends Application {
    public static String readArticles = "";

    @Override
    public void onCreate() {

        Analytics.getTracker(this);
        super.onCreate();
    }
}
