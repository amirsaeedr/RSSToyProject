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
    private Document rssDoc = null;
    private String rssAddress;
    private NewsDao newsDao;
    private SiteDao siteDao;
    private String url;

    public Scraper(String url, String database) {
        logger = Logger.getLogger(Scraper.class);
        this.url = url;
        this.rssAddress = url;
        newsDao = new NewsDaoImp(database);
        siteDao = new SiteDaoImp(database);
    }

    @Override
    public void run() {
        scrape(url);
    }

    private void scrapeNewsData() {
        Elements items;
        try {
            items = rssDoc.select("item");
        } catch (NullPointerException e) {
            logger.error("Error! Item couldn't be fetched from the RSS", e);
            return;
        }
        String title;
        String content;
        String site;
        String newsLink;
        Date date;
        int id;
        for (Element item : items) {
            title = getNewsTitle(item);
            date = getNewsDate(item);
            newsLink = getNewsLink(item);
            content = getNewsContent(newsLink);
            site = getNewsSite();
            id = newsLink.hashCode();
            News news = new News(title, date, newsLink, content, site.hashCode(), id);
            if (!newsDao.addNews(news)) {
                break;
            }
        }
    }

    private void scrape(String rssLink) {
        try {
            rssAddress = rssLink;
            rssDoc = Jsoup.connect(rssLink).get();
        } catch (java.net.UnknownHostException e) {
            logger.error("Error! Rejected by the website");
            logger.info(e);
            return;
        } catch (IOException e) {
            logger.error("Error! Couldn't Scrape " + rssLink, e);
            return;
        }
        scrapeNewsData();
    }

    public String getNewsSite() {
        return rssAddress.split("/")[2];
    }

    public String getNewsTitle(Element item) {
        return item.select("title").html();
    }

    public Date getNewsDate(Element item) {
        String dateString = item.select("pubDate").html();
        String formatString = getDateFormat(rssAddress, dateString);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            logger.error("Couldn't parse the String date " + dateString + " by format " + formatString, e);
            return new Timestamp(System.currentTimeMillis());
        }
    }

    public String getNewsLink(Element item) {
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

    public String getContentClass(String newsLink) {
        getContentDocument(newsLink);
        return siteDao.getPattern(rssAddress);
    }

    public String getDateFormat(String RSSAddress, String dateString) {
        String dateFormat = siteDao.getDateFormat(RSSAddress);
        SimpleDateFormat simpleDateFormat;
        if (dateFormat == null || dateFormat.isEmpty()) {
            ArrayList<String> formats = siteDao.getDateFormats();
            for (String format : formats) {
                simpleDateFormat = new SimpleDateFormat(format);
                try {
                    simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    logger.error("date format can not be found" + e.getMessage());
                    continue;
                }
                siteDao.setDateFormat(format, RSSAddress);
                return format;
            }
        }
        return dateFormat;
    }

}
