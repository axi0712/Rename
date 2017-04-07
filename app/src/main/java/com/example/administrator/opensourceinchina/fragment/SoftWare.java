package com.example.administrator.opensourceinchina.fragment;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class SoftWare {
    private String objid;
    private String type;
    private String title;
    private String description;
    private String url;

    public SoftWare() {
    }

    @Override
    public String toString() {
        return "SoftWare{" +
                "objid='" + objid + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public SoftWare(String objid, String type, String title, String description, String url) {

        this.objid = objid;
        this.type = type;
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
