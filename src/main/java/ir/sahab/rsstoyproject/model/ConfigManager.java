package ir.sahab.rsstoyproject.model;

import java.sql.*;
import java.util.ArrayList;

public class ConfigManager {
    private static String password;
    private static String user;
    private static ConfigManager instance;
    private Connection DatabaseConnector;
    ArrayList<String> URLs ;
    private ConfigManager(String user, String password) {
        URLs = new ArrayList<>();
        this.user = user;
        this.password = password;
        try {
            DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", user, password);
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
            PreparedStatement DatabaseStatement = DatabaseConnector.prepareStatement("insert into Config values(?, ?);");
            DatabaseStatement.setString(1, site);
            DatabaseStatement.setString(2, contentClass);
            DatabaseStatement.executeUpdate();
            URLs.add(site);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getConfig(String site) {
        try {
            PreparedStatement query = DatabaseConnector.prepareStatement("select * from Config where site = ?");
            query.setString(1, site);
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                return queryResult.getString("contentClass");
            }
            DatabaseConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getURLs(){
        try {
            PreparedStatement query = DatabaseConnector.prepareStatement("select * from Config");
            ResultSet queryResult = query.executeQuery();
            while (queryResult.next()) {
                URLs.add(queryResult.getString("site"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return URLs;
    }

}
