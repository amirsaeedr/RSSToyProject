package ir.sahab.rsstoyproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseManager {
    private static final String NEWS_table_declaration = "NEWS(title varchar(50) not null, date varchar(50) not null, author varchar(50) not null, link varchar(50) not null, content varchar(5000), primary key (link))";

    public DataBaseManager(String password) {
        this.password = password;
    }

    private static String password;
    public static void create_database() {
        try {
            String database_name = "RSS_Database";
            Connection database_connector_initializer = DriverManager.getConnection("jdbc:mysql://localhost", "root", password);
            ResultSet res_initializer = database_connector_initializer.getMetaData().getCatalogs();
            boolean database_exists = false;
            while(res_initializer.next()){
                String catalogs = res_initializer.getString(1);

                if(database_name.equals(catalogs)){
                    database_exists = true;
                    break;
                }
            }
            if(!database_exists) {
                database_connector_initializer.createStatement().execute("Create database " + database_name);
            }
            database_connector_initializer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void create_table() {
        try {
            Connection database_connector = DriverManager.getConnection("jdbc:mysql://localhost/RSS_Database", "root", password);
            Statement database_statement = database_connector.createStatement();
            database_statement.execute("Create table if not exists " + NEWS_table_declaration + ";");
//            database_statement.executeUpdate("drop table if exists NEWS;");
            ResultSet rs = database_connector.getMetaData().getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
            database_connector.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

