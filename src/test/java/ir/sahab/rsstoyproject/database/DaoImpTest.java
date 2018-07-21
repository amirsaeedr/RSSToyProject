package ir.sahab.rsstoyproject.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DaoImpTest {
    protected static void createDatabaseTest() {
        Connection initializerConnection = null;
        Statement initializerStatement = null;
        String commands = null;
        String username = null;
        String password = null ;
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
            initializerStatement.executeUpdate("CREATE DATABASE if not exists RSSDatabaseTest CHARACTER SET utf8 COLLATE utf8_general_ci;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            initializerConnection = DriverManager.getConnection("jdbc:mysql://localhost/RSSDatabaseTest", username, password);
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
}
