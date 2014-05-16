package se.ja1984.feber.Models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.ja1984.feber.R;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private final Context context;
    private final ArrayList<Article> articles;
    int resource;

    static class viewHolder{
        String url;
        String iamge;
        TextView header;
        TextView preamble;
    }

    public ArticleAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        this.context = context;
        this.articles = articles;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder holder;
        Article article = articles.get(position);

        if(convertView == null)
        {
            convertView = View.inflate(context, resource, null);
            holder = new viewHolder();
            holder.header = (TextView)convertView.findViewById(R.id.txtArticleHeader);
            holder.preamble = (TextView)convertView.findViewById(R.id.txtArticlePreamble);
            convertView.setTag(holder);
        }
        else
        {
            holder = (viewHolder)convertView.getTag();
        }

        convertView.setTag(R.string.KEY_URL,article.getArticleUrl());

        holder.header.setText(article.getHeader());
        holder.preamble.setText(article.getPreamble());
        return convertView;
    }
}
