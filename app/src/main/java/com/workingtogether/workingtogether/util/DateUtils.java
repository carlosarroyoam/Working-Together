package com.workingtogether.workingtogether.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormatormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateTime = dateFormatormat.format(c.getTime());
        return dateTime;
    }
}
