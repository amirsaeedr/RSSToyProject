package ir.sahab.rsstoyproject.news;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class NewsDaoImp implements NewsDao {
    private static NewsDaoImp instance;
    private Connection databaseConnector;
    private Statement databaseStatement;
    private NewsDaoImp(){}

    public NewsDaoImp(String user, String password) {
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
    public void addNews(News news) {
        try {
            PreparedStatement DatabaseStatement = databaseConnector.prepareStatement("insert into News(title, date, link, content, site) values(?, ?, ?, ?, ?);");
            DatabaseStatement.setString(1, news.getTitle());
            DatabaseStatement.setTimestamp(2, new java.sql.Timestamp(news.getDate().getTime()));
            DatabaseStatement.setString(3, news.getLink());
            DatabaseStatement.setString(4, news.getContent());
            DatabaseStatement.setString(5, news.getSite());
            DatabaseStatement.executeUpdate();
        } catch (MySQLIntegrityConstraintViolationException e){
            return;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //fixme
    @Override
    synchronized public ResultSet get(String query) {
        try {
            ResultSet queryResult = databaseStatement.executeQuery(query);
            queryResult =databaseStatement.getResultSet();
            while (queryResult.next()){
                System.out.println(queryResult.getString(1));
            }
            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
