package ir.sahab.rsstoyproject.console.command;

import java.util.ArrayList;

public class SearchCommand extends Command {
    @Override
    public void execute(String[] args) {
        //TODO
        ArrayList<String> titles = new ArrayList<>();
        titles = newsDao.search(args[Parts.TYPE.ordinal()], args[Parts.TEXT.ordinal()]);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    private enum Parts {
        COMMAND, TYPE, TEXT
    }
}
