package se.ja1984.feber.Helpers;

/**
 * Created by Jack on 2015-07-24.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************************
 * This file is part of RedReader.
 *
 * RedReader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RedReader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RedReader.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
public class LinkHandler {

    public static final Pattern
            youtubeDotComPattern = Pattern.compile("^https?://[\\.\\w]*youtube\\.\\w+/.*"),
            youtuDotBePattern = Pattern.compile("^https?://[\\.\\w]*youtu\\.be/([A-Za-z0-9\\-_]+)(\\?.*|).*"),
            vimeoPattern = Pattern.compile("^https?://[\\.\\w]*vimeo\\.\\w+/.*"),
            googlePlayPattern = Pattern.compile("^https?://[\\.\\w]*play\\.google\\.\\w+/.*");

    public static void onLinkClicked(Activity activity, String url) {
        onLinkClicked(activity, url, false);
    }

    public static void onLinkClicked(Activity activity, String url, boolean forceNoImage) {
        onLinkClicked(activity, url, forceNoImage);
    }



    public static void openWebBrowser(Activity activity, Uri uri) {
        try {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch(Exception e) {

        }
    }

    public static final Pattern imgurPattern = Pattern.compile(".*imgur\\.com/(\\w+).*"),
            qkmePattern1 = Pattern.compile(".*qkme\\.me/(\\w+).*"),
            qkmePattern2 = Pattern.compile(".*quickmeme\\.com/meme/(\\w+).*"),
            lvmePattern = Pattern.compile(".*livememe\\.com/(\\w+).*");

    // TODO handle GIFs
    public static String getImageUrl(final String url) {

        // TODO download anyway and check the mimetype

        // TODO If this fails - download the page and try to find the image - get content type from HTTP request and save in cache
        // TODO If this fails, show the internal browser

        final Matcher matchImgur = imgurPattern.matcher(url);

        if(matchImgur.find()) {
            final String imgId = matchImgur.group(1);
            if(imgId.length() > 2 && !imgId.startsWith("gallery"))
                return String.format("https://i.imgur.com/%s.jpg", imgId);
        }

        final String urlLower = url.toLowerCase();

        final String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webm", ".mp4", ".h264", ".gifv", ".mkv", ".3gp"};

        for(final String ext : imageExtensions) {
            if(urlLower.endsWith(ext)) {
                return url;
            }
        }

        if(url.contains("?")) {

            final String urlBeforeQ = urlLower.split("\\?")[0];

            for(final String ext : imageExtensions) {
                if(urlBeforeQ.endsWith(ext)) {
                    return url;
                }
            }
        }

        final Matcher matchQkme1 = qkmePattern1.matcher(url);

        if(matchQkme1.find()) {
            final String imgId = matchQkme1.group(1);
            if(imgId.length() > 2)
                return String.format("http://i.qkme.me/%s.jpg", imgId);
        }

        final Matcher matchQkme2 = qkmePattern2.matcher(url);

        if(matchQkme2.find()) {
            final String imgId = matchQkme2.group(1);
            if(imgId.length() > 2)
                return String.format("http://i.qkme.me/%s.jpg", imgId);
        }

        final Matcher matchLvme = lvmePattern.matcher(url);

        if(matchLvme.find()) {
            final String imgId = matchLvme.group(1);
            if(imgId.length() > 2)
                return String.format("http://www.livememe.com/%s.jpg", imgId);
        }

        return null;
    }

    public static LinkedHashSet<String> computeAllLinks(final String text) {

        final LinkedHashSet<String> result = new LinkedHashSet<String>();

        // From http://stackoverflow.com/a/1806161/1526861
        // TODO may not handle .co.uk, similar (but should handle .co/.us/.it/etc fine)
        final Pattern urlPattern = Pattern.compile("\\b((((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                "|mil|biz|info|mobi|name|aero|jobs|museum" +
                "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?)\\b");

        final Matcher urlMatcher = urlPattern.matcher(text);

        while(urlMatcher.find()) {
            result.add(urlMatcher.group(1));
        }

        final Matcher subredditMatcher = Pattern.compile("(?<!\\w)(/?[ru]/\\w+)\\b").matcher(text);

        while(subredditMatcher.find()) {
            result.add(subredditMatcher.group(1));
        }

        return result;
    }
}
