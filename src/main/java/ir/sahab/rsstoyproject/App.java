package ir.sahab.rsstoyproject;

import ir.sahab.rsstoyproject.Controller.ContentFinder;
import ir.sahab.rsstoyproject.Controller.ContentFinder;
import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.model.DatabaseManager;
import ir.sahab.rsstoyproject.model.NewsManager;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Scraper RSSReader = new Scraper();
        String URL = "http://www.tabnak.ir/fa/rss/allnews";
        RSSReader.start(URL);
        String databasePassword = "li24v2hk77";
        String user = "root";
        NewsManager tmp = new NewsManager(user, databasePassword);
    }
}
