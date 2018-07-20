package ir.sahab.rsstoyproject.site;

import ir.sahab.rsstoyproject.database.site.SiteDaoImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class SiteDaoImpTest {
    private static SiteDaoImp siteDaoImpTest;

    @BeforeClass
    public static void initialize(){
//        siteDaoImpTest = SiteDaoImp();
    }

    @Test
    public void getPattern() {
        String RSSLink = "https://www.mashreghnews.ir/rss";
        assertEquals("item-text", siteDaoImpTest.getPattern(RSSLink));
    }

    @Test
    public void getDateFormat() {
        String RSSLink = "http://www.tabnak.ir/fa/rss/allnews";
        assertEquals("dd MMM yyyy HH:mm:ss", siteDaoImpTest.getDateFormat(RSSLink));
    }

    @Test
    public void getURLs() {
        Queue<String> URLs = new LinkedList<>();
        URLs.add("https://www.farsnews.com/rss");
        URLs.add("https://www.mashreghnews.ir/rss");
        URLs.add("http://www.tabnak.ir/fa/rss/allnews");
        assertEquals(URLs, siteDaoImpTest.getUrls());
    }

    @Test
    public void getPatternNullResult() {
        String RSSLink = "http://www.varzesh3.com/rss/all";
        assertEquals(null, siteDaoImpTest.getPattern(RSSLink));
    }
}