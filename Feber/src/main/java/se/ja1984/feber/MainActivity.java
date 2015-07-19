package se.ja1984.feber;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import se.ja1984.feber.Fragments.MainFragment;
import se.ja1984.feber.Models.DisqusResponse;
import se.ja1984.feber.Models.Response;


public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {
    private Fragment mainFragment;

    public static String hiddenCategories = "";
    private Drawer drawer = null;
    private SharedPreferences _preferences;

    private final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.android,
            R.string.car,
            R.string.movie,
            R.string.photo,
            R.string.ios,
            R.string.mac,
            R.string.mobile,
            R.string.pc,
            R.string.stuff,
            R.string.society,
            R.string.game,
            R.string.science,
            R.string.video,
            R.string.web
    };

    private final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_navdrawer_android,
            R.drawable.ic_navdrawer_car,
            R.drawable.ic_navdrawer_movie,
            R.drawable.ic_navdrawer_photo,
            R.drawable.ic_navdrawer_ios,
            R.drawable.ic_navdrawer_mac,
            R.drawable.ic_navdrawer_mobile,
            R.drawable.ic_navdrawer_pc,
            R.drawable.ic_navdrawer_stuff,
            R.drawable.ic_navdrawer_society,
            R.drawable.ic_navdrawer_games,
            R.drawable.ic_navdrawer_science,
            R.drawable.ic_navdrawer_video,
            R.drawable.ic_navdrawer_web,
    };

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        _preferences = getSharedPreferences("Feber",MODE_PRIVATE);

        hiddenCategories = _preferences.getString("hiddenCategories","");
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            mainFragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.container, mainFragment,"HOME").commit();
        }

        drawer  = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.navdrawer_header)
                .withDrawerItems(getDrawerItems())
                .withSelectedItem(-1)
                .build();
    }

    public void resetAdapter(){
        ((MainFragment)mainFragment).resetAdapter();
    };

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean checked) {
        if(!checked) {
            hiddenCategories = hiddenCategories + iDrawerItem.getTag().toString();
        }
        else{
           hiddenCategories = hiddenCategories.replace(iDrawerItem.getTag().toString(),"");
        }

        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("hiddenCategories",hiddenCategories).apply();

        ((MainFragment) mainFragment).update();
    }

    private ArrayList<IDrawerItem> getDrawerItems() {
        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();

        for(int i = 0;i < NAVDRAWER_ICON_RES_ID.length;i++){
            String tag = getResources().getString(NAVDRAWER_TITLE_RES_ID[i]);
            drawerItems.add(new SwitchDrawerItem().withName(NAVDRAWER_TITLE_RES_ID[i]).withIcon(NAVDRAWER_ICON_RES_ID[i]).withChecked(!hiddenCategories.contains(tag)).withOnCheckedChangeListener(this).withTag(tag));
        }

        return drawerItems;
    }
}
