package ir.sahab.rsstoyproject.site;

public class Site {
    private String name;
    private String pattern;
    private String dateFormat;
    private String RSSLink;

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

    public void setRSSLink(String RSSLink) {
        this.RSSLink = RSSLink;
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

    public String getRSSLink() { return RSSLink; }


    public int getSiteId() { return siteId; }

    public Site(String name, String pattern, String dateFormat, String rssLink, int siteId) {
        this.name = name;
        this.pattern = pattern;
        this.dateFormat = dateFormat;
        this.RSSLink = rssLink;
        this.siteId = siteId;
    }
    public Site(){

    }
}
