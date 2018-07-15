package ir.sahab.rsstoyproject.news;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface NewsDao {
    List<News> getNews();
    void addNews(String title, Date date, String link, String content, String site);
}
