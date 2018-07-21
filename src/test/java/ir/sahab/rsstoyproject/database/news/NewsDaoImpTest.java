package ir.sahab.rsstoyproject.database.news;

import ir.sahab.rsstoyproject.database.DaoImpTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.*;

public class NewsDaoImpTest extends DaoImpTest {
    private static String[] titles;
    private static String[] contents;
    private static int[] siteIds;
    private static String[] links;
    private static NewsDaoImp newsDaoImpTest;

    private int createRows()
    {
        int counter = 0;
        for (int i = 0; i < 3; i++){
            News news = new News(titles[i], new Date(), links[i], contents[i], siteIds[i], links[i].hashCode());
            if(newsDaoImpTest.addNews(news)){
                counter++;
            }
        }
        return counter;
    }

    @BeforeClass
    public static void initialize(){
        createDatabaseTest();
        titles = new String[]{"خبر فوری 1", "خبر فوری 2", "خبر فوری 3"};
        contents = new String[]{"این متن خبر اول است!", "خبر دوم هیچ ارزشی ندارد!", "متن این خبر بسیار طولانی است! اما باز هم هیچ ارزشی ندارد!"};
        siteIds= new int[]{361321584, 447849981, 447849981};
        links = new String[]{"link1", "link2", "link3"};
        newsDaoImpTest = new NewsDaoImp("RSSDatabaseTest");
        newsDaoImpTest.executeUpdate("delete from News;");
    }

    @Test
    public void addNews() {
        assertEquals(3, createRows());
    }

    @Test
    public void searchOnTitle() {
        createRows();
        ArrayList<String> result = new ArrayList<>();
        String searchString = "فوری";
        result.add( "خبر فوری 1" + "\n and the link is: link1");
        result.add( "خبر فوری 2"  + "\n and the link is: link2");
        result.add("خبر فوری 3"  + "\n and the link is: link3");
        assertEquals(result, newsDaoImpTest.search("title", searchString ));
    }

    @Test
    public void searchOnContent(){
        createRows();
        String searchString = "هیچ";
        assertEquals(2, newsDaoImpTest.search("content", searchString ).size());
    }

    @Test
    public void getNewsFromADay(){
        createRows();
        Date cal = Calendar.getInstance().getTime();
        SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = standardFormat.format(cal);
        assertEquals(2, newsDaoImpTest.getNewsFromADay("www.tabnak.ir", date));
    }

    @AfterClass
    public static void finish(){
        newsDaoImpTest.executeUpdate("delete from News;");
    }


}
