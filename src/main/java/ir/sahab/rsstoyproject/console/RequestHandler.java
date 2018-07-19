package ir.sahab.rsstoyproject.console;

import asg.cliche.Command;
import asg.cliche.Param;
import ir.sahab.rsstoyproject.database.news.NewsDao;
import ir.sahab.rsstoyproject.database.news.NewsDaoImp;
import ir.sahab.rsstoyproject.database.site.SiteDao;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RequestHandler {
    private NewsDao newsDao;
    private SiteDao siteDao;

    public RequestHandler() {
        newsDao = new NewsDaoImp();
        siteDao = new SiteDaoImp();
    }

    @Command(description = "news count of rss link for a day")
    public void countOfNews(@Param(name = "webSiteAddress") String webSiteLink, @Param(name = "date") String date) {
        websiteLinkValidation(webSiteLink);
        System.out.println(newsDao.getNewsFromADay(webSiteLink, date));

    }

    @Command(description = "get latest news of rss")
    public void LatestNews(@Param(name = "webSiteLink") String webSiteLink) {
        websiteLinkValidation(webSiteLink);
        ArrayList<String> titles = newsDao.getLatestNews(webSiteLink);
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

    private void websiteLinkValidation(String websiteLink) {
        Pattern pattern = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$");
        if (!pattern.matcher(websiteLink).matches()) {
            System.out.println("website is not valid");
        }
    }
}
