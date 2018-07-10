package ir.sahab.rsstoyproject.Controller;

import ir.sahab.rsstoyproject.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    //strat scraping RSS
    public void start(String URL){
        List<News> newsList;
        Document RSSDoc = null;
        try {
            RSSDoc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newsList = getNewsList(RSSDoc);
    }
    //add news title from item
    private void getNewsTitle(News news, Element item){
        String title=item.select("title").html();
        news.setTitle(title);
    }
    //add news author from item
    private void getNewsAuthor(News news, Element item){
        String author=item.select("author").html();
        news.setAuthor(author);
    }
    //add news date from item
    private void getNewsDate(News news, Element item){
        String date=item.select("pubDate").html();
        news.setDate(date);
    }
    //add news link from item
    private void getNewsLink(News news, Element item){
        String link=item.select("link").html();
        news.setLink(link);
    }
    //get news list from items in RSS
    private List<News> getNewsList(Document RSS){
        List<News> newsList = new ArrayList<>();
        Elements items = RSS.select("item");
        News tempNews;
        for (Element item: items) {
            tempNews = new News();
            getNewsTitle(tempNews,item);
            getNewsAuthor(tempNews,item);
            getNewsDate(tempNews,item);
            getNewsLink(tempNews,item);
            newsList.add(tempNews);
        }
        return newsList;
    }
}
