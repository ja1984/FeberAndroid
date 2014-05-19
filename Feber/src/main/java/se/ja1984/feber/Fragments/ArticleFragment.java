package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import se.ja1984.feber.Helpers.Temperature;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleActivity;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.PageService;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleFragment extends Fragment {
    ImageView image;
    TextView text;
    TextView header;
    TextView temperature;
    TextView published;
    Activity _activity;
    Article article;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
    }

    public ArticleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        image = (ImageView)rootView.findViewById(R.id.imgArticleMainImage);
        text = (TextView)rootView.findViewById(R.id.txtArticleText);
        header = (TextView)rootView.findViewById(R.id.txtArticleHeader);
        temperature = (TextView)rootView.findViewById(R.id.txtTemperature);
        published = (TextView)rootView.findViewById(R.id.txtArticlePublished);

        loadArticle(((ArticleActivity) getActivity()).article);

        return rootView;
    }

    public void loadArticle(Article _article){

        header.setText(_article.getHeader());
        //text.setLinksClickable(true);
        //text.setAutoLinkMask(Linkify.ALL);
        text.setText(Html.fromHtml(_article.getText()));
        //new Link().setTextViewHTML(article,_article.getText(),getActivity());

        published.setText("Publiserad " + _article.getPublished());

        String _temperature = _article.getTemperature();
        temperature.setText(_temperature);
        temperature.setBackground(_activity.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
        temperature.setVisibility(View.VISIBLE);

        if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
            Picasso.with(_activity).setDebugging(true);
            Picasso.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
        }

    }

    public void loadArticle(String url, Activity activity){
        new PageService(_activity,new TaskCompleted<ArrayList<Article>>() {
            @Override
            public void onTaskComplete(ArrayList<Article> result) {
                Article _article = result.get(0);

                Log.d("Feber - image","" + _article.getImageUrl());

                if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
                    Picasso.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
                }
                header.setText(_article.getHeader());
                text.setLinksClickable(true);
                text.setAutoLinkMask(Linkify.ALL);
                text.setText(Html.fromHtml(_article.getText()));
                //new Link().setTextViewHTML(article,_article.getText(),getActivity());

                published.setText("Publiserad " + _article.getPublished());

                String _temperature = _article.getTemperature();
                temperature.setText(_temperature);
                temperature.setBackground(_activity.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
                temperature.setVisibility(View.VISIBLE);

            }
        }).getArticle(url);
    }

}
