package se.ja1984.feber.Models;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import se.ja1984.feber.Fragments.ArticleFragment;
import se.ja1984.feber.Fragments.MainFragment;
import se.ja1984.feber.R;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleActivity extends FragmentActivity {
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        String articleUrl = "";
        if (extras != null) {
            articleUrl = extras.getString("ArticleUrl");
        }
        getActionBar().setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ArticleFragment fragment = new ArticleFragment();
        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, fragment,"ARTICLE").commit();
            fragment.loadArticle(articleUrl);
        }

    }
}
