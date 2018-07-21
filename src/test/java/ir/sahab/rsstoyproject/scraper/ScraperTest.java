package ir.sahab.rsstoyproject.scraper;

import ir.sahab.rsstoyproject.database.news.NewsDaoImp;
import ir.sahab.rsstoyproject.database.site.SiteDaoImp;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.text.Document;
import javax.swing.text.Element;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class ScraperTest {
    public static Scraper scraperTest;
    public static org.jsoup.nodes.Document RSSDoc;
    private static Elements items;
    private static org.jsoup.nodes.Element item;

    @BeforeClass
    public static void initialize(){
        scraperTest = new Scraper("http://www.tabnak.ir/fa/rss/allnews");
        String html = "<rss version=\"2.0\"><channel><title>سایت خبری تحلیلی تابناك|اخبار ایران و جهان|TABNAK</title><link>http://www.tabnak.ir</link><description>سايت خبري تحليلي تابناك</description><managingEditor>info@tabnak.ir</managingEditor><webMaster>info@iransamaneh.com</webMaster><lastBuildDate>Sat, 21 Jul 2018 07:05:00 +0430</lastBuildDate><generator>SepehrFeed V1.2</generator><item><title>امید ابراهیمی: الاهلی را با شناخت انتخاب کردم</title><link>\n" +
                "http://www.tabnak.ir/fa/news/818241/امید-ابراهیمی-الاهلی-را-با-شناخت-انتخاب-کردم\n" +
                "</link><author>info@tabnak.ir</author><pubDate>21 Jul 2018 07:01:01 +0430</pubDate></item></channel></rss>";
        RSSDoc = Jsoup.parse(html, "", Parser.xmlParser());
        item = RSSDoc.select("item").get(0);
    }

    @Test
    public void getNewsSiteTest(){
        assertEquals("www.tabnak.ir", scraperTest.getNewsSite());
    }

    @Test
    public void getNewsTitle() {
        assertEquals("امید ابراهیمی: الاهلی را با شناخت انتخاب کردم", scraperTest.getNewsTitle(item));
    }

    @Test
    public void getNewsDateTest() {
        String formatString = "dd MMM yyyy HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            assertEquals(format.parse("21 Jul 2018 07:01:01 +0430"), scraperTest.getNewsDate(item));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getDateFormatTest(){
//        assertEquals("dd MMM yyyy HH:mm:ss", scraperTest.getDateFormat("http://www.tabnak.ir/fa/rss/allnews"));
//    }

    @Test
    public void getContentClass(){
        assertEquals("body", scraperTest.getContentClass("http://www.tabnak.ir/fa/rss/allnews"));
    }

}