package ir.sahab.rsstoyproject.console.command;

import java.util.ArrayList;

public class HistoryCommamd extends Command {

    public HistoryCommamd() {
    }

    @Override
    public void execute(String[] args) {
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

