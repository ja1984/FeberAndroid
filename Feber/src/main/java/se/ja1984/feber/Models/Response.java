package se.ja1984.feber.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2015-07-16.
 */
public class Response {
    public final List<Post> posts;

    public Response(List<Post> posts) {
        this.posts = posts;
    }

}
