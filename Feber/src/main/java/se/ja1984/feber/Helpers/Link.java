package se.ja1984.feber.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import se.ja1984.feber.Activities.ArticleActivity;
import se.ja1984.feber.R;

/**
 * Created by Jack on 2014-05-17.
 */
public class Link {
    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span, final Activity activity)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        Log.d("Test","Span - " + span.getURL());
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                Log.d("Feber - link","" + span.getURL());
                if(span.getURL().contains("feber.se")){
                    Intent intent = new Intent(activity, ArticleActivity.class);
                    intent.putExtra("ArticleUrl", view.getTag(R.string.KEY_URL).toString());
                    activity.startActivity(intent);
                }
                // Do something with span.getURL() to handle the link click...
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public void setTextViewHTML(TextView text, String html, final Activity activity)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {

            makeLinkClickable(strBuilder, span, activity);
        }
        text.setAutoLinkMask(Linkify.ALL);
        text.setLinksClickable(true);
        text.setText(strBuilder);

    }
}
