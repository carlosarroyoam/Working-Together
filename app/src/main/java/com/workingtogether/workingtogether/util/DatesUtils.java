package com.workingtogether.workingtogether.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatesUtils {

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        return dateFormatormat.format(calendar.getTime());
    }

}
