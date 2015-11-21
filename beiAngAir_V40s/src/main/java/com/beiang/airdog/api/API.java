package com.beiang.airdog.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.beiang.airdog.net.BANetUtil;

/***
 * 
 * 通用API 公共操作接口
 * 
 * @author LSD
 * 
 */
public class API {
	public static final String HostName = "smart.99.com";
	public static String WLHost = "121.207.243.64";
	public static final int WLPort = 6066;

	// public static final String AppAuthUrl = "http://www.airdog.cn/auth?";
	public static final String AppAuthUrl = "http://www.airdog.cn/download?";

	public static final String AppAdConfogUrl = "http://121.207.243.132:8082/appIndex/beiang/";

	/***
	 * 获取微信Token
	 * 
	 * @param context
	 * @param appid
	 * @param secret
	 * @param code
	 * @param listener
	 * @param errorListener
	 */
	public static void getWinxinToken(Context context, String appid, String secret, String code, Listener<String> listener,
			ErrorListener errorListener) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("appid", appid);
		parmas.put("secret", secret);
		parmas.put("code", code);
		parmas.put("grant_type", "authorization_code");
		BANetUtil.get(context, url, parmas, listener, errorListener);
	}

}
