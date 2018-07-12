package ir.sahab.rsstoyproject;
import ir.sahab.rsstoyproject.Controller.IOController;
import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.model.ConfigManager;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args ) {
        String databasePassword = "1375109";
        String user = "root";
        NewsManager newsManager = NewsManager.getInstance(user, databasePassword);
        ConfigManager configManger = ConfigManager.getInstance(user,databasePassword);
        Scraper RSSReader = new Scraper();
        IOController ioController = new IOController();
        ioController.start();
        RSSReader.start();
    }
}
