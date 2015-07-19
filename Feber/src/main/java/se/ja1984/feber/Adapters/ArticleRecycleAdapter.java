package se.ja1984.feber.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.Helpers.CircularImageView;
import se.ja1984.feber.MainActivity;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.R;

/**
 * Created by Jack on 2015-07-15.
 */
public class ArticleRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context _context;
    private ArrayList<Article> _articles;


    public ArticleRecycleAdapter(Context context, ArrayList<Article> articles){
        _context = context;
        _articles = articles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.title) TextView title;
        @Bind(R.id.information) TextView information;
        @Bind(R.id.image) ImageView image;
        @Bind(R.id.authorImage)        CircularImageView authorImage;
        @Bind(R.id.temp)        TextView temp;
        @Bind(R.id.card_view)
        CardView wrapper;

        public ViewHolder(View v){
            super(v);
            ButterKnife.bind(this,v);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return MainActivity.hiddenCategories.contains(_articles.get(position).getCategory()) ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( viewType == 0 ? R.layout.listitem_article_card_alt : R.layout.listitem_article_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, int position) {
        final Article article = _articles.get(position);

        ArticleRecycleAdapter.ViewHolder holder = ((ArticleRecycleAdapter.ViewHolder)_holder);

        Glide.with(_context).load(article.getImageUrl()).error(R.drawable.placeholder).into(holder.image);
        Glide.with(_context).load(article.getAuthor().getImage()).into(holder.authorImage);

        holder.image.setVisibility(MainActivity.hiddenCategories.contains(article.getCategory()) ? View.GONE : View.VISIBLE);

        holder.title.setText(article.getHeader());
        holder.information.setText(article.getAuthor().getName());
        holder.temp.setText(article.getTemperature());
        holder.temp.setBackgroundTintList(ColorStateList.valueOf(_context.getResources().getColor(getBackgroundColor(article.getTemperatureAsInt()))));

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, ArticleActivity.class);
                intent.putExtra("Article", article);
                _context.startActivity(intent);
            }
        });

        String _temperature = article.getTemperature();
    }

    @Override
    public int getItemCount() {
        return _articles.size();
    }

    private int getBackgroundColor(int temperature) {
        if(temperature >= 39) return R.color.hot;
        if(temperature <= 35) return R.color.cold;

        return R.color.feber;
    }
}
