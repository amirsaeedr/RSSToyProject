package ir.sahab.rsstoyproject.site;

import java.util.ArrayList;
import java.util.Queue;

public interface SiteDao {
    String getPattern(String RSSLink);

    String getDateFormat(String site);

    Queue<String> getURLs();

    String getRSSURL();

    void addSite(String siteURL, String pattern, String datePattern);

    String findPattern(String RSSLink);

    void updateLastSrape(String URL);
}
