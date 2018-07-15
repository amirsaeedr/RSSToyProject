package ir.sahab.rsstoyproject.service;

import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;

import java.util.Date;

public class NewsService {
    private NewsDao newsDao;

    public NewsService() {
        newsDao = NewsDaoImp.getInstance();
    }

    public void addNews(String title, Date date, String newsLink, String content, String site) {
        newsDao.addNews(title, date, newsLink, content, site);
    }
}
