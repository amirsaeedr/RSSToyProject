package ir.sahab.rsstoyproject.news;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface NewsDao {
    List<News> getNews();
    void addNews(News news);
//    ResultSet get(String query);
    ArrayList<News> search(String field, String text);
}
