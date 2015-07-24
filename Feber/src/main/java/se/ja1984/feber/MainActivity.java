package se.ja1984.feber;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Activities.FilterActivity;
import se.ja1984.feber.Activities.SettingsActivity;
import se.ja1984.feber.Fragments.MainFragment;
import se.ja1984.feber.Models.DisqusResponse;
import se.ja1984.feber.Models.Response;


public class MainActivity extends AppCompatActivity{
    private Fragment mainFragment;

    public static String hiddenCategories = "";
    private Drawer drawer = null;
    private SharedPreferences _preferences;
    public static boolean refresh;


    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onResume() {
        if(refresh){
            if(mainFragment != null) {
                ((MainFragment) mainFragment).update();
                refresh = false;
            }
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        _preferences = getSharedPreferences("Feber", MODE_PRIVATE);

        hiddenCategories = _preferences.getString("hiddenCategories","");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if(savedInstanceState == null) {
            mainFragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.container, mainFragment,"HOME").commit();
        }

        /*drawer  = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.navdrawer_header)
                .withDrawerItems(getDrawerItems())
                .withSelectedItem(-1)
                .build();*/
    }

    public void resetAdapter(){
        ((MainFragment)mainFragment).resetAdapter();
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_filter:
                startActivity(new Intent(this, FilterActivity.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
