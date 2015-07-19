package se.ja1984.feber.Models;

/**
 * Created by Jack on 2015-07-16.
 */
public class disqus_author {
    public final String username;
    public final disqus_avatar avatar;

    public disqus_author(String username, disqus_avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }
}
