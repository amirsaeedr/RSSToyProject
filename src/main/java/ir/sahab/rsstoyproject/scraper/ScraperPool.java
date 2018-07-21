package ir.sahab.rsstoyproject.scraper;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperPool implements Runnable {
    private ExecutorService executor;
    private SiteDao siteDao;

    public ScraperPool(String propertiesFile) {
        initializeDatabase();
        executor = Executors.newFixedThreadPool(10);
        siteDao = new SiteDaoImp(propertiesFile);
    }

    @Override
    public void run() {
        Queue<String> urls = siteDao.getUrls();
        for (String url : urls) {
            executor.execute(new Scraper(url));
        }
    }
    private void initializeDatabase() {
        Connection initializerConnection = null;
        Statement initializerStatement = null;
        String commands = null;
        try {
            Properties prop = new Properties();
            InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("SQL-Config.properties");
            try {
                prop.load(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = prop.getProperty("Username");
            String password = prop.getProperty("Password");
            commands = prop.getProperty("Commands");
            initializerConnection = DriverManager.getConnection("jdbc:mysql://localhost/?", username, password);
            initializerStatement = initializerConnection.createStatement();
            initializerStatement.executeUpdate("CREATE DATABASE if not exists RSSDatabase CHARACTER SET utf8 COLLATE utf8_general_ci;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            initializerConnection = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase", "root", "li24v2hk77");
            initializerStatement = initializerConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for(String command: commands.split(";")){
                initializerStatement.executeUpdate(command);
            }
        } catch (MySQLIntegrityConstraintViolationException ignored) {
        }catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
