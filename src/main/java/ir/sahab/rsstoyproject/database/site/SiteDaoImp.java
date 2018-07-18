package ir.sahab.rsstoyproject.database.site;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.database.C3P0DataSource;
import ir.sahab.rsstoyproject.scraper.ScraperPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SiteDaoImp implements SiteDao {
    private static Logger logger = null;
    private final List<String> dateFormats = null;
    private Connection databaseConnector;
    private C3P0DataSource dataSource;

    public SiteDaoImp() {
        dataSource = C3P0DataSource.getInstance();
        logger = Logger.getLogger(SiteDaoImp.class);
        String[] tmpArray = {
                "E, MMM dd yyyy HH:mm:ss",
                "E, dd MMM yyyy HH:mm:ss",
                "MMM dd yyyy HH:mm:ss",
                "E, MMM dd yyyy HH:mm",
                "E, dd MMM yyyy HH:mm:ss",
                "E, dd MMM yyyy HH:mm:ss Z",
                "dd MMM yyyy HH:mm:ss Z",
                "yyyy-MM-dd'T'HH:mm:ss"
        };
    }


    @Override
    public String getPattern(String RSSLink) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site where RSSlink = ?");
            query.setString(1, RSSLink);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                String pattern = queryResult.getString("contentClass");
                databaseConnector.close();
                return pattern;
            }
        } catch (SQLException e) {
            logger.error("Error! Getting pattern for " + RSSLink + " failed", e);
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
    public String getDateFormat(String site) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site where RSSlink = ?");
            query.setString(1, site);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                String dateFormat = queryResult.getString("dateFormat");
                databaseConnector.close();
                return dateFormat;
            }

        } catch (SQLException e) {
            logger.error("Error! Getting date format for " + site + " failed", e);
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
    public Queue<String> getURLs() {
        Queue<String> urls = new LinkedList<>();
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                urls.add(queryResult.getString("RSSLink"));
            }
            databaseConnector.close();
        } catch (SQLException e) {
            logger.error("Error! Getting URLs from database failed ", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return urls;
    }

    @Override
    public void addSite(String siteURL, String pattern, String datePattern) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into Site(site, contentClass) values(?, ?);");
            databaseStatement.setString(1, siteURL);
            databaseStatement.setString(2, pattern);
            databaseStatement.executeUpdate();
            databaseConnector.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            return;
        } catch (SQLException e) {
            logger.error("Error! Couldn't add new website to the database", e);
        } finally {
            if (databaseConnector != null) {
                try {
                    databaseConnector.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public String findPattern(String RSSLink) {
        return null;
    }

}
