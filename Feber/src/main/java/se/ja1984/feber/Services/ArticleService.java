package se.ja1984.feber.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.ja1984.feber.Helpers.Keys;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.DisqusResponse;
import se.ja1984.feber.Models.Post;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleService {

    private Context _context;
    private TaskCompleted _taskCompleted;

    public ArticleService(Context context, TaskCompleted taskComplete){
        _context = context;
        _taskCompleted = taskComplete;
    }

    public void getArticles(String url) {
        new LoadArticlesTask(new String[]{url}).execute(url);
    }

    public void getArticles(String[] urls) {
        new LoadArticlesTask(urls).execute();
    }

    public ArrayList<Article> convertToArticle(Elements elements){
        ArrayList<Article> articles = new ArrayList<Article>();

        for(org.jsoup.nodes.Element element : elements){
            articles.add(new Article(element));
        }

        return articles;
    }

    public void loadComments(String id){
        new LoadCommentsTask(id).execute();
    }

    public class LoadArticlesTask extends AsyncTask<String,Void,ArrayList<Article>> {

        private String[] _urls;

        public LoadArticlesTask(String[] urls){
            _urls = urls;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {

            _taskCompleted.onTaskComplete(articles);
        }

        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            Document doc = null;
            Elements elements = null;
            ArrayList<Article> articles = new ArrayList<Article>();

            for(String url : _urls) {

                try {
                    doc = Jsoup.connect(url).get();
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
            }
            return articles;


        }
    }


    private class LoadCommentsTask extends AsyncTask<String, Void, List<Post>>{

        private String _id;

        public LoadCommentsTask(String id){
            _id = id;
        }

        @Override
        protected List<Post> doInBackground(String... params) {
            String url = String.format(Keys.COMMENTS_URL,_id);

            Document doc = null;
            Elements elements = null;
            Element element = null;

            try {
                doc = Jsoup.connect(url).get();
                element = doc.select("#disqus-threadData").first();
                String test = element.toString().replace("<script type=\"text/json\" id=\"disqus-threadData\">","").replace("</script>","");

                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<DisqusResponse> adapter = moshi.adapter(DisqusResponse.class);
                try{
                    DisqusResponse res = adapter.fromJson(test);
                    return res.response.posts;
                }
                catch (JsonDataException e){
                    e.printStackTrace();
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            _taskCompleted.onTaskComplete(posts);
        }
    }

}
