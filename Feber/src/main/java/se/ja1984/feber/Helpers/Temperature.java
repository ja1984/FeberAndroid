package se.ja1984.feber.Helpers;

import se.ja1984.feber.R;

/**
 * Created by Jack on 2014-05-18.
 */
public class Temperature {
    public int setBackgroundBasedOnTemperature(int temperature) {
        if(temperature >= 39) return R.drawable.circle_hot;
        if(temperature <= 35) return R.drawable.circle_cold;

        return R.drawable.circle_feber;
    }
}
