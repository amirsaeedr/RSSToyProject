package ir.sahab.rsstoyproject.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


public class C3P0DataSource {
    private static C3P0DataSource instance;
    private ComboPooledDataSource comboPooledDataSource;
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "1375109";

    private C3P0DataSource() {
        comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/RSSDatabase?useUnicode=yes&characterEncoding=UTF-8");
            comboPooledDataSource.setUser(USER_NAME);
            comboPooledDataSource.setPassword(PASSWORD);
            comboPooledDataSource.setMinPoolSize(5);
            comboPooledDataSource.setAcquireIncrement(5);
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMaxStatements(200);
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
