package se.ja1984.feber.Services;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.ArticleAdapter;
import se.ja1984.feber.R;

/**
 * Created by jonathan on 2014-05-16.
 */
public class PageService {

    private Context _context;
    private TaskCompleted _taskCompleted;

    public PageService(Context context, TaskCompleted taskComplete){
        _context = context;
        _taskCompleted = taskComplete;
    }


    public void getArticle(String url){
        new LoadArticlesTask().execute(url);
    }
    public void getArticles(String url){
        new LoadArticlesTask().execute(url);
    }

    public ArrayList<Article> convertToArticle(Elements elements){
        ArrayList<Article> articles = new ArrayList<Article>();

        for(org.jsoup.nodes.Element element : elements){
            articles.add(new Article(element));
        }

        return articles;
    }

    public class LoadArticlesTask extends AsyncTask<String,Void,Elements> {
        @Override
        protected void onPostExecute(Elements elements) {
            if(elements == null || elements.size() == 0) return;
            ArrayList<Article> articles = convertToArticle(elements);
            _taskCompleted.onTaskComplete(articles);
        }

        @Override
        protected Elements doInBackground(String... params) {
            Document doc = null;
            Elements articles = null;
            try {
                doc = Jsoup.connect(String.format(params[0])).get();
                articles = doc.select("div[id^=article]");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return articles;
        }
    }

}
