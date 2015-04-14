package com.moderco.utility;

import org.apache.http.cookie.Cookie;

public class CookieHandler {
	
	public final static String COOKIE = "com.moderco.moder.MESSAGE";

	public static String formatCookie(Cookie cookie) {
		return "Unique_ID=" + cookie.getValue();
	}
}
