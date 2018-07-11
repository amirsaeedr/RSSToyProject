package ir.sahab.rsstoyproject.model;


import java.sql.*;

public class NewsManager extends DatabaseManager{
    public NewsManager(String user, String password) {
        this.user = user;
        this.password = password;
        createDatabase();
        this.TableDeclaration = "NEWS(title varchar(100) not null, date varchar(50) not null," +
                " author varchar(50) not null, link varchar(50) not null, content varchar(5000), primary key (link))";
        this.TableName = "NEWS";
    }


}
