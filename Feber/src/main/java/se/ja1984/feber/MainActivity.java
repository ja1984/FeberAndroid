package se.ja1984.feber;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Fragments.MainFragment;


public class MainActivity extends AppCompatActivity implements OnCheckedChangeListener {
    private Fragment mainFragment;

    public static String hiddenCategories = "";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.navdrawer_header)
                .addDrawerItems(
                        new SwitchDrawerItem().withName(R.string.android).withIcon(R.drawable.ic_navdrawer_android).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.android)),
                        new SwitchDrawerItem().withName(R.string.car).withIcon(R.drawable.ic_navdrawer_car).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.car)),
                        new SwitchDrawerItem().withName(R.string.movie).withIcon(R.drawable.ic_navdrawer_movie).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.movie)),
                        new SwitchDrawerItem().withName(R.string.photo).withIcon(R.drawable.ic_navdrawer_photo).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.photo)),
                        new SwitchDrawerItem().withName(R.string.ios).withIcon(R.drawable.ic_navdrawer_ios).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.ios)),
                        new SwitchDrawerItem().withName(R.string.mac).withIcon(R.drawable.ic_navdrawer_mac).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.mac)),
                        new SwitchDrawerItem().withName(R.string.mobile).withIcon(R.drawable.ic_navdrawer_mobile).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.mobile)),
                        new SwitchDrawerItem().withName(R.string.pc).withIcon(R.drawable.ic_navdrawer_pc).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.pc)),
                        new SwitchDrawerItem().withName(R.string.stuff).withIcon(R.drawable.ic_navdrawer_stuff).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.stuff)),
                        new SwitchDrawerItem().withName(R.string.society).withIcon(R.drawable.ic_navdrawer_society).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.society)),
                        new SwitchDrawerItem().withName(R.string.game).withIcon(R.drawable.ic_navdrawer_games).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.game)),
                        new SwitchDrawerItem().withName(R.string.science).withIcon(R.drawable.ic_navdrawer_science).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.science)),
                        new SwitchDrawerItem().withName(R.string.video).withIcon(R.drawable.ic_navdrawer_video).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.video)),
                        new SwitchDrawerItem().withName(R.string.web).withIcon(R.drawable.ic_navdrawer_web).withOnCheckedChangeListener(this).withChecked(true).withTag(getResources().getString(R.string.web))
                )
                .withSelectedItem(-1)
                .build();

        if(savedInstanceState == null) {
            mainFragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.container, mainFragment,"HOME").commit();
        }
    }

    public void resetAdapter(){
        ((MainFragment)mainFragment).resetAdapter();
    };

    @Override
    public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean checked) {
        if(!checked) {
            hiddenCategories = hiddenCategories + iDrawerItem.getTag().toString();
        }


        Log.d("Hidden Categories","" + hiddenCategories);

        ((MainFragment)mainFragment).update();
    }

}
