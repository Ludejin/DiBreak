package com.zero.dibreak.domain.model.response;

import com.fasterxml.jackson.annotation.JacksonInject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jin_ on 2016/10/8
 * 邮箱：Jin_Zboy@163.com
 */

public class Sister extends RealmObject {

    /**
     * _id : 57bc5238421aa9125fa3ed70
     * createdAt : 2016-08-23T21:40:08.159Z
     * desc : 8.24
     * publishedAt : 2016-08-24T11:38:48.733Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f740f701gqj20u011hgo9.jpg
     * used : true
     * who : daimajia
     */

    @JacksonInject("_id")
    @PrimaryKey
    private String id;

    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

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
}
