package com.king.nowedge.helper;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Date;

@Component
public class NumberHelper {

	public String formatDouble(Double d) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(d);
	}

	private static Integer seq = 0;

	/**
	 * 
	 * @return
	 */
	public static synchronized Long generateLongId(Integer suffixLength) {
		Date date = new Date();
		if (seq.toString().length() > suffixLength)
			seq = 0;
		String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0" + suffixLength + "d", date, seq++);
		return Long.parseLong(str);
	}

	public static synchronized long longId(Integer suffixLength) {
		Date date = new Date();
		if (seq.toString().length() > suffixLength)
			seq = 0;
		String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0" + suffixLength + "d", date, seq++);
		return Long.parseLong(str);
	}

	public static synchronized String longIdString(Integer suffixLength) {
		Date date = new Date();
		if (seq.toString().length() > suffixLength)
			seq = 0;
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0" + suffixLength + "d", date, seq++);
	}

	/**
	 * 
	 * @param suffixLength
	 * @return
	 */
	public static synchronized String longIdString() {
		return longIdString(6);
	}

	public static synchronized Long generateLongId() {
		return generateLongId(6);
	}

	public static Boolean isZeroOrNull(Double obj) {
		if (null == obj || obj == 0.0) {
			return true;
		}

		return false;
	}

	public static Boolean isZeroOrNull(Integer obj) {
		if (null == obj || obj == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 固定长度随机数
	 * 
	 * @param length
	 * @return
	 */
	public static Integer generateFixLengthRandom(Integer length) {
		return (int) ((Math.random() * 9 + 1) * Math.pow(10, (length - 1)));
	}

	/**
	 * 最大长度随机数
	 * 
	 * @param length
	 * @return
	 */
	public static Integer generateRandom(Integer length) {
		return (int) (Math.random() * Math.pow(10, length));
	}
}
