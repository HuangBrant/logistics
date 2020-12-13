package com.example.logistics.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TimeUtil {

    public static String toString(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date toDate(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            log.info("time error:"+e);
        }
        return new Date();
    }

    public static Date toDate(String time,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            log.info("time error:"+e);
        }
        return new Date();
    }

    public static int betweenDay(Date startTime, Date endTime){
        if (null!=startTime || null!=endTime){
            return 0;
        }
        long start = startTime.getTime();
        long end = endTime.getTime();
        long one = 24L * 60L * 60L * 1000L;
        long day = (end - start) / one;
        return (int)day;
    }
}
