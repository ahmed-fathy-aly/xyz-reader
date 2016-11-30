package com.example.xyzreader.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed on 11/26/2016.
 */

public class ArticleDetails implements Parcelable {

    private long id;
    private String title;
    private String body;
    private String imageUrl;
    private long publishDate;
    private String authorName;

    public ArticleDetails(long id, String title, String body, String imageUrl, long publishDate, String authorName) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.authorName = authorName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    protected ArticleDetails(Parcel in) {
        id = in.readLong();
        title = in.readString();
        imageUrl = in.readString();
        publishDate = in.readLong();
        authorName = in.readString();
        body = in.readString();
    }

    public static final Creator<ArticleDetails> CREATOR = new Creator<ArticleDetails>() {
        @Override
        public ArticleDetails createFromParcel(Parcel in) {
            return new ArticleDetails(in);
        }

        @Override
        public ArticleDetails[] newArray(int size) {
            return new ArticleDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(imageUrl);
        dest.writeLong(publishDate);
        dest.writeString(authorName);
    }

    public static ArticleDetails fromCursor(Cursor cursor) {
        return new ArticleDetails(
                cursor.getLong(ArticleLoader.Query._ID),
                cursor.getString(ArticleLoader.Query.TITLE),
                cursor.getString(ArticleLoader.Query.BODY),
                cursor.getString(ArticleLoader.Query.PHOTO_URL),
                cursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                cursor.getString(ArticleLoader.Query.AUTHOR)
        );
    }
}
