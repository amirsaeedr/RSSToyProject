package ir.sahab.rsstoyproject.console.command;

import org.apache.commons.validator.UrlValidator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatestNewsCommand extends Command {
    @Override
    public void execute(String[] args) {
        Pattern pattern = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$");
        if(pattern.matcher(args[Parts.SITENAME.ordinal()]).matches()) {
            ArrayList<String> titles = newsDao.getLatestNews(args[Parts.SITENAME.ordinal()]);
            for (String title : titles) {
                System.out.println(title);
            }
        }
        else{
            System.out.println("url is not valid");
        }
    }

    private enum Parts {
        COMMAND, SITENAME;
    }
}
