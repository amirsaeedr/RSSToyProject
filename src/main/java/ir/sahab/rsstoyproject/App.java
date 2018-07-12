package ir.sahab.rsstoyproject;
import ir.sahab.rsstoyproject.Controller.IOController;
import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.Controller.ConfigController;
import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.model.ConfigManager;

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
        Scraper RSSReader = new Scraper();
        ConfigManager templateManger = ConfigManager.getInstance(user,databasePassword);
        NewsManager newsManager = NewsManager.getInstance(user, databasePassword);
//        newsManager.add("test", "1 June 1998", "A", "nothing3", "content");
        String URL = "https://www.farsnews.com/rss";
        //RSSReader.start(URL);
        ConfigController configController = new ConfigController();
        configController.getConfig("fars");
    }
}
