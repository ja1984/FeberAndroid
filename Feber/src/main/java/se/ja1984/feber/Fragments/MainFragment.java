package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import se.ja1984.feber.Helpers.EndlessScrollListener;
import se.ja1984.feber.Helpers.Keys;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleActivity;
import se.ja1984.feber.Models.ArticleAdapter;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.PageService;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class MainFragment extends Fragment {
    public ListView _articles;

    ArticleAdapter articlesAdapter;
    LinearLayout loading;
    public static int currentPage = 1;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        _articles = (ListView) rootView.findViewById(R.id.lstArticles);
        loading = (LinearLayout)rootView.findViewById(R.id.lnrLoadingArticles);
        articlesAdapter = new ArticleAdapter(getActivity(), R.layout.listitem_article,new ArrayList<Article>());


        if(_articles != null){
            _articles.setAdapter(articlesAdapter);
            _articles.setOnScrollListener(new EndlessScrollListener(getActivity(),this,articlesAdapter));
            _articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("Article", (Article)_articles.getAdapter().getItem(position));
                    startActivity(intent);
                }
            });
        }

        loadPages();
        //new LoadPageTask().execute();
        return rootView;
    }

    public void setAsLoading(boolean isLoading){
        if(isLoading){
            Animation bottomUp = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
            loading.startAnimation(bottomUp);
            loading.setVisibility(View.VISIBLE);
        }else{
            Animation bottomDown = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
            loading.startAnimation(bottomDown);
            loading.setVisibility(View.GONE);
        }

    }

    public void resetAdapter(){
        articlesAdapter.clear();
        articlesAdapter.notifyDataSetChanged();
    }


    private void loadPages() {
        while (currentPage < 3){
            setAsLoading(true);
            new PageService(getActivity(),new TaskCompleted<ArrayList<Article>>() {
                @Override
                public void onTaskComplete(ArrayList<Article> result) {
                    articlesAdapter.addAll(result);
                    setAsLoading(false);
                };
            }).getArticles(String.format(Keys.SELECTED_PAGE_URL,currentPage));
            currentPage++;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}

