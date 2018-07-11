package ir.sahab.rsstoyproject;

import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.model.TemplateManager;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String databasePassword = "li24v2hk77";
        String user = "root";
       // Scraper RSSReader = new Scraper();

        TemplateManager templateManger = TemplateManager.getInstance(user,databasePassword);
        NewsManager newsManager = NewsManager.getInstance(user, databasePassword);
        newsManager.add("test", "1 June 1998", "A", "nothing3", "content");
        String URL = "https://www.farsnews.com/rss";
        //RSSReader.start(URL);
    }
}
