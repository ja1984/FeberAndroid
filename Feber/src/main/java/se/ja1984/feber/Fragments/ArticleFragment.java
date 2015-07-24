package se.ja1984.feber.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laurencedawson.activetextview.ActiveTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.FeberApplication;
import se.ja1984.feber.Helpers.CircularImageView;
import se.ja1984.feber.Helpers.TextDrawable;
import se.ja1984.feber.Helpers.Utils;
import se.ja1984.feber.Interface.TaskCompleted;
import se.ja1984.feber.Models.Article;
import se.ja1984.feber.Models.Post;
import se.ja1984.feber.R;
import se.ja1984.feber.Services.ArticleService;

/**
 * Created by jonathan on 2014-05-16.
 */
public class ArticleFragment extends Fragment {

    @Bind(R.id.toolbar)    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.backdrop)    ImageView image;
    @Bind(R.id.text)    ActiveTextView text;
    @Bind(R.id.fab)    FloatingActionButton fab;
    @Bind(R.id.authorImage)    CircularImageView authorImage;
    @Bind(R.id.information) TextView information;
    @Bind(R.id.comments) LinearLayout comments;
    @Bind(R.id.youtube)    LinearLayout youtubeWrapper;
    @Bind(R.id.nocomments)    TextView nocomments;



    Activity _activity;
    Article _article;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, rootView);

        ((ArticleActivity)getActivity()).setSupportActionBar(toolbar);
        ((ArticleActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String youTubeId = ((ArticleActivity) getActivity()).article.getYouTubeId();

        if(!Utils.stringIsNullorEmpty(youTubeId)) {

            youtubeWrapper.setVisibility(View.VISIBLE);

            youtubeWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youTubeId)));
                }
            });
        }

        loadArticle(((ArticleActivity) getActivity()).article);



        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
    }

    public ArticleFragment(){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_article,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_openInBrowser:
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(_article.getArticleUrl()));
                startActivity(i);
                return true;
            case R.id.menu_share:
                shareArticle();
                return true;
            case R.id.home:
                super.onOptionsItemSelected(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }

    private void shareArticle() {
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide what to do with it.
        intent.putExtra(Intent.EXTRA_SUBJECT, _article.getHeader());
        intent.putExtra(Intent.EXTRA_TEXT, _article.getArticleUrl());
        startActivity(Intent.createChooser(intent, String.format("Dela %s", _article.getHeader())));
    }



    public void loadArticle(Article _article){
        this._article = _article;


        new ArticleService(_activity, new TaskCompleted<List<Post>>() {
            @Override
            public void onTaskComplete(List<Post> result) {

                if(result ==null || result.size() == 0){
                    nocomments.setVisibility(View.VISIBLE);
                    comments.setVisibility(View.GONE);
                    return;
                }

                for(Post post : result){
                    final View view = _activity.getLayoutInflater().inflate(R.layout.listitem_comment, comments, false);
                    CircularImageView image = (CircularImageView)view.findViewById(R.id.image);
                    TextView userName = (TextView)view.findViewById(R.id.username);
                    TextView comment = (TextView)view.findViewById(R.id.comment);

                    userName.setText(post.author.username);
                    comment.setText(post.raw_message);
                    Glide.with(_activity).load(post.author.avatar.permalink).into(image);

                    comments.addView(view);
                }
            }
        }).loadComments(_article.getId());

        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(getBackgroundColor(_article.getTemperatureAsInt()))));
        fab.setImageDrawable(new TextDrawable(_article.getTemperature()));
        collapsingToolbar.setTitle(_article.getHeader());
        text.setText(Html.fromHtml(_article.getText()));

        Glide.with(_activity).load(_article.getAuthor().getImage()).into(authorImage);

        information.setText(_article.getAuthor().getName());

        if(_article.getImageUrl() != null || _article.getImageUrl() != "") {
            Glide.with(_activity).load(_article.getImageUrl()).placeholder(R.drawable.placeholder).into(image);
        }

    }

    private int getBackgroundColor(int temperature) {
        if(temperature >= 39) return R.color.hot;
        if(temperature <= 35) return R.color.cold;

        return R.color.feber;
    }
}
