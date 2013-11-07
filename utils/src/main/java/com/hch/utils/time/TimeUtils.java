package com.hch.utils.time;

import com.hch.utils.HchException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huaiwang
 * Date: 13-11-7
 * Time: 上午9:02
 */
public class TimeUtils {
    public static final long SecondMilliSeconds = 1000;
    public static final long MinuteMilliSeconds = 60 * SecondMilliSeconds;
    public static final long HourMilliSeconds = 60 * MinuteMilliSeconds;
    public static final long DayMilliSeconds = 24 * HourMilliSeconds;


    // a period of time, e.g., 12h3m2s
    // accept only (d)ay, (h)our, (m)inute, (s)econd,
    // because they stands for a constant period of time,
    // not like month takes 4 different value, 28, 29, 30, 31.
    public static final String TimeGapPattern = "^(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?$";
    public static final Map<Character, Long> TimeMilliSeconds = new HashMap<Character, Long>();

    static {
        TimeMilliSeconds.put('d', DayMilliSeconds);
        TimeMilliSeconds.put('h', HourMilliSeconds);
        TimeMilliSeconds.put('m', MinuteMilliSeconds);
        TimeMilliSeconds.put('s', SecondMilliSeconds);
    }


    public static long timeGap(String timeString) {
        Pattern ptn = Pattern.compile(TimeGapPattern);

        Matcher matcher = ptn.matcher(timeString);

        if (!matcher.find()) throw new HchException("[" + timeString + "] not match " + TimeGapPattern);

        int cnt = matcher.groupCount();

        long ms = 0;

        for (int i = 0; i < cnt; i++) {
            String str = matcher.group(i + 1);
            if (str == null) continue;

            int units = Integer.parseInt(str.substring(0, str.length() - 1));
            char c = str.charAt(str.length() - 1);

            ms += units * TimeMilliSeconds.get(c);
        }

        return ms;
    }
}
