package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.Controller.ConfigController;
import ir.sahab.rsstoyproject.news.News;
import ir.sahab.rsstoyproject.service.NewsService;
import ir.sahab.rsstoyproject.service.SiteService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Scraper implements Runnable {
    private NewsService newsService;
    private SiteService siteService;
    private Document contentDoc = null;
    private Document RSSDoc = null;
    private Thread thread;
    private String RSSAddress;

    public Scraper() {
        newsService = new NewsService();
        siteService = new SiteService();
    }

    @Override
    public void run() {

    }

    public void start() {
        thread = new Thread(this, "ScrapThread");
        thread.start();
    }

    private void scrapeNewsData() {
        Elements items = RSSDoc.select("item");
        String title = null;
        String content = null;
        String site = null;
        String newsLink = null;
        Date date = null;
        for (Element item : items) {
            title = getNewsTitle(item);
            date = getNewsDate(item);
            newsLink = getNewsLink(item);
            content = getNewsContent(newsLink, item);
            site = getNewsSite();
            newsService.addNews(title, date, newsLink, content, site);
            try {
                thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void scrape(String RSSLink) {
        try {
            RSSAddress = RSSLink;
            RSSDoc = Jsoup.connect(RSSLink).get();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
        scrapeNewsData();
    }

    private String getNewsSite() {
        return RSSAddress.split("/")[2];
    }

    private String getNewsTitle(Element item) {
        return item.select("title").html();
    }

    private Date getNewsDate(Element item) {
        String dateString = item.select("pubDate").html();
        String formatString = getDateFormat(RSSAddress);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
        }
        return null;
    }

    private String getNewsLink(Element item) {
        return item.select("link").html();
    }

    private String getNewsContent(String newsLink, Element item) {
        String content;
        String contentClass = getContentClass(newsLink);
        return contentDoc.getElementsByClass(contentClass).text();
    }

    private void getContentDocument(String newsLink) {
        try {
            contentDoc = Jsoup.connect(newsLink).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContentClass(String newsLink) {
//        getContentDocument(newsLink);
//        ConfigController configController = new ConfigController();
//        String contentClass = configController.getConfig(RSSAddress);
//        if (contentClass == null || contentClass.isEmpty()) {
//            contentClass = configController.findConfig(contentDoc);
//        }
        //TODO
        return null;
    }

    private String getDateFormat(String newsLink) {
//        getContentDocument(newsLink);
//        ConfigController configController = new ConfigController();
//        String contentClass = configController.getDateFormat(RSSAddress);
//        if (contentClass == null || contentClass.isEmpty()) {
//            contentClass = configController.findDateFormat(contentDoc);
//        }
        //TODO
        return null;
    }

}
