package com.hch.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 8/19/13
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/hello")
public class HelloAction {
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.put("time", Calendar.getInstance().getTimeInMillis());
        return "hello";
    }
}
