package ir.sahab.rsstoyproject.Controller;

import org.jsoup.nodes.Document;
import ir.sahab.rsstoyproject.model.ConfigManager;

import java.sql.*;

public class ConfigController {
    private ConfigManager configmanager = ConfigManager.getInstance();
    public String getConfig(String site) {
        String config = configmanager.getConfig(site);
        return config;
    }
    public String findConfig(Document news){

        return null;
    }

    public String getDateFormat(String site) {
        String dateFormat = configmanager.getDateFormat(site);
        return dateFormat;
    }
    public String findDateFormat(Document news){

        return null;
    }
}
