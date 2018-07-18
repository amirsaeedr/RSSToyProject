package ir.sahab.rsstoyproject.news;

import ir.sahab.rsstoyproject.database.news.NewsDaoImp;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class NewsDaoImpTest {
    private static NewsDaoImp newsDaoImpTest;
    private static Connection connection;

    @BeforeClass
    public static void initialize(){
        System.out.println("HERE");
        newsDaoImpTest = new NewsDaoImp();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testspring","root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchOnTitle(){
        ArrayList<String> result = new ArrayList<>();
        String searchString = "جوانان";
        result.add("نرخ بیکاری در جوانان ایران ۲۸ درصد شد");
        assertEquals(result, newsDaoImpTest.search("title", searchString ));
    }

    @Test
    public void searchOnContent(){
        String searchString = "صنایع دستی";
        assertEquals(3, newsDaoImpTest.search("content", searchString ).size());
    }

}