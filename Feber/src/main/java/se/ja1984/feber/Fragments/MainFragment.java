package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.Adapters.ArticleAdapter;
import se.ja1984.feber.Helpers.EndlessScrollListener;
import se.ja1984.feber.Helpers.Keys;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticlesViewModel;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.ArticleService;


/**
 * Created by jonathan on 2014-05-16.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.articles) ListView list;
    @Bind(R.id.progress)    SwipeRefreshLayout progressBar;
    @Bind(R.id.empty)    FrameLayout empty;
    ArrayList<Article> articles = new ArrayList<>();

    ArticleAdapter adapter;
    public static int currentPage = 0;
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new ArticleAdapter(getActivity(), R.layout.listitem_article_card,articles);
        list.setEmptyView(empty);
        Reservoir.getAsync("old", ArticlesViewModel.class, new ReservoirGetCallback<ArticlesViewModel>() {
            @Override
            public void onSuccess(ArticlesViewModel articlesViewModel) {
                articles.addAll(articlesViewModel.Articles);
                adapter.notifyDataSetChanged();

                /*Snackbar.make(list, R.string.cachedcopy_text, Snackbar.LENGTH_LONG).setAction(R.string.loadnew_cache, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadPages();
                    }
                }).show();*/

            }

            @Override
            public void onFailure(Exception e) {
                loadPages();
                e.printStackTrace();
            }
        });

        progressBar.setRefreshing(true);

        progressBar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPages();
            }
        });

        if(list != null){
            list.setAdapter(adapter);
            list.setOnScrollListener(new EndlessScrollListener(getActivity(),this,adapter));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("Article", (Article) list.getAdapter().getItem(position));
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }



    public void setAsLoading(boolean isLoading){
        progressBar.setRefreshing(isLoading);
    }

    private void loadPages() {
        Log.d("loadPages","Loading");

        ArrayList<String> urls = new ArrayList<>();

        for(int i = 0; i <= 2;i++){
            urls.add(String.format(Keys.SELECTED_PAGE_URL,i, (i * 12)));
        }

        setAsLoading(true);
        new ArticleService(getActivity(),new TaskCompleted<ArrayList<Article>>() {
            @Override
            public void onTaskComplete(ArrayList<Article> result) {
                articles.clear();
                articles.addAll(result);

                Reservoir.putAsync("old", new ArticlesViewModel(result), new ReservoirPutCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Put","Success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });

                adapter.notifyDataSetChanged();
                setAsLoading(false);
                currentPage = 2;
            };
        }).getArticles(urls.toArray(new String[0]));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Reservoir.init(getActivity(),20000000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetAdapter(){
        adapter.clear();
        currentPage = 0;
        loadPages();
    }

    public void update(){
        adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}

