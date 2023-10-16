package de.glowman554.bungeelang.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils
{
	public static String get(String url) throws IOException
	{

		OkHttpClient client = new OkHttpClient();

		var req = new Request.Builder();

		req.url(url);

		req.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		Response res = client.newCall(req.build()).execute();

		assert res.body() != null;
		String body = res.body().string();

		res.close();

		return body;
	}
}
