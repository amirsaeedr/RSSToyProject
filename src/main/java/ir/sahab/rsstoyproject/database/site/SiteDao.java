package ir.sahab.rsstoyproject.database.site;

import java.util.ArrayList;
import java.util.Queue;

public interface SiteDao {
    String getPattern(String rssLink);

    String getDateFormat(String site);

    Queue<String> getUrls();

    void addSite(String siteUrl, String pattern);

    ArrayList<String> getDateFormats();

    void setDateFormat(String format, String rssLink);
}
