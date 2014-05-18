package se.ja1984.feber.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;

import java.io.IOException;
import java.util.ArrayList;

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
    public void getArticles(String url) {
        Log.d("Page", "" + url);
        new LoadArticlesTask().execute(url);
    }

    public ArrayList<Article> convertToArticle(Elements elements){
        ArrayList<Article> articles = new ArrayList<Article>();

        for(org.jsoup.nodes.Element element : elements){
            articles.add(new Article(element));
        }

        return articles;
    }

    public class LoadArticlesTask extends AsyncTask<String,Void,ArrayList<Article>> {
        @Override
        protected void onPostExecute(ArrayList<Article> articles) {

            _taskCompleted.onTaskComplete(articles);
        }

        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            Document doc = null;
            Elements elements = null;
            ArrayList<Article> articles = new ArrayList<Article>();
            try {
                doc = Jsoup.connect(String.format(params[0])).get();
                elements = doc.select("div[id^=article]");

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(elements == null || elements.size() < 1) return articles;

            try{
                articles.addAll(convertToArticle(elements));
            }
            catch (Exception ex){
                ex.printStackTrace();
                return  articles;
            }

            return articles;


        }
    }

}
