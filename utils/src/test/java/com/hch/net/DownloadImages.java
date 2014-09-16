package com.hch.net;

import com.hch.utils.io.FileUtils;
import com.hch.utils.net.HttpEngine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by huaiwang on 09/16/2014.
 */
public class DownloadImages {
    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpEngine eng = new HttpEngine();
        eng.get("http://personal.crocodoc.com/6QrhluT?embedded=true");
        String html = eng.getHtml();
        Document doc = Jsoup.parse(html);
        String baseUrl = "http://crocodoc-public.s3.amazonaws.com/" +
                "c5b968ea-3c20-4ebe-8d17-ff0594dac377/images/";
        Elements divs = doc.select("div[pagenum]");
        for (Element div : divs) {
            String pagenum = div.attr("pagenum");
            String pn = String.format("%03d", Integer.valueOf(pagenum));
            String url = baseUrl + "page-" + pn + ".png";
            System.out.println(url);
            eng.get(url);
            InputStream is = eng.getInputStream();
            FileUtils.saveAs(is, pn + ".png");
        }
    }
}
