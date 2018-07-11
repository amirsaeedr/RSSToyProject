package ir.sahab.rsstoyproject.model;

import java.sql.*;

public abstract class DatabaseManager {
    protected static String password;
    protected static String user;
    protected static String TableDeclaration;
    protected static String TableName;
    public DatabaseManager() {

    }
    protected static void createDatabase() {
        try {
            String database_name = "RSS_Database";
            Connection DatabaseConnectorInitializer = DriverManager.getConnection("jdbc:mysql://localhost", user , password);
            ResultSet ResInitializer = DatabaseConnectorInitializer.getMetaData().getCatalogs();
            boolean database_exists = false;
            while(ResInitializer.next()){
                String catalogs = ResInitializer.getString(1);

                if(database_name.equals(catalogs)){
                    database_exists = true;
                    break;
                }
            }
            if(!database_exists) {
                DatabaseConnectorInitializer.createStatement().execute("Create database " + database_name);
            }
            DatabaseConnectorInitializer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected static void createTable(){
        try {
            Connection DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", user, password);
            Statement DatabaseStatement = DatabaseConnector.createStatement();
            DatabaseStatement.execute("Create table if not exists " + TableDeclaration + ";");
//            database_statement.executeUpdate("drop table if exists NEWS;");
            ResultSet rs = DatabaseConnector.getMetaData().getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
            DatabaseConnector.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected static void add(String values){
        try {
            Connection DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", "root", password);
            Statement DatabaseStatement = DatabaseConnector.createStatement();
            DatabaseStatement.executeUpdate("insert into " + TableName + " values " + values + ";");
            DatabaseConnector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected static ResultSet get(String query){
        try {
            Connection DatabaseConnector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", "root", password);
            Statement DatabaseStatement = DatabaseConnector.createStatement();
            ResultSet QueryResult = DatabaseStatement.executeQuery(query);
            DatabaseConnector.close();
            return QueryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

