package ir.sahab.rsstoyproject.query;

import ir.sahab.rsstoyproject.news.News;

import java.sql.PreparedStatement;

public class QueryBuilder implements AddNews {


    @Override
    public String addNewsQuery(News news) {
        String query = "insert into News(\"" + news.getTitle() + "\",\"" + news.getDate().toString() + "\", \"" + news.getLink() + "\", \"" +
                news.getContent() + "\", \"" + news.getSite() + "\");";
        return query;
    }
}
