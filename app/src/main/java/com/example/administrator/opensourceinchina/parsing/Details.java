package com.example.administrator.opensourceinchina.parsing;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class Details {
    private String title;
    private String url;
    private String body;
    private String id;

    public Details() {
    }

    @Override
    public String toString() {
        return "Details{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Details(String title, String url, String body, String id) {
        this.title = title;
        this.url = url;
        this.body = body;
        this.id = id;
    }
}
