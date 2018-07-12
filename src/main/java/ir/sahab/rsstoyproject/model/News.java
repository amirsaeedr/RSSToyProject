package ir.sahab.rsstoyproject.model;

public class News {
    private String title;
    private String date;
    private String author;
    private String link;
    private String content;
    private String site;

    public News(String title, String date, String author, String link, String content, String site) {
        this.title = title;
        this.date = date;
        this.author = author;
        this.link = link;
        this.content = content;
        this.site = site;
    }

    public News() {
    }

    public News(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
