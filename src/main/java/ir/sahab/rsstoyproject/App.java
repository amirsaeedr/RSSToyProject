package ir.sahab.rsstoyproject;
//import ir.sahab.rsstoyproject.Controller.IOController;
import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;
import ir.sahab.rsstoyproject.scraper.Scraper;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args ) {
//        IOController ioController = new IOController();
        Scraper RSSReader = new Scraper();

//        ioController.start();
        RSSReader.start();
    }
}
