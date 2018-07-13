package ir.sahab.rsstoyproject;
import ir.sahab.rsstoyproject.Controller.IOController;
import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.model.ConfigManager;

import java.util.ArrayList;

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
