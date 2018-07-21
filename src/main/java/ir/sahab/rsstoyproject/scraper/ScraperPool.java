package ir.sahab.rsstoyproject.scraper;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperPool implements Runnable {
    private ExecutorService executor;
    private SiteDao siteDao;
    private String databse;

    public ScraperPool(String database) {
        executor = Executors.newFixedThreadPool(10);
        siteDao = new SiteDaoImp(database);
        this.databse = database;
    }

    @Override
    public void run() {
        Queue<String> urls = siteDao.getUrls();
        for (String url : urls) {
            executor.execute(new Scraper(url, databse));
        }
    }


}
