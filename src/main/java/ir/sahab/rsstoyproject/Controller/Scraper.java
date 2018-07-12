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
    private Document contentDoc = null;
    private Document RSSDoc = null;
    public void start(String URL){
        List<News> newsList;
        try {
            RSSDoc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newsList = getNewsList();
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
        content = contentDoc.getElementsByClass("nwstxtmainpane").text();
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
        String contentClass = configController.getConfig(link);
        if (contentClass==null || contentClass==""){
            contentClass = configController.findConfig(contentDoc);
        }
        return contentClass;
    }

    private List<News> getNewsList(){
        List<News> newsList = new ArrayList<>();
        Elements items = RSSDoc.select("item");
        News tempNews;
        for (Element item: items) {
            tempNews = new News();
            getNewsTitle(tempNews,item);
            getNewsAuthor(tempNews,item);
            getNewsDate(tempNews,item);
            getNewsLink(tempNews,item);
            getNewsContent(tempNews,tempNews.getLink() ,item);
            newsList.add(tempNews);
            System.out.println("news title: \t"+tempNews.getTitle());
            System.out.println("news author: \t"+tempNews.getAuthor());
            System.out.println("news link: \t"+tempNews.getLink());
            System.out.println("news date: \t"+tempNews.getDate());
            System.out.println("news content: \t"+tempNews.getContent());
        }
        return newsList;
    }
}
