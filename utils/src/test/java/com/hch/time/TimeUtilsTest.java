package com.hch.time;

import com.hch.utils.time.TimeUtils;
import org.junit.*;
import org.junit.Assert.*;

/**
 * User: huaiwang
 * Date: 13-11-7
 * Time: 上午10:00
 */
public class TimeUtilsTest {
    @Test
    public void testSuccess() {
        String str = "12h";
        long l = TimeUtils.timeGap(str);
        Assert.assertEquals("12h", 12 * 60 * 60 * 1000, l);
    }
}
