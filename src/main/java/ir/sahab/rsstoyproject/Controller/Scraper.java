package ir.sahab.rsstoyproject.Controller;

import ir.sahab.rsstoyproject.model.ConfigManager;
import ir.sahab.rsstoyproject.model.News;
import ir.sahab.rsstoyproject.model.NewsManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Scraper implements Runnable{
    private Document contentDoc = null;
    private Document RSSDoc = null;
    private String RSSAddress;
    private NewsManager newsManager = NewsManager.getInstance();
    private Thread thread;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private void scrape(){
        RSSAddress = "https://www.farsnews.com/rss";
        try {
            RSSDoc = Jsoup.connect(RSSAddress).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getNewsList();
    }

    private void getNewsTitle(News news, Element item){
        String title=item.select("title").html();
        news.setTitle(title);
    }

    private void getNewsAuthor(News news, Element item){
        String author=item.select("author").html();
        news.setAuthor(author);
    }

    private void getNewsDate(News news, Element item){
        String date=item.select("pubDate").html();
        news.setDate(date);
    }

    private void getNewsLink(News news, Element item){
        String link=item.select("link").html();
        news.setLink(link);
    }

    private void getNewsContent(News news,String link ,Element item){
        String content;
        String contentClass = getContentClass(link);
        content = contentDoc.getElementsByClass(contentClass).text();
        news.setContent(content);
    }

    private void getContentDocument(String link){
        try {
            contentDoc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContentClass(String link){
        getContentDocument(link);
        ConfigController configController = new ConfigController();
        String contentClass = configController.getConfig(RSSAddress);
        if (contentClass==null || contentClass==""){
            contentClass = configController.findConfig(contentDoc);
        }
        return contentClass;
    }

    private void getNewsList(){
        Elements items = RSSDoc.select("item");
        News tempNews;
        for (Element item: items) {
            tempNews = new News();
            getNewsTitle(tempNews,item);
            getNewsAuthor(tempNews,item);
            getNewsDate(tempNews,item);
            getNewsLink(tempNews,item);
            getNewsContent(tempNews,tempNews.getLink() ,item);
            tempNews.setSite(RSSAddress);
            newsManager.add(tempNews.getTitle(), tempNews.getDate(), tempNews.getAuthor(), tempNews.getLink(), tempNews.getContent(), tempNews.getSite());

        }
    }

    @Override
    public void run() {
        ConfigManager configManager= ConfigManager.getInstance();
        int id =1;
//        while (true){
           // String RSSLink =configManager.getRSS(id);
        scrape();
        Lock readLock = lock.readLock();
        readLock.lock();
        //System.out.println("finish");
        readLock.unlock();
//            id++;
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void start() {
        thread= new Thread(this,"ScrapThread");
        thread.start();
    }
}
