package ir.sahab.rsstoyproject.site;

import java.util.Date;

public class Site {
    private String name;
    private String pattern;
    private String dateFormat;
    private String RSSLink;
    private int siteId;
    private Date lastUpdate;



    public void setName(String name) {
        this.name = name;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setRSSLink(String RSSLink) {
        this.RSSLink = RSSLink;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public String getRSSLink() { return RSSLink; }

    public int getSiteId() { return siteId; }

    public Date getLastUpdate() { return lastUpdate; }

    public Site(String name, String pattern, String dateFormat, String rssLink, int siteId, Date lastUpdate) {
        this.name = name;
        this.pattern = pattern;
        this.dateFormat = dateFormat;
        this.RSSLink = rssLink;
        this.siteId = siteId;
        this.lastUpdate = lastUpdate;
    }
    public Site(){

    }
}
