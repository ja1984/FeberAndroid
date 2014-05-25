package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import se.ja1984.feber.Helpers.Temperature;
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
    Activity _activity;

    @InjectView(R.id.imgArticleMainImage) ImageView image;
    @InjectView(R.id.txtArticleText) TextView text;
    @InjectView(R.id.txtArticleHeader) TextView header;
    @InjectView(R.id.txtArticlePublished) TextView published;
    @InjectView(R.id.lnrOverlay) LinearLayout overlay;
    TextView temperature;
    Article _article;

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
        MenuItem item = menu.findItem(R.id.menu_temperature);

        if(item != null) {
            temperature = (TextView) item.getActionView().findViewById(R.id.txtTemperature);

            if (temperature != null) {
                String _temperature = _article.getTemperature();
                temperature.setText(_temperature);
                temperature.setBackground(_activity.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
                temperature.setVisibility(View.VISIBLE);
            }
        }
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
        startActivity(Intent.createChooser(intent, String.format("Dela %s",_article.getHeader())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.inject(this, rootView);

        final String youTubeId = ((ArticleActivity) getActivity()).article.getYouTubeId();

        if(!Utils.stringIsNullorEmpty(youTubeId)) {
            overlay.setVisibility(View.VISIBLE);
            overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youTubeId));
                    startActivity(intent);
                }
            });
        }

        loadArticle(((ArticleActivity) getActivity()).article);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    public void loadArticle(Article _article){
        this._article = _article;
        header.setText(_article.getHeader());
        //text.setLinksClickable(true);
        text.setAutoLinkMask(Linkify.WEB_URLS);
        text.setText(Html.fromHtml(_article.getText()));
        //new Link().setTextViewHTML(menu_article,_article.getText(),getActivity());

        published.setText("Publiserad " + _article.getPublished());

        if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
            Picasso.with(_activity).setDebugging(true);
            Picasso.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
        }

    }

    public void loadArticle(String url, Activity activity){
        new ArticleService(_activity,new TaskCompleted<ArrayList<Article>>() {
            @Override
            public void onTaskComplete(ArrayList<Article> result) {
                Article _article = result.get(0);

                Log.d("Feber - image", "" + _article.getImageUrl());

                if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
                    Picasso.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
                }
                header.setText(_article.getHeader());
                text.setLinksClickable(true);
                text.setAutoLinkMask(Linkify.ALL);
                text.setText(Html.fromHtml(_article.getText()));
                //new Link().setTextViewHTML(menu_article,_article.getText(),getActivity());

                published.setText("Publiserad " + _article.getPublished());

                String _temperature = _article.getTemperature();
                temperature.setText(_temperature);
                temperature.setBackground(_activity.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
                temperature.setVisibility(View.VISIBLE);

            }
        }).getArticle(url);
    }

}
