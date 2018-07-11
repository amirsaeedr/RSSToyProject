package ir.sahab.rsstoyproject.model;

import java.sql.*;

public class TemplateManager extends DatabaseManager {
    private static TemplateManager instance;
    private TemplateManager(String user, String password) {
        this.user = user;
        this.password = password;
        createDatabase();
        this.TableDeclaration = "TEMPLATE(site varchar(50), class varchar(50)), primary key class";
        this.TableName = "Template";
    }
    private TemplateManager(){}
    public static TemplateManager getInstance(String user, String password) {
        if (instance == null) {
            instance = new TemplateManager(user, password);
        }
        return instance;
    }
    public static TemplateManager getInstance() {
        if (instance == null) {
            instance = new TemplateManager();
        }
        return instance;
    }
}
