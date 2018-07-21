package ir.sahab.rsstoyproject.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class C3P0DataSource {
    private static C3P0DataSource instance;
    private ComboPooledDataSource comboPooledDataSource;
    private void initializeDatabase(String database) {
        Connection initializerConnection = null;
        Statement initializerStatement = null;
        String commands = null;
        String username = null;
        String password = null;
        try {
            Properties prop = new Properties();
            InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("SQL-Config.properties");
            try {
                prop.load(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            username = prop.getProperty("Username");
            password = prop.getProperty("Password");
            commands = prop.getProperty("Commands");
            initializerConnection = DriverManager.getConnection("jdbc:mysql://localhost/?", username, password);
            initializerStatement = initializerConnection.createStatement();
            initializerStatement.executeUpdate("CREATE DATABASE if not exists " + database + " CHARACTER SET utf8 COLLATE utf8_general_ci;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            initializerConnection = DriverManager.getConnection("jdbc:mysql://localhost/" + database, username, password);
            initializerStatement = initializerConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (String command : commands.split(";")) {
                initializerStatement.executeUpdate(command);
            }
        } catch (MySQLIntegrityConstraintViolationException ignored) {
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private C3P0DataSource(String database) {
        initializeDatabase(database);
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

    public static C3P0DataSource getInstance(String database) {
        if (instance == null) {
            instance = new C3P0DataSource(database);
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
