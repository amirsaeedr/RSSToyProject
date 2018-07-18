package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.site.SiteDao;
import ir.sahab.rsstoyproject.site.SiteDaoImp;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperPool implements Runnable {
    private Queue<String> URLS;
    private ExecutorService executor;

    public void start() {
        Thread thread = new Thread(this, "ScrapThread");
        thread.start();
    }

    public ScraperPool() {
        executor = Executors.newFixedThreadPool(10);
        SiteDao siteDao = SiteDaoImp.getInstance();
        URLS = siteDao.getURLs();
    }

    @Override
    public void run() {
        while (true) {
            String URL = URLS.poll();
            Scraper scraper = new Scraper(URL);
            executor.execute(scraper);
            URLS.add(URL);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
//            System.out.println(URL);
        }
    }
}
