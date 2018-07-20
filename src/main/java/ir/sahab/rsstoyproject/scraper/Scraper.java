package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.database.news.News;
import ir.sahab.rsstoyproject.database.news.NewsDao;
import ir.sahab.rsstoyproject.database.news.NewsDaoImp;
import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

public class Scraper implements Runnable {
    private static Logger logger = null;
    private Document contentDoc = null;
    private Document RSSDoc = null;
    private Thread thread;
    private String RSSAddress;
    private NewsDao newsDao;
    private SiteDao siteDao;
    private String URL;

    public Scraper(String URL) {
        logger = Logger.getLogger(Scraper.class);
        this.URL = URL;
        newsDao = new NewsDaoImp();
        siteDao = new SiteDaoImp();
    }

    @Override
    public void run() {
        scrape(URL);
    }

    private void scrapeNewsData() {
        Elements items = null;
        try {
            items = RSSDoc.select("item");
        } catch (NullPointerException e) {
            logger.error("Error! Item couldn't be fetched from the RSS", e);
            return;
        }
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
        }
    }

    private void scrape(String RSSLink) {
        try {
            RSSAddress = RSSLink;
            RSSDoc = Jsoup.connect(RSSLink).get();
        } catch (java.net.UnknownHostException e) {
            logger.error("Error! Rejected by the website");
            logger.info(e);
            return;
        } catch (IOException e) {
            //TODO
            logger.error("Error! Couldn't Scrape " + RSSLink, e);
            return;
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
        String formatString = getDateFormat(RSSAddress, dateString);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            logger.error("Couldn't parse the String date " + dateString + " by format " + formatString, e);
            return new Timestamp(System.currentTimeMillis());
        }
    }

    private String getNewsLink(Element item) {
        return item.select("link").html();
    }

    private String getNewsContent(String newsLink) {
        String contentClass = getContentClass(newsLink);
        try {
            return contentDoc.getElementsByClass(contentClass).text();
        } catch (NullPointerException e) {
            logger.error("could find content");
            logger.info(e);
        }
        return null;
    }

    private void getContentDocument(String newsLink) {
        try {
            contentDoc = Jsoup.connect(newsLink).get();
        } catch (IOException e) {
            logger.error("Error! Couldn't fetch news link!", e);
            logger.info(e);
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

    private String getDateFormat(String RSSAddress, String dateString) {
        String dateFormat = siteDao.getDateFormat(RSSAddress);
        SimpleDateFormat simpleDateFormat;
        if (dateFormat == null || dateFormat.isEmpty()) {
            ArrayList<String> formats = siteDao.getDateFormats();
            for (String format : formats) {
                simpleDateFormat = new SimpleDateFormat(format);
                try {
                    simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    logger.error("date format can not be found"+e.getMessage());
                    continue;
                }
                siteDao.setDateFormat(format, RSSAddress);
                return format;
            }
        }
        //TODO
        return dateFormat;
    }

}
