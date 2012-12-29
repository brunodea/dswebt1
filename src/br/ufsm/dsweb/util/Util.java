package br.ufsm.dsweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String dateToString(Date date) {
		return SimpleDateFormat.getDateTimeInstance().format(date);
	}
	public static Date stringToDate(String date) {
		Date d = null;
		try {
			d = SimpleDateFormat.getDateTimeInstance().parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
}
