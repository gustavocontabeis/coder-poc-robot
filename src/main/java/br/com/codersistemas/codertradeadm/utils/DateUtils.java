package br.com.codersistemas.codertradeadm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

	public static Date parseTimestampBR(String value) throws ParseException {
		return StringUtils.isNotBlank(value) ? new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(value) : null;
	}

	public static Date parseDateBR(String value) throws ParseException {
		return StringUtils.isNotBlank(value) ? new SimpleDateFormat("dd/MM/yyyy").parse(value) : null;
	}

	public static Date parseTimeBR(String value) throws ParseException {
		return StringUtils.isNotBlank(value) ? new SimpleDateFormat("hh:mm:ss").parse(value) : null;
	}

}
