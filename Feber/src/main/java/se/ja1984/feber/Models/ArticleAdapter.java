package se.ja1984.feber.Models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import se.ja1984.feber.Helpers.Temperature;
import se.ja1984.feber.R;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private final Context context;
    private final ArrayList<Article> articles;
    int resource;

    static class viewHolder{
        TextView header;
        TextView preamble;
        TextView temperature;
        TextView category;
        TextView published;
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
            holder.temperature = (TextView)convertView.findViewById(R.id.txtArticleTemperature);
            holder.category = (TextView)convertView.findViewById(R.id.txtArticleCategory);
            holder.published = (TextView)convertView.findViewById(R.id.txtArticlePublished);
            convertView.setTag(holder);
        }
        else
        {
            holder = (viewHolder)convertView.getTag();
        }

        convertView.setTag(R.string.KEY_URL,article.getArticleUrl());

        holder.header.setText(article.getHeader());
        holder.preamble.setText(article.getPreamble());
        holder.category.setText(article.getCategory());
        holder.published.setText("Publiserad " + article.getPublished());

        String _temperature = article.getTemperature();
        holder.temperature.setText(_temperature);
        holder.temperature.setBackground(context.getResources().getDrawable(new Temperature().setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
        return convertView;
    }
}
