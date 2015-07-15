package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Helpers.EndlessScrollListener;
import se.ja1984.feber.Helpers.Keys;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.Adapters.ArticleAdapter;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.ArticleService;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.articles) ListView articles;
    @Bind(R.id.progress)    SwipeRefreshLayout progressBar;

    ArticleAdapter articlesAdapter;
    public static int currentPage = 0;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        articlesAdapter = new ArticleAdapter(getActivity(), R.layout.listitem_article_card,new ArrayList<Article>());

        if(articles != null){
            articles.setAdapter(articlesAdapter);
            articles.setOnScrollListener(new EndlessScrollListener(getActivity(),this,articlesAdapter));
            articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("Article", (Article)articles.getAdapter().getItem(position));
                    startActivity(intent);
                }
            });
        }

        loadPages();
        return rootView;
    }

    public void setAsLoading(boolean isLoading){
        progressBar.setRefreshing(isLoading);
    }

    private void loadPages() {
        while (currentPage < 3){
            setAsLoading(true);
            new ArticleService(getActivity(),new TaskCompleted<ArrayList<Article>>() {
                @Override
                public void onTaskComplete(ArrayList<Article> result) {
                    articlesAdapter.addAll(result);
                    articlesAdapter.notifyDataSetChanged();
                    setAsLoading(false);
                };
            }).getArticles(String.format(Keys.SELECTED_PAGE_URL,currentPage, (currentPage * 12)));
            currentPage++;
        }
    }

    public void resetAdapter(){
        articlesAdapter.clear();
        currentPage = 1;
        loadPages();
    }

    public void update(){
        articlesAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}

