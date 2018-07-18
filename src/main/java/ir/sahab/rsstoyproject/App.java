package ir.sahab.rsstoyproject;

import asg.cliche.ShellFactory;
import ir.sahab.rsstoyproject.console.RequestHandler;
import ir.sahab.rsstoyproject.scraper.ScraperPool;
//import ir.sahab.rsstoyproject.scraper.Scraper;

import java.io.IOException;

/**
 * RSS feed reader!
 */
public class App {
    public static void main(String[] args) {
        ScraperPool pool = new ScraperPool();
        pool.start();
        try {
            ShellFactory.createConsoleShell("RSSFeedReader", "", new RequestHandler())
                    .commandLoop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
