package se.ja1984.feber.Activities;

import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Fragments.MainFragment;
import se.ja1984.feber.Helpers.CircularImageView;
import se.ja1984.feber.MainActivity;
import se.ja1984.feber.R;

public class FilterActivity extends AppCompatActivity {

    @Bind(R.id.options)    LinearLayout list;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupFilterOptions();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupFilterOptions() {
        for(int i = 0;i < NAVDRAWER_ICON_RES_ID.length;i++){
            String tag = getResources().getString(NAVDRAWER_TITLE_RES_ID[i]);

            final View view = this.getLayoutInflater().inflate(R.layout.listitem_filteropion, list, false);
            final SwitchCompat switchView = (SwitchCompat)view.findViewById(R.id.switchView);
            switchView.setChecked(!MainActivity.hiddenCategories.contains(tag));
            ImageView icon = (ImageView)view.findViewById(R.id.icon);
            TextView name  = (TextView)view.findViewById(R.id.name);
            RelativeLayout wrapper = (RelativeLayout)view.findViewById(R.id.wrapper);
            view.setTag(tag);

            name.setText(tag);
            icon.setImageDrawable(getResources().getDrawable(NAVDRAWER_ICON_RES_ID[i]));

            wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = !switchView.isChecked();
                    switchView.setChecked(checked);
                }
            });

            switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateHiddenCategories(isChecked, view.getTag().toString());
                }
            });

            list.addView(view);


            //drawerItems.add(new SwitchDrawerItem().withName(NAVDRAWER_TITLE_RES_ID[i]).withIcon(NAVDRAWER_ICON_RES_ID[i]).withChecked(!hiddenCategories.contains(tag)).withOnCheckedChangeListener(this).withTag(tag));
        }
    }

    private void updateHiddenCategories(boolean checked, String tag) {
        if(!checked) {
            MainActivity.hiddenCategories = MainActivity.hiddenCategories + tag;
        }
        else{
            MainActivity.hiddenCategories = MainActivity.hiddenCategories.replace(tag.toString(),"");
        }

        SharedPreferences.Editor editor = getSharedPreferences("Feber", MODE_PRIVATE).edit();
        editor.putString("hiddenCategories", MainActivity.hiddenCategories).apply();

        MainActivity.refresh = true;

    }
}
