package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperPool implements Runnable {
    private ExecutorService executor;
    private SiteDao siteDao;

    public ScraperPool() {
        executor = Executors.newFixedThreadPool(10);
        siteDao = new SiteDaoImp();
    }

    @Override
    public void run() {
        Queue<String> urls = siteDao.getURLs();
        for (String url : urls) {
            executor.execute(new Scraper(url));
        }
    }
}
