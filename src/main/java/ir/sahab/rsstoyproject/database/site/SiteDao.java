package ir.sahab.rsstoyproject.database.site;

import java.util.ArrayList;
import java.util.Queue;

public interface SiteDao {
    String getPattern(String RSSLink);

    String getDateFormat(String site);

    Queue<String> getURLs();

    void addSite(String siteURL, String pattern);

    String findPattern(String RSSLink);

    ArrayList<String> getDateFormats();

    void setDateFormat(String format , String rssLink);
}
