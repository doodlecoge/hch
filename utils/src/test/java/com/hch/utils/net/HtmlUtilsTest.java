package com.hch.utils.net;

import org.junit.Test;

import java.util.List;

/**
 * User: huaiwang
 * Date: 13-10-10
 * Time: 下午1:44
 */

public class HtmlUtilsTest {
    @Test
    public void foo() {
        String html = "" +
                "<form method=post>" +
                "   <input type=\"text\" name=\"username\" />" +
                "   <input type=\"text\" name=\"password\" />" +
                "</form>";


        List<WebForm> forms = HtmlUtils.getForms(html);


        System.out.println(forms.size());

        for (WebForm form : forms) {
            System.out.println(form.getUrl());
            System.out.println(form.getMethod());
        }
    }

}
