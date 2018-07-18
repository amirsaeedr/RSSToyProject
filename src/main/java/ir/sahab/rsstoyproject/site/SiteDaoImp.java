package ir.sahab.rsstoyproject.site;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SiteDaoImp implements SiteDao {
    private static Logger logger = null;
    private final List<String> dateFormats = null;
    private Queue<String> URLs;
    private static SiteDaoImp instance;
    private Connection databaseConnector;
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "li24v2hk77";

    private SiteDaoImp() {
        try {
            logger = Logger.getLogger(SiteDaoImp.class);
            URLs = new LinkedList<>();
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
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8", USER_NAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("Connection couldn't be made with the database", e);
        }
    }

    public static SiteDaoImp getInstance() {
        if (instance == null) {
            instance = new SiteDaoImp();
        }
        return instance;
    }

    @Override
    public String getPattern(String RSSLink) {
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site where RSSlink = ?");
            query.setString(1, RSSLink);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                return queryResult.getString("contentClass");
            }
            databaseConnector.close();
        } catch (SQLException e) {
            logger.error("Error! Getting pattern for " + RSSLink + " failed", e);
        }
        return null;
    }

    @Override
    public String getDateFormat(String site) {
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site where RSSlink = ?");
            query.setString(1, site);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                return queryResult.getString("dateFormat");
            }
            databaseConnector.close();
        } catch (SQLException e) {
            logger.error("Error! Getting date format for " + site + " failed", e);
        }
        return null;
    }

    @Override
    public Queue<String> getURLs() {
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                URLs.add(queryResult.getString("RSSLink"));
            }
        } catch (SQLException e) {
            logger.error("Error! Getting URLs from database failed ", e);
        }
        return URLs;
    }

    @Override
    public void addSite(String siteURL, String pattern, String datePattern) {
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into Site(site, contentClass) values(?, ?);");
            databaseStatement.setString(1, siteURL);
            databaseStatement.setString(2, pattern);
            databaseStatement.executeUpdate();
            URLs.add(siteURL);
        } catch (MySQLIntegrityConstraintViolationException e) {
            return;
        } catch (SQLException e) {
            logger.error("Error! Couldn't add new website to the database", e);
        }
    }

    @Override
    public String findPattern(String RSSLink) {
        return null;
    }

}
