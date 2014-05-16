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

    public Article(){

    }
    public Article(Element element){
        setHeader(element.select("h1.type2 a").first().text());
        setArticleUrl(element.select("h1.type2 a").first().attr("href"));
        setPreamble(element.select("div.preamble a").first().text());
        setText(element.select("div.body1").first().html());
        Element youtubeImage = element.select("div.youtube > a img").first();
        if(youtubeImage != null){
            String id = youtubeImage.attr("id");
            setImageUrl(String.format(YOUTUBE_URL, id.substring(3,(id.length()))));
            return;
        }


        Element regularImage = element.select("div.img-img  img").first();
        if(regularImage != null){
            setImageUrl(regularImage.attr("src"));
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
}
