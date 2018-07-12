package ir.sahab.rsstoyproject.model;


import java.sql.*;

public class NewsManager {
    private static NewsManager instance;
    private static String password;
    private static String user;
    Connection DatabaseConnector;
    Statement DatabaseStatement;
    private NewsManager (){}
    private NewsManager(String user, String password) {
        this.user = user;
        this.password = password;
        try {
            DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", user, password);
            DatabaseStatement = DatabaseConnector.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        createDatabase();
//        this.TableDeclaration = "NEWS(title varchar(100) not null, date varchar(50) not null," +
//                " author varchar(50), link varchar(50) not null, content varchar(5000), primary key (link));";
//        this.TableName = "NEWS";
//        createTable();
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
    public void add(String title, String date, String author, String link, String content){
        try {
            PreparedStatement DatabaseStatement = DatabaseConnector.prepareStatement("insert into NEWS values(?, ?, ?, ?, ?);");
            DatabaseStatement.setString(1, title);
            DatabaseStatement.setString(2, date);
            DatabaseStatement.setString(3, author);
            DatabaseStatement.setString(4, link);
            DatabaseStatement.setString(5, content);
            int i =DatabaseStatement.executeUpdate();
            System.out.println(i);
            DatabaseConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet get(String query) {
        try {
            ResultSet QueryResult = DatabaseStatement.executeQuery(query);
            DatabaseConnector.close();
            return QueryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
