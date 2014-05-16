package se.ja1984.feber.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.PageService;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleFragment extends Fragment {
    ImageView image;
    TextView article;
    TextView header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        image = (ImageView)rootView.findViewById(R.id.imgArticleMainImage);
        article = (TextView)rootView.findViewById(R.id.txtArticleText);
        header = (TextView)rootView.findViewById(R.id.txtArticleHeader);



        return rootView;
    }

    public void loadArticle(String url){
        new PageService(getActivity(),new TaskCompleted<ArrayList<Article>>() {
            @Override
            public void onTaskComplete(ArrayList<Article> result) {
                Article _article = result.get(0);
                header.setText(_article.getHeader());
                Log.d("Feber html","" + _article.getText());
                article.setText(Html.fromHtml(_article.getText()));
                if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
                    Picasso.with(getActivity()).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
                }
            }
        }).getArticle(url);
    }

}
