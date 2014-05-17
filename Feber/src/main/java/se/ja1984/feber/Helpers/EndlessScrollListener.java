package se.ja1984.feber.Helpers;

import android.app.Activity;
import android.app.Fragment;
import android.widget.AbsListView;
import se.ja1984.feber.Fragments.MainFragment;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleAdapter;
import se.ja1984.feber.Services.PageService;

import java.util.ArrayList;

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
    private Fragment _fragment;

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(Activity activity,Fragment fragment, ArticleAdapter articles) {
        _articles = articles;
        _activity = activity;
        _fragment = fragment;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                ((MainFragment)_fragment).setAsLoading(false);
                previousTotal = totalItemCount;
                MainFragment.currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            loading = true;
            new PageService(_activity,new TaskCompleted<ArrayList<Article>>() {
                @Override
                public void onTaskComplete(ArrayList<Article> result) {
                    _articles.addAll(result);
                };
            }).getArticles(String.format(Keys.SELECTED_PAGE_URL,MainFragment.currentPage));
            ((MainFragment)_fragment).setAsLoading(true);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
