package ir.sahab.rsstoyproject.model;

import java.sql.*;

public class TemplateManager extends DatabaseManager {
    public TemplateManager(String user, String password) {
        this.user = user;
        this.password = password;
        createDatabase();
        this.TableDeclaration = "TEMPLATE(site varchar(50), class varchar(50)), primary key class";
        this.TableName = "Template";
    }

}
