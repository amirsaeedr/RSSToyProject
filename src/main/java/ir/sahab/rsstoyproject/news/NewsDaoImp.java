package ir.sahab.rsstoyproject.news;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import jdk.internal.dynalink.beans.StaticClass;

import java.sql.*;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NewsDaoImp implements NewsDao {
    private static NewsDaoImp instance;
    private Connection databaseConnector;
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "1375109";


    private NewsDaoImp() {
        try {
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8", USER_NAME, PASSWORD);
//            databaseStatement = databaseConnector.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public boolean addNews(News news) {
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into News(title, date, link, content, siteId, NewsId) values(?, ?, ?, ?, ?, ?);");
            databaseStatement.setString(1, news.getTitle());
            databaseStatement.setTimestamp(2, new java.sql.Timestamp(news.getDate().getTime()));
            databaseStatement.setString(3, news.getLink());
            databaseStatement.setString(4, news.getContent());
            databaseStatement.setInt(5, news.getSiteId());
            databaseStatement.setInt(6, news.getNewsId());
            databaseStatement.executeUpdate();
        } catch (MySQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public ArrayList<String> search(String field, String text) {
        try {
            ArrayList<String> result = new ArrayList<>();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select title from News where " + field + " like ?;");
            databaseStatement.setString(1, "%" + text + "%");
            ResultSet queryResult = databaseStatement.executeQuery();
            while (queryResult.next()) {
                result.add(queryResult.getString("title"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getLatestNews(String siteName) {
        ArrayList<String> titles = new ArrayList<>();
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select title from News where siteId = ? order by date desc limit 10;");
            databaseStatement.setInt(1, siteName.hashCode());
            ResultSet resultSet = databaseStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
                titles.add(resultSet.getString("title"));
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public ArrayList<String> getNewsFromADay(String siteName, String date) {
        ArrayList<String> titles = new ArrayList<>();
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(standardFormat.parse(date));
            cal.add(Calendar.DATE, 1);
            String lastDay = standardFormat.format(cal.getTime());
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select title from News where siteId = ? and date > ? and date < ?;");
            databaseStatement.setInt(1, siteName.hashCode());
            databaseStatement.setString(2, date);
            databaseStatement.setString(3, lastDay);
            ResultSet resultSet = databaseStatement.executeQuery();
            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        } catch (MySQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return titles;
    }
}
