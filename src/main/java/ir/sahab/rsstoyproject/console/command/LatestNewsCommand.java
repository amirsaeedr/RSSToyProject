package ir.sahab.rsstoyproject.console.command;

import java.util.ArrayList;

public class LatestNewsCommand extends Command {
    @Override
    public void execute(String[] args) {
        System.out.println(args[Parts.SITENAME.ordinal()]);
        ArrayList<String> titles = newsDao.getLatestNews(args[Parts.SITENAME.ordinal()]);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    private enum Parts {
        COMMAND, SITENAME;
    }
}
