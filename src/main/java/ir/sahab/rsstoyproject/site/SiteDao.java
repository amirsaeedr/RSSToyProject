package ir.sahab.rsstoyproject.site;

import java.util.ArrayList;

public interface SiteDao {
    String getPattern(String RSSLink);
    String getDateFormat(String site);
    ArrayList<String> getURLs();
    void addSite(Site site);
    String findPattern(String RSSLink);
}
