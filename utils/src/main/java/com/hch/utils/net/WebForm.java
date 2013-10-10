package com.hch.utils.net;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 7/10/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebForm {
    public static final String METHOD_GET = "get";
    public static final String METHOD_POST = "post";

    public static final String STR_METHOD = "method";
    public static final String STR_ACTION = "action";

    private String method;
    private String url;
    private Map<String, String> postDataPairs = new HashMap<String, String>();


    public void addPostDataPair(String key, String val) {
        postDataPairs.put(key, val);
    }

    public void deleteDataPair(String key) {
        postDataPairs.remove(key);
    }

    public List<NameValuePair> getPostDataPairs() {
        List<NameValuePair> lst = new ArrayList<NameValuePair>();

        for (String key : postDataPairs.keySet()) {
            BasicNameValuePair pair = new BasicNameValuePair(key, postDataPairs.get(key));
            lst.add(pair);
        }

        return lst;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
