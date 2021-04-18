package com.edsc.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Url {

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String intlist) {
		String[] vet = intlist.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
//		return Arrays.asList(intlist.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

}
