package com.agah.furkan.newsapplicationwithweka.data.local.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.Nullable;

@Entity(tableName = "ArticlesTable")
public class RoomArticleModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id=0;
    @Nullable
    @ColumnInfo(name = "source")
    private String source;
    @Nullable
    @ColumnInfo(name = "author")
    private String author;
    @Nullable
    @ColumnInfo(name = "title")
    private String title;
    @Nullable
    @ColumnInfo(name = "description")
    private String description;
    @Nullable
    @ColumnInfo(name = "url")
    private String url;
    @Nullable
    @ColumnInfo(name = "urlToImage")
    private String urlToImage;
    @Nullable
    @ColumnInfo(name = "publishedAt")
    private String publishedAt;
    @Nullable
    @ColumnInfo(name = "content")
    private String content;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}