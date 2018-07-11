package ir.sahab.rsstoyproject;

import ir.sahab.rsstoyproject.Controller.Scraper;
import ir.sahab.rsstoyproject.model.DataBaseManager;

/**
 * RSS feed reader!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scraper RSSReader = new Scraper();
        String URL = "http://www.tabnak.ir/fa/rss/allnews";
        RSSReader.start(URL);
//        String database_password = "li24v2hk77";
//        DataBaseManager DBM = new DataBaseManager(database_password);
//        DBM.create_database();
//        DBM.create_table();
    }
}
