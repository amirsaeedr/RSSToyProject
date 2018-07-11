package ir.sahab.rsstoyproject.model;


import java.sql.*;

public class NewsManager extends DatabaseManager {
    private static NewsManager instance;
    private NewsManager (){}
    private NewsManager(String user, String password) {
        this.user = user;
        this.password = password;
        createDatabase();
        this.TableDeclaration = "NEWS(title varchar(100) not null, date varchar(50) not null," +
                " author varchar(50) not null, link varchar(50) not null, content varchar(5000), primary key (link))";
        this.TableName = "NEWS";
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
}
