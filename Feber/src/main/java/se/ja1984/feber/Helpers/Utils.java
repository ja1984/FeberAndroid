package se.ja1984.feber.Helpers;

/**
 * Created by Jack on 2014-05-18.
 */
public class Utils {

    private Utils(){};

    public static boolean stringIsNullorEmpty(String value){
        if(value == null) return true;
        if(value != null && value.isEmpty()) return true;

        return false;
    }
}
