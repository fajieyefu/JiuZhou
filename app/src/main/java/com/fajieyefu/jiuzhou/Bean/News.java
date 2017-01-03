package com.fajieyefu.jiuzhou.Bean;

/**
 * Created by Fajieyefu on 2016/7/11.
 */
public class News {
    private String title;
    private String content;
    private String newsTime;
    private String fb_person;
    private String url;

    public String getContent() {
        return content;
    }

    public String getFb_person() {
        return fb_person;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public News(String title, String content, String newsTime, String fb_person, String url) {

        this.title = title;
        this.content = content;
        this.newsTime = newsTime;
        this.fb_person = fb_person;
        this.url = url;
    }
}
