package ir.sahab.rsstoyproject.database.site;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.database.C3P0DataSource;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SiteDaoImp implements SiteDao {
    private static Logger logger = null;
    private Connection databaseConnector;
    private C3P0DataSource dataSource;

    public SiteDaoImp() {
        dataSource = C3P0DataSource.getInstance();
        logger = Logger.getLogger(SiteDaoImp.class);
    }


    @Override
    public String getPattern(String rssLink) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("select * from Site where RSSlink = ?");
            query.setString(1, rssLink);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                String pattern = queryResult.getString("contentClass");
                databaseConnector.close();
                return pattern;
            }
        } catch (SQLException e) {
            logger.error("Error! Getting pattern for " + rssLink + " failed", e);
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
    public Queue<String> getUrls() {
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
    public void addSite(String siteUrl, String pattern) {
        try {
            String site = siteUrl.split("/")[2];
            databaseConnector = dataSource.getConnection();
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into Site(RSSLink, contentClass, site, siteId) values(?, ?, ?, ?);");
            databaseStatement.setString(1, siteUrl);
            databaseStatement.setString(2, pattern);
            databaseStatement.setString(3, site);
            databaseStatement.setInt(4, site.hashCode());
            databaseStatement.executeUpdate();
            databaseConnector.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            return;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public ArrayList<String> getDateFormats() {
        ArrayList<String> formats = new ArrayList<>();
        String dateFormat;
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("select * from DateFormat ");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                dateFormat = queryResult.getString("Format");
                formats.add(dateFormat);
            }
            databaseConnector.close();
            return formats;
        } catch (SQLException e) {
            logger.error("Error! Getting date formats");
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
    public void setDateFormat(String format, String rssLink) {
        try {
            databaseConnector = dataSource.getConnection();
            PreparedStatement query = databaseConnector.prepareStatement("update Site set dateFormat = ? where RSSLink = ?");
            query.setString(1, format);
            query.setString(2, rssLink);
            int queryResult = query.executeUpdate();
        } catch (SQLException e) {
            logger.error("can't set time format");
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

}
