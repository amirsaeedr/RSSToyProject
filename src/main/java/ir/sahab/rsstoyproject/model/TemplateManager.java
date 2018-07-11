package ir.sahab.rsstoyproject.model;

import java.sql.*;

public class TemplateManager  {
    private static String password;
    private static String user;
    private static TemplateManager instance;
    Connection DatabaseConnector;
    Statement DatabaseStatement;

    private TemplateManager(String user, String password) {
        this.user = user;
        this.password = password;
        try {
            DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", user, password);
            DatabaseStatement = DatabaseConnector.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        createDatabase();
//        this.TableDeclaration = "TEMPLATE(site varchar(50), contentClass varchar(50)), primary key (contentClass));";
//        this.TableName = "Template";
//        createTable();
    }
    private TemplateManager(){}
    public static TemplateManager getInstance(String user, String password) {
        if (instance == null) {
            instance = new TemplateManager(user, password);
        }
        return instance;
    }
    public static TemplateManager getInstance() {
        if (instance == null) {
            instance = new TemplateManager();
        }
        return instance;
    }
    public void add(String site, String contentClass){
        try {
            PreparedStatement DatabaseStatement = DatabaseConnector.prepareStatement("insert into TEMPLATE values(?, ?);");
            DatabaseStatement.setString(1, site);
            DatabaseStatement.setString(2, contentClass);
            DatabaseStatement.executeUpdate();
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
