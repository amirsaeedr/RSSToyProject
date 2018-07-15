package ir.sahab.rsstoyproject.model;


import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class NewsManager {
    private static NewsManager instance;
    private Connection databaseConnector;
    private Statement databaseStatement;
    private NewsManager (){}

    private NewsManager(String user, String password) {
        try {
            databaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8", user, password);
            databaseStatement = databaseConnector.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static NewsManager getInstance(String user, String password) {
        if (instance == null) {
            instance = new NewsManager(user, password);
        }
        return instance;
    }

    public static NewsManager getInstance() {
        if (instance == null) {
            instance = new NewsManager();
        }
        return instance;
    }

    public synchronized void add(String title, String date, String author, String link, String content, String site){
        try {
            PreparedStatement DatabaseStatement = databaseConnector.prepareStatement("insert into News(title, date, author, link, content, site) values(?, ?, ?, ?, ?, ?);");
            DatabaseStatement.setString(1, title);
            DatabaseStatement.setString(2, date);
            DatabaseStatement.setString(3, author);
            DatabaseStatement.setString(4, link);
            DatabaseStatement.setString(5, content);
            DatabaseStatement.setString(6, site);
            DatabaseStatement.executeUpdate();
        } catch (MySQLIntegrityConstraintViolationException e){
            return;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //fixme
    public synchronized ResultSet get(String query) {
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
