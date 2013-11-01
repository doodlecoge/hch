package com.hch.test;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: huaiwang
 * Date: 13-9-17
 * Time: 下午3:53
 */
public class ThreadTest {
    public static void main(String[] args) {
        Document doc = Jsoup.parse("<A><b><c NaME='a name'>c</c></b><b><d>d</d></b></a>");

        Elements select = doc.select("a").select("b").select("c");

        System.out.println(select.size());

        System.out.println(select);

        System.out.println("[" + select.attr("namea"));
    }
}
