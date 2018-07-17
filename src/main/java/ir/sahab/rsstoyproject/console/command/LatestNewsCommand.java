package ir.sahab.rsstoyproject.console.command;

import ir.sahab.rsstoyproject.console.validation.CommandSizeValidation;
import ir.sahab.rsstoyproject.console.validation.Validation;
import ir.sahab.rsstoyproject.console.validation.WebsiteLinkValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LatestNewsCommand extends Command {
    @Override
    public void execute(String[] args) {
        int length = 2;
        Map<String, Validation> validators = new HashMap<>();
        validators.put("Site name", new WebsiteLinkValidation());
        validators.put("size", new CommandSizeValidation());
        try {
            validators.get("size").validate(args, length);
            validators.get("site name").validate(args, Parts.SITENAME.ordinal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        ArrayList<String> titles = newsDao.getLatestNews(args[Parts.SITENAME.ordinal()]);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    private enum Parts {
        COMMAND, SITENAME;
    }
}
