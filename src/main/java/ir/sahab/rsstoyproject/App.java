package ir.sahab.rsstoyproject;
import ir.sahab.rsstoyproject.Controller.IOController;

/**
 * RSS feed reader!
 *
 */
public class App
{
    public static void main( String[] args ) {
        IOController ioController = new IOController();
        Scraper RSSReader = new Scraper();

        ioController.start();
        RSSReader.start();
    }
}
