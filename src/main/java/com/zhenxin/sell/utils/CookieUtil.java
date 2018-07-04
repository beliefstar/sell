package com.zhenxin.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static void setCookie(HttpServletResponse response, String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String get(HttpServletRequest request, String key) {
        Map<String, String> cookieMap = getCookieMap(request);
        return cookieMap.get(key);
    }

    public static void delete(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private static Map<String, String> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = new HashMap<>();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }
        return map;
    }
}
