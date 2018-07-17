package ir.sahab.rsstoyproject.site;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteDaoImp implements SiteDao {
    private final List<String> dateFormats = null;
    private ArrayList<String> URLs;
    private static SiteDaoImp instance;
    private Connection databaseConnector;

    private SiteDaoImp() {
    }

    public SiteDaoImp(String user, String password) {
        try {
            URLs = new ArrayList<>();
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
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SiteDaoImp getInstance(String user, String password) {
        if (instance == null) {
            instance = new SiteDaoImp(user, password);
        }
        return instance;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getURLs() {
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                URLs.add(queryResult.getString("RSSLink"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return URLs;
    }

    @Override
    public String getRSSURL() {
        String URL = null;
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("select site from Site order by updateDate asc limit 1;");
            ResultSet resultSet = databaseStatement.executeQuery();
            URL = resultSet.getString("site");
        } catch (MySQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return URL;
    }

    @Override
    public void addSite(String siteURL, String pattern, String datePattern) {
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into Site(site, contentClass) values(?, ?);");
            databaseStatement.setString(1, siteURL);
            databaseStatement.setString(2, pattern);
//            databaseStatement.setString(3,site.getName());
            databaseStatement.executeUpdate();
            URLs.add(siteURL);
        } catch (MySQLIntegrityConstraintViolationException e) {
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findPattern(String RSSLink) {
        return null;
    }
}
