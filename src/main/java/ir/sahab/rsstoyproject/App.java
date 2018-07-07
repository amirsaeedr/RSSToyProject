package ir.sahab.rsstoyproject;

import org.jsoup.nodes.Document;

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
    }
}
