package ir.sahab.rsstoyproject.console.command;

import ir.sahab.rsstoyproject.console.validation.CommandSizeValidation;
import ir.sahab.rsstoyproject.console.validation.DateValidation;
import ir.sahab.rsstoyproject.console.validation.Validation;
import ir.sahab.rsstoyproject.console.validation.WebsiteLinkValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryCommand extends Command {

    public HistoryCommand() {
    }

    @Override
    public void execute(String[] args) {
        int length = 4;
        Map<String, Validation> validators = new HashMap<>();
        validators.put("Site name", new WebsiteLinkValidation());
        validators.put("size", new CommandSizeValidation());
        validators.put("date", new DateValidation());
        try {
            validators.get("size").validate(args, length);
            validators.get("site name").validate(args, Parts.SITENAME.ordinal());
            validators.get("date").validate(args, Parts.DATE.ordinal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        ArrayList<String> titles = new ArrayList<>();
        titles = newsDao.getNewsFromADay(args[Parts.SITENAME.ordinal()], args[Parts.DATE.ordinal()], Integer.valueOf(args[Parts.LENGTH.ordinal()]));
        for (String title : titles) {
            System.out.println(title);
        }

    }

    private enum Parts {
        COMMAND, SITENAME, DATE, LENGTH;
    }
}

