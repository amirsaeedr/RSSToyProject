package ir.sahab.rsstoyproject.news;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.model.NewsManager;
import ir.sahab.rsstoyproject.query.AddNews;
import ir.sahab.rsstoyproject.query.QueryBuilder;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsDaoImp implements NewsDao {
    private static NewsDaoImp instance;
    private Connection databaseConnector;
    private Statement databaseStatement;
    private NewsDaoImp(){}

    private NewsDaoImp(String user, String password) {
        try {
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8", user, password);
            databaseStatement = databaseConnector.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static NewsDaoImp getInstance(String user, String password) {
        if (instance == null) {
            instance = new NewsDaoImp(user, password);
        }
        return instance;
    }

    public static NewsDaoImp getInstance() {
        if (instance == null) {
            instance = new NewsDaoImp();
        }
        return instance;
    }
    @Override
    public List<News> getNews() {
        return null;
    }

    @Override
    public void addNews(String title, Date date, String link, String content, String site) {
        try {
            News news = new News(title, date, link, content, site);
            AddNews addNewsImp = new QueryBuilder();
            Statement DatabaseStatement = databaseConnector.createStatement();
            DatabaseStatement.executeUpdate(addNewsImp.addNewsQuery(news));
        } catch (MySQLIntegrityConstraintViolationException e){
            return;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
