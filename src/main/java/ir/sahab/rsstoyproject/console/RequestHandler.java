package ir.sahab.rsstoyproject.console;

import asg.cliche.Command;
import asg.cliche.Param;
import ir.sahab.rsstoyproject.news.NewsDao;
import ir.sahab.rsstoyproject.news.NewsDaoImp;
import ir.sahab.rsstoyproject.site.SiteDao;
import ir.sahab.rsstoyproject.site.SiteDaoImp;

import java.util.ArrayList;

public class RequestHandler {
    private NewsDao newsDao;
    private SiteDao siteDao;

    public RequestHandler() {
        newsDao = NewsDaoImp.getInstance();
        siteDao = SiteDaoImp.getInstance();
    }

    @Command(description = "news count of rss link for a day")
    public void countOfNews(@Param(name = "webSiteAddress") String webSiteLink, @Param(name = "date") String date) {
        ArrayList<String> titles = new ArrayList<>();
        titles = newsDao.getNewsFromADay(webSiteLink, date);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    @Command(description = "get latest news of rss")
    public void LatestNews(@Param(name = "webSiteName") String webSiteName) {
        ArrayList<String> titles = newsDao.getLatestNews(webSiteName);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    @Command(description = "add new website")
    public void addNewSite(@Param(name = "RSSLink") String RSSLink, @Param(name = "pattern") String pattern) {
        String datePattern = null;
        siteDao.addSite(RSSLink, pattern, datePattern);
    }

    @Command(description = "search in title and text")
    public void search(@Param(name = "type") String type, @Param(name = "text") String text) {
        ArrayList<String> titles = newsDao.search(type, text);
        for (String title : titles) {
            System.out.println(title);
        }
    }

    @Command(description = "get new news")
    public void refresh() {
        notifyAll();
    }
}
