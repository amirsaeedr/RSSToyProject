package ir.sahab.rsstoyproject.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class C3P0DataSource {
    private static C3P0DataSource instance;
    private ComboPooledDataSource comboPooledDataSource;


    private C3P0DataSource(String database) {
        comboPooledDataSource = new ComboPooledDataSource();
        try {
            Properties prop = new Properties();
            InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("SQL-Config.properties");
            prop.load(resource);
            String username = prop.getProperty("Username");
            String password = prop.getProperty("Password");
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/" + database + "?useUnicode=yes&characterEncoding=UTF-8");
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
            comboPooledDataSource.setMinPoolSize(5);
            comboPooledDataSource.setAcquireIncrement(5);
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMaxStatements(200);
        } catch (IOException | PropertyVetoException e) {
            //TODO
//            System.out.println(e.getMessage());
        }
    }

    public static C3P0DataSource getInstance(String propertiesFile) {
        if (instance == null) {
            instance = new C3P0DataSource(propertiesFile);
        }
        return instance;
    }
    public Connection getConnection(){
        try {
            return this.comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            //TODO
            System.out.println(e.getMessage());
        }
        return null;
    }
}
