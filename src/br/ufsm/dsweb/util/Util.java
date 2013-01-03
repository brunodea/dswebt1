package br.ufsm.dsweb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String dateToString(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(date);
	}
	public static Date stringToDate(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
}
