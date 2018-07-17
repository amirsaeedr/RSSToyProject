package ir.sahab.rsstoyproject;
//import ir.sahab.rsstoyproject.Controller.IOController;

import asg.cliche.ShellFactory;
import ir.sahab.rsstoyproject.console.ConsoleHandler;
import ir.sahab.rsstoyproject.console.RequstHandler;
import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;
import ir.sahab.rsstoyproject.scraper.Scraper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * RSS feed reader!
 */
public class App {
    public static void main(String[] args) {

//        Scraper RSSReader = new Scraper();
//        RSSReader.start();
//        ConsoleHandler consoleHandler = new ConsoleHandler();
//        consoleHandler.run();
        try {
            ShellFactory.createConsoleShell("RSSFeadReader", "", new RequstHandler())
                    .commandLoop();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
