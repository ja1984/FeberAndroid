package se.ja1984.feber.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jack on 2015-07-14.
 */

public class Author implements Parcelable {
    private String name;
    private String image;

    public Author(){}
    public Author(String name, String image){
        this.name = name;
        this.image = image;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }



    protected Author(Parcel in) {
        name = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}