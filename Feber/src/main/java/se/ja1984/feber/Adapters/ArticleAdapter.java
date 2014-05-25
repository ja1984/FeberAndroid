package se.ja1984.feber.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.ja1984.feber.Helpers.Temperature;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.R;

import java.util.ArrayList;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private final Context _context;
    private final ArrayList<Article> _articles;
    int _resource;
    private Temperature _temperatureHelper;

    static class viewHolder{
        @InjectView(R.id.txtArticleHeader) TextView header;
        @InjectView(R.id.txtArticlePreamble) TextView preamble;
        @InjectView(R.id.txtArticleTemperature) TextView temperature;
        @InjectView(R.id.txtArticleCategory) TextView category;
        @InjectView(R.id.txtArticlePublished) TextView published;

        public viewHolder(View view){
            ButterKnife.inject(this, view);
        }

    }

    public ArticleAdapter(Context context, int resource, ArrayList<Article> articles) {
        super(context, resource, articles);
        _context = context;
        _articles = articles;
        _resource = resource;
        _temperatureHelper = new Temperature();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        Article article = _articles.get(position);

        if(convertView == null)
        {
            convertView = View.inflate(_context, _resource, null);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (viewHolder)convertView.getTag();
        }

        holder.header.setText(article.getHeader());
        holder.preamble.setText(article.getPreamble());
        holder.category.setText(article.getCategory());
        holder.published.setText("Publiserad " + article.getPublished());

        String _temperature = article.getTemperature();
        holder.temperature.setText(_temperature);
        holder.temperature.setBackground(_context.getResources().getDrawable(_temperatureHelper.setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
        return convertView;
    }
}
