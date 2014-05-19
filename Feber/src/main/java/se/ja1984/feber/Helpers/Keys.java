package se.ja1984.feber.Helpers;

/**
 * Created by Jack on 2014-05-17.
 */
public class Keys {

    private Keys(){};

    public static String SELECTED_PAGE_URL = "http://feber.se/?p=%s";

    public static String DEFAULT_PAGE_URL = "http://feber.se/?p=%s";
    public static String ANDROID_PAGE_URL = "http://feber.se/android/?p=%s";
    public static String CAR_PAGE_URL = "http://feber.se/bil/?p=%s";
    public static String MOVIE_PAGE_URL = "http://feber.se/film/?p=%s";
    public static String PHOTO_PAGE_URL = "http://feber.se/foto/?p=%s";
    public static String IOS_PAGE_URL = "http://feber.se/ios/?p=%s";
    public static String MAC_PAGE_URL = "http://feber.se/mac/?p=%s";
    public static String MOBILE_PAGE_URL = "http://feber.se/mobil/?p=%s";
    public static String PC_PAGE_URL = "http://feber.se/pc/?p=%s";
    public static String GADGET_PAGE_URL = "http://feber.se/pryl/?p=%s";
    public static String GAMES_PAGE_URL = "http://feber.se/spel/?p=%s";
    public static String SCIENCE_PAGE_URL = "http://feber.se/vetenskap/?p=%s";
    public static String VIDEO_PAGE_URL = "http://feber.se/video/?p=%s";
    public static String WEBB_PAGE_URL = "http://feber.se/webb/?p=%s";
    public static String YOUTUBE_URL = "http://img.youtube.com/vi/%s/maxresdefault.jpg";


    public static void setDefaultUrl(String defaultUrl) {
        SELECTED_PAGE_URL = defaultUrl;
    }
}
