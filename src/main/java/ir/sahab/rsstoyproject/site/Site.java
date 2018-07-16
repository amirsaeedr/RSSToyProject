package ir.sahab.rsstoyproject.site;

public class Site {
    private String name;
    private String pattern;
    private String dateFormat;
    private String RSSLivk;

    public void setName(String name) {
        this.name = name;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setRSSLivk(String RSSLivk) {
        this.RSSLivk = RSSLivk;
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

    public String getRSSLivk() {
        return RSSLivk;
    }

    public Site(String name, String pattern, String dateFormat, String rssLivk) {
        this.name = name;
        this.pattern = pattern;
        this.dateFormat = dateFormat;
        RSSLivk = rssLivk;
    }
    public Site(){

    }
}
