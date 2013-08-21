package com.hch.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 8/21/13
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class JstlAction {
    @RequestMapping("/jstl")
    public String jstl(ModelMap modelMap) {
        Map<String, String> map = new HashMap<String, String>();

        Random random = new Random();
        int len = random.nextInt(10) + 3;

        for (int i = 0; i < len; i++) {
            String key = randomString();
            map.put("k" + key, "v" + key);
        }

        String[] keys = map.keySet().toArray(new String[map.size()]);
        List<String> keyList = Arrays.asList(keys);
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });


        modelMap.put("map", map);
        modelMap.put("keys", keyList);

        return "jstl";
    }

    private static final String Chars = "abcdefghijklmnopqrstuvwxyz";

    public String randomString() {
        int sz = Chars.length();
        Random random = new Random();
        int len = random.nextInt(10) + 5;

        String str = "";

        for (int i = 0; i < len; i++) {
            random.nextInt();
            str += Chars.charAt(random.nextInt(sz));
        }

        return str;
    }
}
