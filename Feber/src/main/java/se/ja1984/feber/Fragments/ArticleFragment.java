package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Helpers.CircularImageView;
import se.ja1984.feber.Helpers.Temperature;
import se.ja1984.feber.Helpers.TextDrawable;
import se.ja1984.feber.Helpers.Utils;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.ArticleService;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleFragment extends Fragment {

    @Bind(R.id.toolbar)    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.backdrop)    ImageView image;
    @Bind(R.id.text)    TextView text;
    @Bind(R.id.fab)    FloatingActionButton fab;
    @Bind(R.id.authorImage)    CircularImageView authorImage;
    @Bind(R.id.information) TextView information;



    Activity _activity;
    Article _article;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, rootView);

        ((ArticleActivity)getActivity()).setSupportActionBar(toolbar);
        ((ArticleActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final String youTubeId = ((ArticleActivity) getActivity()).article.getYouTubeId();

        if(!Utils.stringIsNullorEmpty(youTubeId)) {
            //overlay.setVisibility(View.VISIBLE);
//            overlay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youTubeId));
//                    startActivity(intent);
//                }
//            });
        }

        loadArticle(((ArticleActivity) getActivity()).article);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
    }

    public ArticleFragment(){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_article,menu);
        // MenuItem item = menu.findItem(R.id.menu_temperature);

//        if(item != null) {
        //          temperature = (TextView) item.getActionView().findViewById(R.id.txtTemperature);

        //        if (temperature != null) {
        //          String _temperature = _article.getTemperature();
        //        temperature.setText(_temperature);
        //        temperature.setBackground(_activity.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
        //        temperature.setVisibility(View.VISIBLE);
        //     }
        // }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_openInBrowser:
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(_article.getArticleUrl()));
                startActivity(i);
                return true;
            case R.id.menu_share:
                shareArticle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }

    private void shareArticle() {
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide what to do with it.
        intent.putExtra(Intent.EXTRA_SUBJECT, _article.getHeader());
        intent.putExtra(Intent.EXTRA_TEXT, _article.getArticleUrl());
        startActivity(Intent.createChooser(intent, String.format("Dela %s", _article.getHeader())));
    }



    public void loadArticle(Article _article){
        this._article = _article;
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(getBackgroundColor(_article.getTemperatureAsInt()))));
        fab.setImageDrawable(new TextDrawable(_article.getTemperature()));
        collapsingToolbar.setTitle(_article.getHeader());
        //header.setText(_article.getHeader());

        //text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setClickable(false);
        //text.setLinksClickable(false);
        //text.setFocusable(false);

        text.setText(Html.fromHtml(_article.getText()));
        //text.setMovementMethod(LinkMovementMethod.getInstance());

        Glide.with(_activity).load(_article.getAuthor().getImage()).into(authorImage);

        information.setText(_article.getAuthor().getName());

        //new Link().setTextViewHTML(menu_article,_article.getText(),getActivity());

        //published.setText("Publiserad " + _article.getPublished());

        if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
            Glide.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
        }

    }

    private int getBackgroundColor(int temperature) {
        if(temperature >= 39) return R.color.hot;
        if(temperature <= 35) return R.color.cold;

        return R.color.feber;
    }
}
