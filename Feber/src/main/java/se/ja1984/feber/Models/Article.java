package se.ja1984.feber.Models;

import android.os.Parcel;
import android.os.Parcelable;
import org.jsoup.nodes.Element;
import se.ja1984.feber.Helpers.Keys;

/**
 * Created by Jack on 2014-05-15.
 */
public class Article implements Parcelable {
    private String Header;
    private String Text;
    private String ImageUrl;
    private String Preamble;
    private String ArticleUrl;
    private String Temperature;
    private String Category;
    private String Published;
    private String YouTubeId;

    public Article(Element element){
        setCategory(element.className());
        setHeader(element.select("h1.type2 a").first().text());
        setArticleUrl(element.select("h1.type2 a").first().attr("href"));
        setPreamble(element.select("div.preamble a").first().text());
        setText(element.select("div.body1").first().html());
        setTemperature(element.select("div.tempContainer div.temp").first().text());
        Element published = element.select("div.dastags > a:nth-child(3)").first();

        if(published != null) {
            setPublished(published.text());
        }
        else{
            setPublished("-");
        }
        Element youtubeImage = element.select("div.youtube > a img").first();
        if(youtubeImage != null){
            String id = youtubeImage.attr("id");
            String youTubeId = id.substring(3,(id.length()));
            setYouTubeId(youTubeId);
            setImageUrl(String.format(Keys.YOUTUBE_URL, youTubeId));
            return;
        }


        Element regularImage = element.select("div.img-img  img").first();
        if(regularImage != null){

            String imageUrl = regularImage.attr("src");

            if(imageUrl == ""){
                imageUrl = regularImage.attr("data-src");
            }

            setImageUrl(imageUrl);
        }
    }

    public Article() {

    }

    public String getHeader(){
        return Header;
    }

    public void setHeader(String header){
        this.Header = header;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text)
    {
        Text = text;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPreamble() {
        return Preamble;
    }

    public void setPreamble(String preamble) {
        Preamble = preamble;
    }

    public String getArticleUrl() {
        return ArticleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        ArticleUrl = articleUrl;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPublished() {
        return Published;
    }

    public void setPublished(String published) {
        Published = published;
    }

    public String getYouTubeId() {
        return YouTubeId;
    }

    public void setYouTubeId(String youTubeId) {
        YouTubeId = youTubeId;
    }


    protected Article(Parcel in) {
        Header = in.readString();
        Text = in.readString();
        ImageUrl = in.readString();
        Preamble = in.readString();
        ArticleUrl = in.readString();
        Temperature = in.readString();
        Category = in.readString();
        Published = in.readString();
        YouTubeId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Header);
        dest.writeString(Text);
        dest.writeString(ImageUrl);
        dest.writeString(Preamble);
        dest.writeString(ArticleUrl);
        dest.writeString(Temperature);
        dest.writeString(Category);
        dest.writeString(Published);
        dest.writeString(YouTubeId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };


}
