package se.ja1984.feber.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Helpers.CircularImageView;
import se.ja1984.feber.Helpers.Temperature;
import se.ja1984.feber.MainActivity;
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
        //@Bind(R.id.txtArticleHeader) TextView header;
        //@Bind(R.id.txtArticlePreamble) TextView preamble;
        //@Bind(R.id.txtArticleTemperature) TextView temperature;
        //@Bind(R.id.txtArticleCategory) TextView category;
        @Bind(R.id.title) TextView title;
        @Bind(R.id.information) TextView information;
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.authorImage)        CircularImageView authorImage;
        @Bind(R.id.temp)        TextView temp;

        public viewHolder(View view){
            ButterKnife.bind(this, view);
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

        Glide.with(_context).load(article.getImageUrl()).error(R.drawable.placeholder).into(holder.image);
        Glide.with(_context).load(article.getAuthor().getImage()).into(holder.authorImage);

        holder.image.setVisibility(MainActivity.hiddenCategories.contains(article.getCategory()) ? View.GONE : View.VISIBLE);


        holder.title.setText(article.getHeader());
        holder.information.setText(article.getAuthor().getName());
        holder.temp.setText(article.getTemperature());
        holder.temp.setBackgroundTintList(ColorStateList.valueOf(_context.getResources().getColor(getBackgroundColor(article.getTemperatureAsInt()))));

        //holder.preamble.setText(article.getPreamble());
        //holder.category.setText(article.getCategory());
        //holder.published.setText("Publiserad " + article.getPublished());

        String _temperature = article.getTemperature();
        //holder.temperature.setText(_temperature);
        //holder.temperature.setBackground(_context.getResources().getDrawable(_temperatureHelper.setBackgroundBasedOnTemperature(Integer.parseInt(_temperature.substring(0, 2).replace(".", "")))));
        return convertView;
    }


    private int getBackgroundColor(int temperature) {
        if(temperature >= 39) return R.color.hot;
        if(temperature <= 35) return R.color.cold;

        return R.color.feber;
    }

}
