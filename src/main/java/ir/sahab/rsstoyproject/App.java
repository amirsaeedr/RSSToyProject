package ir.sahab.rsstoyproject;
import java.security.*;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import sun.misc.BASE64Encoder;
import java.math.*;
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
        NewsManager newsManager = NewsManager.getInstance(user, databasePassword);
        ConfigManager configManger = ConfigManager.getInstance(user,databasePassword);
        Scraper RSSReader = new Scraper();
        String URL = "https://www.farsnews.com/rss";
        RSSReader.start(URL);
//        ConfigController configController = new ConfigController();
//        configController.getConfig("fars");
    }
}
