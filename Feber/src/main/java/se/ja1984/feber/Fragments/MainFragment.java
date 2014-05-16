package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

import se.ja1984.feber.Helpers.EndlessScrollListener;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleActivity;
import se.ja1984.feber.Models.ArticleAdapter;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.PageService;

/**
 * Created by jonathan on 2014-05-16.
 */
public class MainFragment extends Fragment {
    public ListView _articles;

    ArticleAdapter articlesAdapter;
    private String PAGE_URL = "http://feber.se/?p=%s";



    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        _articles = (ListView) rootView.findViewById(R.id.lstArticles);
        articlesAdapter = new ArticleAdapter(getActivity(), R.layout.listitem_article,new ArrayList<Article>());

        if(_articles != null){
            _articles.setAdapter(articlesAdapter);
            _articles.setOnScrollListener(new EndlessScrollListener(getActivity(),articlesAdapter));
            _articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("ArticleUrl", view.getTag(R.string.KEY_URL).toString());
                    startActivity(intent);
                }
            });
        }

        loadPages();
        //new LoadPageTask().execute();
        return rootView;
    }

    private void loadPages() {
        for(int i = 1;i<3;i++){
            new PageService(getActivity(),new TaskCompleted<ArrayList<Article>>() {
                @Override
                public void onTaskComplete(ArrayList<Article> result) {
                    articlesAdapter.addAll(result);
                };
            }).getArticles(String.format(PAGE_URL,i));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}

