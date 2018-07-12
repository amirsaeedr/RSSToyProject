package ir.sahab.rsstoyproject.Controller;

import org.jsoup.nodes.Document;
import ir.sahab.rsstoyproject.model.ConfigManager;

import java.sql.*;

public class ConfigController {
    private ConfigManager manager = ConfigManager.getInstance();
    public String getConfig(String site) {
        String config = manager.getConfig(site);
        System.out.println(config);
        return config;
    }
    public String findConfig(Document news){

        return null;
    }
}
