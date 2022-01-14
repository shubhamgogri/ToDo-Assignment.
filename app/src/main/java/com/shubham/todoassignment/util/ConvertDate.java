package com.shubham.todoassignment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
    public static String toDate(String date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("MMM d");
        return simpleDateFormat.format(fromtimeStamp(date));
    }

    public static Date fromtimeStamp(String num_date){
        long date_time_stamp = Long.parseLong(num_date);
        return new Date(date_time_stamp);
    }

    public static long toTimeStamp(Date date){
        return date.getTime();
    }
}

