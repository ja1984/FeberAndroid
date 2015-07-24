package se.ja1984.feber.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import se.ja1984.feber.Fragments.ArticleFragment;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.R;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleActivity extends AppCompatActivity {

    public Article article;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hideActionBar();
        Bundle extras = getIntent().getExtras();
        article = new Article();
        if (extras != null) {
            article = extras.getParcelable("Article");
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        ArticleFragment fragment = new ArticleFragment();

        fragment.setArguments(getIntent().getExtras());
        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, fragment,"ARTICLE").commit();
        }

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

}
