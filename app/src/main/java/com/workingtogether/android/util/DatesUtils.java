package com.workingtogether.android.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatesUtils {

	/**
	 * @return
	 */
	public static String getDateTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault());
		return simpleDateFormat.format(calendar.getTime());
	}

}
