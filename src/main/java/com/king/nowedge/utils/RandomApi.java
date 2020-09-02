package com.king.nowedge.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author www.zuidaima.com
 */
public class RandomApi {
	public static String getRandom() {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		return afterShuffle.substring(5, 9); // 保留4位
	}
}
