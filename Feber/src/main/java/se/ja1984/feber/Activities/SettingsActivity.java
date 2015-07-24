package se.ja1984.feber.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import se.ja1984.feber.Fragments.SettingsFragment;
import se.ja1984.feber.R;

public class SettingsActivity extends AppCompatActivity {

    private Activity _activity;

    public void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.BaseThemeSettings);
        super.onCreate(savedInstanceState);
        _activity = this;
        setContentView(R.layout.layout_settings);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
