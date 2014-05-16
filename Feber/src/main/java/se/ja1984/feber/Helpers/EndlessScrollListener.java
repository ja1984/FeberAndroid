package se.ja1984.feber.Helpers;

import android.app.Activity;
import android.widget.AbsListView;

import java.util.ArrayList;

import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleAdapter;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.PageService;

/**
 * Created by jonathan on 2014-05-16.
 */
public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 3;
    private int currentPage = 3;
    private int previousTotal = 0;
    private boolean loading = true;
    private ArticleAdapter _articles;
    private Activity _activity;
    private String PAGE_URL = "http://feber.se/?p=%s";

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(Activity activity, ArticleAdapter articles) {
        _articles = articles;
        _activity = activity;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            new PageService(_activity,new TaskCompleted<ArrayList<Article>>() {
                @Override
                public void onTaskComplete(ArrayList<Article> result) {
                    _articles.addAll(result);
                };
            }).getArticles(String.format(PAGE_URL,currentPage));
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
