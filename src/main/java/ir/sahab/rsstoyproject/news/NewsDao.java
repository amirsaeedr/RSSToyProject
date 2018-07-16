package ir.sahab.rsstoyproject.news;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface NewsDao {
    List<News> getNews();

    boolean addNews(News news);

    ArrayList<String> search(String field, String text);

    ArrayList<String> getLatestNews(String siteName);

    ArrayList<String> getNewsFromADay(String siteName, String date, int length);
}
