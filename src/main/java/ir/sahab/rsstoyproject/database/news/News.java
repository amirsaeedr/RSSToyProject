package ir.sahab.rsstoyproject.database.news;

import java.util.Date;

public class News {
    private String title;
    private Date date;
    private String link;
    private String content;
    private int siteId;
    private int newsId;

    public News(String title, Date date, String link, String content, int siteId, int newsId) {
        this.title = title;
        this.date = date;
        this.link = link;
        this.content = content;
        this.siteId = siteId;
        this.newsId = newsId;
    }

    public News() {
    }

//    public News(String title) {
//        this.title = title;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSite(int siteId) {
        this.siteId = siteId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsID(int newsId) {
        this.newsId = newsId;
    }
}
