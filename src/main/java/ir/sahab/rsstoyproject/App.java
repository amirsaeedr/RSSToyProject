package ir.sahab.rsstoyproject;

import asg.cliche.ShellFactory;
import ir.sahab.rsstoyproject.console.RequestHandler;
import ir.sahab.rsstoyproject.scraper.ScraperPool;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * RSS feed reader!
 */
public class App {
    public static void main(String[] args) {
        ScraperPool scraperPool = new ScraperPool();
        scraperPool.start();
        try {
            ShellFactory.createConsoleShell("RSSFeedReader", "", new RequestHandler())
                    .commandLoop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
