package com.hch.utils.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by huaiwang on 09/16/2014.
 */
public class HttpEngine2 {
    private static final Logger log = LoggerFactory.getLogger(HttpEngine2.class);

    public String get(String url) throws IOException {
        return get(url, "UTF-8");
    }

    public String get(String url, String encoding) throws IOException {
        URL u = new URL(url);
        URLConnection uconn = u.openConnection();
        uconn.connect();
        InputStream is = uconn.getInputStream();
        ByteArrayOutputStream baos = readContent(is);
        return baos.toString(encoding);
    }

    private ByteArrayOutputStream readContent(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (is == null) {
            log.info("===is is null===");
            return baos;
        }
        int b;
        BufferedInputStream bis = new BufferedInputStream(is);
        while ((b = bis.read()) != -1) {
            baos.write(b);
        }
        return baos;
    }
}
