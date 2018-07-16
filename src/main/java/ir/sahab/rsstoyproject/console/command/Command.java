package ir.sahab.rsstoyproject.console.command;

import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;
import ir.sahab.rsstoyproject.site.SiteDao;
import ir.sahab.rsstoyproject.site.SiteDaoImp;


public abstract class Command {
    protected static NewsDao newsDao;
    protected static SiteDao siteDao;

    public Command(String user, String databasePassword) {
        newsDao = NewsDaoImp.getInstance(user, databasePassword);
        siteDao = SiteDaoImp.getInstance(user, databasePassword);
    }

    public Command() {
    }

    public abstract void execute(String[] Args);
}
