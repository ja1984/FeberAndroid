package se.ja1984.feber.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.FeberApplication;
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
        @Bind(R.id.preamble) TextView preamble;
        @Bind(R.id.information) TextView information;
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.authorImage)        CircularImageView authorImage;
        @Bind(R.id.temp)        TextView temp;
        @Bind(R.id.container)
        LinearLayout wrapper;


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

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {
        return MainActivity.hiddenCategories.contains(_articles.get(position).getCategory()) ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        Article article = _articles.get(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemViewType(position) == 0 ? R.layout.listitem_article_card_alt : R.layout.listitem_article_card, parent, false);
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

        holder.wrapper.setBackgroundColor(_context.getResources().getColor(FeberApplication.readArticles.contains(article.getId()) ? R.color.card_read : R.color.card_default));


        holder.title.setText(article.getHeader());
        holder.preamble.setText(article.getPreamble());
        holder.information.setText(article.getAuthor().getName());
        holder.temp.setText(article.getTemperature());


        if (Build.VERSION.SDK_INT < 16) {
            holder.temp.setBackgroundDrawable(_context.getResources().getDrawable(getBackgroundColor(article.getTemperatureAsInt())));
        }
        else{
            holder.temp.setBackground(_context.getResources().getDrawable(getBackgroundColor(article.getTemperatureAsInt())));
        }
        return convertView;
    }


    private int getBackgroundColor(int temperature) {
        if(temperature >= 39) return R.drawable.circle_hot;
        if(temperature <= 35) return R.drawable.circle_cold;

        return R.drawable.circle_feber;
    }

}
