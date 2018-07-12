package ir.sahab.rsstoyproject;

import ir.sahab.rsstoyproject.Controller.ContentFinder;
import ir.sahab.rsstoyproject.Controller.ContentFinder;
import ir.sahab.rsstoyproject.Controller.IOController;
import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.model.DatabaseManager;
import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.model.TemplateManager;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String databasePassword = "1375109";
        String user = "root";
        Scraper RSSReader = new Scraper();
        DatabaseManager newsManager = NewsManager.getInstance(user, databasePassword);
        DatabaseManager templateManger = TemplateManager.getInstance(user,databasePassword);
        String URL = "https://www.farsnews.com/rss";
        RSSReader.start(URL);
    }
}
