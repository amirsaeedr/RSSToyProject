package ir.sahab.rsstoyproject.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class C3P0DataSource {
    private static C3P0DataSource instance;
    private ComboPooledDataSource comboPooledDataSource;
    private static String username;
    private static String password;
    private static String database;


    private C3P0DataSource() {
        comboPooledDataSource = new ComboPooledDataSource();
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("../RSSToyProject/src/main/resources/databaseConfig.properties"));
            database = prop.getProperty("Database");
            username = prop.getProperty("Username");
            password = prop.getProperty("Password");
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/" + database + "?useUnicode=yes&characterEncoding=UTF-8");
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
            comboPooledDataSource.setMinPoolSize(5);
            comboPooledDataSource.setAcquireIncrement(5);
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMaxStatements(200);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            //TODO
            System.out.println(e.getMessage());
        }
    }

    public static C3P0DataSource getInstance() {
        if (instance == null) {
            instance = new C3P0DataSource();
        }
        return instance;
    }
    public Connection getConnection(){
        try {
            return this.comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
