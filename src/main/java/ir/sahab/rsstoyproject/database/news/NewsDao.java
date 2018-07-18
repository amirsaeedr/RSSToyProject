package ir.sahab.rsstoyproject.database.news;

import java.util.ArrayList;
import java.util.List;

public interface NewsDao {

    boolean addNews(News news);

    ArrayList<String> search(String field, String text);

    ArrayList<String> getLatestNews(String siteName);

    ArrayList<String> getNewsFromADay(String siteName, String date);
}
