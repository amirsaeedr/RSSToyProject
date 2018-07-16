package ir.sahab.rsstoyproject.news;

import java.util.Date;

public class News {
    private String title;
    private Date date;
    private String link;
    private String content;
    private String site;
    private int ID;

    public News(String title, Date date, String link, String content, String site, int ID) {
        this.title = title;
        this.date = date;
        this.link = link;
        this.content = content;
        this.site = site;
        this.ID = ID;
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

    public String getSite() { return site; }

    public void setSite(String site) { this.site = site; }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }
}
