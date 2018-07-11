package ir.sahab.rsstoyproject.Controller;

import ir.sahab.rsstoyproject.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContentFinder {
    public void getContent(String link){
        String content;
        Document HtmlDoc = null;
        try {
            HtmlDoc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tags = HtmlDoc.getAllElements();
        for(Element tag : tags){
            System.out.println(tag.tag() + " " + tag.text());
        }
        return;

    }
}
