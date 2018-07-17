package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.news.News;
import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;
import ir.sahab.rsstoyproject.site.SiteDao;
import ir.sahab.rsstoyproject.site.SiteDaoImp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.*;

public class Scraper implements Runnable {
    private Document contentDoc = null;
    private Document RSSDoc = null;
    private Thread thread;
    private String RSSAddress;
    private NewsDao newsDao;
    private SiteDao siteDao;

    public Scraper() {
        newsDao = NewsDaoImp.getInstance("root", "li24v2hk77");
        siteDao = SiteDaoImp.getInstance("root", "li24v2hk77");
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<String> URLs = siteDao.getURLs();
            for(String URL: URLs)
            {
                scrape(URL);
            }
//            siteDao.updateLastsrape(URL);
        }
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
        int ID = 0;
        for (Element item : items) {
            title = getNewsTitle(item);
            date = getNewsDate(item);
            newsLink = getNewsLink(item);
            content = getNewsContent(newsLink);
            site = getNewsSite();
            ID = newsLink.hashCode();
            News news = new News(title, date, newsLink, content, site.hashCode(), ID);
            if (!newsDao.addNews(news)) {
                break;
            }
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

    private String getNewsContent(String newsLink) {
        String contentClass = getContentClass(newsLink);
        return contentDoc.getElementsByClass(contentClass).text();
    }

    private void getContentDocument(String newsLink) {
        try {
            contentDoc = Jsoup.connect(newsLink).get();
        } catch (IOException e) {
            System.out.println("Couldn't load " + newsLink);
            e.printStackTrace();
        }
    }

    private String getContentClass(String newsLink) {
        getContentDocument(newsLink);
        String contentClass = siteDao.getPattern(RSSAddress);
//        if (contentClass == null || contentClass.isEmpty()) {
//            contentClass = configController.findConfig(contentDoc);
//        }
        //TODO
        return contentClass;
    }

    private String getDateFormat(String RSSAddress) {
        String dateFormat = siteDao.getDateFormat(RSSAddress);
        if (dateFormat == null || dateFormat.isEmpty()) {
//            dateFormat = siteDao.findDateFormat(RSSAddress);
        }
        //TODO
        return dateFormat;
    }

}
