package se.ja1984.feber.Models;

/**
 * Created by Jack on 2015-07-16.
 */
public class DisqusResponse {
    public final Response response;

    public DisqusResponse(Response response) {
        this.response = response;
    }

    @Override public String toString() {
        return "response=" + response;
    }
}
