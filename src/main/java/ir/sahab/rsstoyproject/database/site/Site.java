package ir.sahab.rsstoyproject.database.site;

import java.util.Date;

public class Site {
    private String name;
    private String pattern;
    private String dateFormat;
    private String rssLink;
    private int siteId;

    public void setName(String name) {
        this.name = name;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getRssLink() { return rssLink; }

    public int getSiteId() {
        return siteId;
    }

    public Site(String name, String pattern, String dateFormat, String rssLink, int siteId, Date lastUpdate) {
        this.name = name;
        this.pattern = pattern;
        this.dateFormat = dateFormat;
        this.rssLink = rssLink;
        this.siteId = siteId;
    }

    public Site() {

    }
}
