package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperPool implements Runnable {
    private Queue<String> urls;
    private ExecutorService executor;

    public void start() {
        Thread thread = new Thread(this, "ScrapThread");
        thread.start();
    }

    public ScraperPool() {
        urls= new LinkedList<>();
        executor = Executors.newFixedThreadPool(20);
        SiteDao siteDao = new SiteDaoImp();
        urls = new LinkedList<>(siteDao.getURLs());
    }
    public void addUrl(String url){
        urls.add(url);
    }
    @Override
    public void run() {
        while (true) {
            String URL = urls.poll();
            executor.execute(new Scraper(URL));
            urls.add(URL);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
