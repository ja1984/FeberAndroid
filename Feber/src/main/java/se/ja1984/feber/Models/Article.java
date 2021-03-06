package se.ja1984.feber.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
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
    private String CompleteCategory;
    private String Published;
    private String YouTubeId;
    private String Id;
    public Author author;

    public Article(Element element){

        setCategory(element.className());
        setId(element.id());
        Element header = element.select("h1.type2 a").first();
        if(header == null){
            header = element.select("h1.type1 a").first();
        }
        setHeader(header == null ? "-" :  header.text());


        Log.d("ListArtivle","" + getHeader());
        Element url = element.select("h1.type2 a").first();
        if(url == null){
            url = element.select("h1.type1 a").first();
        }
        setArticleUrl(url == null ? "-" :  url.attr("href"));

        setPreamble(element.select("div.preamble a").first().text());

        setCompleteCategory(element.select("span.bodyCat").first().text());




        //Remove links in bottom
        element.select("div.body1 div.text a.linkSelf, div.body1 div.text a.linkBlank, div.body1 div.text-overlay, span.bodyCat").remove();

        Element text = element.select("div.body1").first();
        setText(Jsoup.clean(text.html(), Whitelist.basic()));

        setTemperature(element.select("div.tempContainer div.temp").first().text());
        Element published = element.select("div.dastags > a:nth-child(3)").first();

        setPublished(published == null ? "-" : published.text());


        Element author = element.select(".avatar .writer_icon").first();

        if(author != null){
            setAuthor(new Author(author.attr("alt"),author.attr("src")));
        }

        Element youtubeImage = element.select("div.youtube > a img").first();
        if(youtubeImage != null){
            String id = youtubeImage.attr("id");
            String youTubeId = id.substring(3,(id.length()));
            setYouTubeId(youTubeId);
            Element test = element.select("#img"+youTubeId).first();
            setImageUrl(String.format(Keys.YOUTUBE_URL, youTubeId));
            return;
        }


        Element regularImage = element.select("div.img-img  img").first();
        if(regularImage != null){

            String imageUrl = regularImage.attr("src");

            String lazyloadedImageSrc =regularImage.attr("data-src");

            if(lazyloadedImageSrc != null && !lazyloadedImageSrc.isEmpty())
                imageUrl = lazyloadedImageSrc;

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
    public String getId(){
        return Id;
    }

    public void setId(String id){
        this.Id = id.replace("article","");
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

    public int getTemperatureAsInt() {
        if(Temperature == null) return 0;
        return Integer.parseInt(Temperature.substring(0, 2).replace(".", ""));
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

    public String getCompleteCategory() {
        return CompleteCategory;
    }

    public void setCompleteCategory(String completeCategory) {
        CompleteCategory = completeCategory;
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

    public void setAuthor(Author author){
        this.author = author;
    }

    public Author getAuthor(){
        return author;
    }

    protected Article(Parcel in) {
        Header = in.readString();
        Text = in.readString();
        Id = in.readString();
        ImageUrl = in.readString();
        Preamble = in.readString();
        ArticleUrl = in.readString();
        Temperature = in.readString();
        Category = in.readString();
        CompleteCategory = in.readString();
        Published = in.readString();
        YouTubeId = in.readString();
        author = (Author) in.readValue(Author.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Header);
        dest.writeString(Text);
        dest.writeString(Id);
        dest.writeString(ImageUrl);
        dest.writeString(Preamble);
        dest.writeString(ArticleUrl);
        dest.writeString(Temperature);
        dest.writeString(Category);
        dest.writeString(CompleteCategory);
        dest.writeString(Published);
        dest.writeString(YouTubeId);
        dest.writeValue(author);
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
