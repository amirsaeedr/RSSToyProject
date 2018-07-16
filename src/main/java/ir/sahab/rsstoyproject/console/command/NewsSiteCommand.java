package ir.sahab.rsstoyproject.console.command;

public class NewsSiteCommand extends Command {
    @Override
    public void execute(String[] args) {
        siteDao.addSite(args[Parts.SITEURL.ordinal()],args[Parts.PATTERN.ordinal()],args[Parts.DATEPATTERN.ordinal()]);
    }
    private enum Parts {
        COMMAND, SITEURL, PATTERN, DATEPATTERN;
    }
}
