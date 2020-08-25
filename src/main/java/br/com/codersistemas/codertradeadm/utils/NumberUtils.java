package br.com.codersistemas.codertradeadm.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

	public static Object formatBR(BigDecimal valor) {
		NumberFormat instance = NumberFormat.getInstance(new Locale("pt","BR"));
		return instance.format(valor.floatValue());
	}

}
