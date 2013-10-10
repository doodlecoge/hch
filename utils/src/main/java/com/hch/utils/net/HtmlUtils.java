package com.hch.utils.net;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: huaiwang
 * Date: 13-10-10
 * Time: 下午12:28
 */
public class HtmlUtils {
    public static List<WebForm> getForms(String html) {
        return getForms(html, null);
    }

    /**
     * @param html     html string where to extract forms
     * @param selector Jsoup selector, see
     *                 http://jsoup.org/cookbook/extracting-data/selector-syntax
     *                 for how to write selectors.
     * @return a list of {@link WebForm}
     */
    public static List<WebForm> getForms(String html, String selector) {
        Document doc = Jsoup.parse(html);

        Elements forms = doc.select("form");
        if (selector != null) {
            forms = forms.select(selector);
        }

        List<WebForm> webForms = new ArrayList<WebForm>();

        for (Element form : forms) {
            WebForm webForm = new WebForm();

            webForm.setMethod(WebForm.METHOD_GET);

            if (form.hasAttr(WebForm.STR_METHOD) && WebForm.METHOD_POST.equalsIgnoreCase(form.attr(WebForm.STR_METHOD).trim())) {
                webForm.setMethod(WebForm.METHOD_POST);
            }

            if (form.hasAttr(WebForm.STR_ACTION)) {
                webForm.setUrl(form.attr(WebForm.STR_ACTION));
            } else {
                webForm.setUrl("");
            }


            // for all <input name=... elements
            Elements elements = form.select("input[name]");

            for (Element element : elements) {
                webForm.addPostDataPair(
                        element.attr("name"),
                        element.attr("value")
                );
            }

            // todo: process <selector name=... elements

            webForms.add(webForm);
        }

        return webForms;
    }
}
