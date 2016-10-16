package com.zero.dibreak.domain.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Jin_ on 2016/10/16
 * 邮箱：Jin_Zboy@163.com
 */

public class ItemInfo {

    /**
     * _id : 57fee025421aa95de3b8ab9b
     * createdAt : 2016-10-13T09:15:17.365Z
     * desc : 快速生成 Add to Google Calendar 按钮。
     * images : ["http://img.gank.io/981e9747-1725-44f1-9e1d-c806a9ffc924"]
     * publishedAt : 2016-10-13T11:30:10.490Z
     * source : chrome
     * type : 前端
     * url : https://github.com/maxogden/gcalwizard
     * used : true
     * who : 机器人
     */

    @PrimaryKey
    @JsonProperty("_id")
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        if (null == images) {
            images = new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
