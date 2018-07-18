package ir.sahab.rsstoyproject.database.news;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.database.C3P0DataSource;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NewsDaoImp implements NewsDao {
    private static Logger logger = null;
    private Connection databaseConnector;
    private C3P0DataSource dataSource;
    private static int count = 0;

    public NewsDaoImp() {
        logger = Logger.getLogger(NewsDao.class);
        dataSource = C3P0DataSource.getInstance();
//        databaseConnector = dataSource.getConnection();
    }

    @Override
    public boolean addNews(News news) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into News(title, date, link, content, siteId, NewsId) values(?, ?, ?, ?, ?, ?);");
            databaseStatement.setString(1, news.getTitle());
            databaseStatement.setTimestamp(2, new java.sql.Timestamp(news.getDate().getTime()));
            databaseStatement.setString(3, news.getLink());
            databaseStatement.setString(4, news.getContent());
            databaseStatement.setInt(5, news.getSiteId());
            databaseStatement.setInt(6, news.getNewsId());
            databaseStatement.executeUpdate();
            databaseConnector.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            logger.error("Error! Couldn't add news to the database", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> search(String field, String text) {
        try {
            databaseConnector = dataSource.getConnection();
            ArrayList<String> result = new ArrayList<>();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select title from News where " + field + " like ?;");
            databaseStatement.setString(1, "%" + text + "%");
            ResultSet queryResult = databaseStatement.executeQuery();
            while (queryResult.next()) {
                result.add(queryResult.getString("title"));
            }
            databaseConnector.close();
            return result;
        } catch (SQLException e) {
            logger.error("Error! Search on news table failed", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<String> getLatestNews(String siteName) {
        ArrayList<String> titles = new ArrayList<>();
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select title from News where siteId = ? order by date desc limit 10;");
            databaseStatement.setInt(1, siteName.hashCode());
            ResultSet resultSet = databaseStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
                titles.add(resultSet.getString("title"));
            }
            databaseConnector.close();
        } catch (SQLException e) {
            logger.error("Error! Couldn't fetch top 10 news!", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return titles;
    }

    @Override
    public ArrayList<String> getNewsFromADay(String siteName, String date) {
        ArrayList<String> titles = new ArrayList<>();
        try {
            databaseConnector = dataSource.getConnection();
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
            databaseConnector.close();
        } catch (SQLException | ParseException e) {
            logger.error("Error! Couldn't get news on the specific dates", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return titles;
    }
}
