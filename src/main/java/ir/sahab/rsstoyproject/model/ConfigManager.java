package ir.sahab.rsstoyproject.model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;

public class ConfigManager {
    private static String password;
    private static String user;
    private static ConfigManager instance;
    private Connection databaseConnector;
    ArrayList<String> URLs ;
    private ConfigManager(String user, String password) {
        URLs = new ArrayList<>();
        this.user = user;
        this.password = password;
        try {
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ConfigManager(){}

    public static ConfigManager getInstance(String user, String password) {
        if (instance == null) {
            instance = new ConfigManager(user, password);
        }
        return instance;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public void addConfig(String site, String contentClass){
        try {
            PreparedStatement databaseStatement = databaseConnector.prepareStatement("insert into Config values(?, ? , ?);");
            databaseStatement.setString(1, site);
            databaseStatement.setString(2, contentClass);
            databaseStatement.setString(3,site.split("/")[2]);
            databaseStatement.executeUpdate();
            URLs.add(site);
        } catch (MySQLIntegrityConstraintViolationException e){
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getConfig(String site) {
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Config where RSSlink = ?");
            query.setString(1, site);
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

    public ArrayList<String> getURLs(){
        try {
            PreparedStatement query = databaseConnector.prepareStatement("select * from Config");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                URLs.add(queryResult.getString("RSSLink"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return URLs;
    }

}
