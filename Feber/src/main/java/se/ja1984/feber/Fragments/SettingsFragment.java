package se.ja1984.feber.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirDeleteCallback;
import com.github.machinarius.preferencefragment.PreferenceFragment;

import se.ja1984.feber.FeberApplication;
import se.ja1984.feber.MainActivity;
import se.ja1984.feber.R;

/**
 * Created by Jack on 2015-07-23.
 */
public class SettingsFragment extends PreferenceFragment {

    private Context _context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        _context = getActivity();

        addPreferencesFromResource(R.xml.pref_general);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if(preference.getKey().equals("pref_cleararticles")){
            Reservoir.deleteAsync("old", new ReservoirDeleteCallback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if(preference.getKey().equals("pref_clearread")){
            FeberApplication.readArticles = "";

        }
        MainActivity.refresh = true;


        return super.onPreferenceTreeClick(preferenceScreen, preference);

    }

}
