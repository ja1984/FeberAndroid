package se.ja1984.feber.Models;

import org.jsoup.nodes.Element;

/**
 * Created by Jack on 2014-05-15.
 */
public class Article {
    private String YOUTUBE_URL = "http://img.youtube.com/vi/%s/maxresdefault.jpg";
    private String Header;
    private String Text;
    private String ImageUrl;
    private String Preamble;
    private String ArticleUrl;
    private String Temperature;
    private String Category;
    private String Published;

    public Article(){

    }
    public Article(Element element){
        setCategory(element.className());
        setHeader(element.select("h1.type2 a").first().text());
        setArticleUrl(element.select("h1.type2 a").first().attr("href"));
        setPreamble(element.select("div.preamble a").first().text());
        setText(element.select("div.body1").first().html());
        setTemperature(element.select("div.tempContainer div.temp").first().text());
        setPublished(element.select("div.dastags > a:nth-child(3)").first().text());
        Element youtubeImage = element.select("div.youtube > a img").first();
        if(youtubeImage != null){
            String id = youtubeImage.attr("id");
            setImageUrl(String.format(YOUTUBE_URL, id.substring(3,(id.length()))));
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

    public String getHeader(){return Header;}
    public void setHeader(String header){this.Header = header;}

    public String getText() {return Text;}
    public void setText(String text) {Text = text;}

    public String getImageUrl() {return ImageUrl;}

    public void setImageUrl(String imageUrl) {ImageUrl = imageUrl;}

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
}
