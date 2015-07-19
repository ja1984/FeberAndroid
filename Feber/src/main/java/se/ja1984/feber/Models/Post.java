package se.ja1984.feber.Models;

/**
 * Created by Jack on 2015-07-16.
 */
public class Post {
    public final String raw_message;
    public final String createdAt;
    public final disqus_author author;

    public Post(String raw_message, String createdAt, disqus_author author) {
        this.raw_message = raw_message;
        this.createdAt = createdAt;
        this.author = author;
    }
}
