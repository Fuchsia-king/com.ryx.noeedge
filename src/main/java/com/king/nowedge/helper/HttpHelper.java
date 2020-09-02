package com.king.nowedge.helper;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.wxpay.HttpClientConnectionManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

public class HttpHelper {

	public static ResultDTO<String> post(String url, String data) {

		ResultDTO<String> result = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		httpost.setEntity(new StringEntity(data, "UTF-8"));
		HttpResponse response;
		String str = "";
		try {
			response = httpclient.execute(httpost);

			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			result = new ResultDTO<String>(str);
		} catch (IOException e) {
			result = new ResultDTO<String>("error", e.getMessage());
		} finally {
			httpclient.close();
		}

		return result;
	}

	

	public static ResultDTO<String> post1(String url, String data) {

		ResultDTO<String> result = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		httpost.setEntity(new StringEntity(data, "UTF-8"));
		httpost.setHeader("Content-Type", "text/plain;charset=utf-8");
		HttpResponse response;
		String str = "";
		try {
			response = httpclient.execute(httpost);
			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			result = new ResultDTO<String>(str);
		} catch (IOException e) {
			result = new ResultDTO<String>("error", e.getMessage());
		} finally {
			httpclient.close();
		}

		return result;
	}

	

	public static ResultDTO<String> get(String url) {

		ResultDTO<String> result = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet get = HttpClientConnectionManager.getGetMethod(url);
		HttpResponse response;
		String str = "";
		try {
			response = httpclient.execute(get);
			str = EntityUtils.toString(response.getEntity(), "UTF-8");
			result = new ResultDTO<String>(str);
		} catch (IOException e) {
			result = new ResultDTO<String>("error", e.getMessage());
		} catch (Exception e) {
			result = new ResultDTO<String>("error", e.getMessage());
		} finally {
			httpclient.close();
		}
		return result;
	}

	public static String post(String url, Map<String, Object> params,  Map<String, String> headers) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		String result = "";
		// 创建httpClient实例
		httpClient = HttpClients.createDefault();
		// 创建httpPost远程连接实例
		HttpPost httpPost = new HttpPost(url);
		// 配置请求参数实例
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
				.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
				.setSocketTimeout(60000)// 设置读取数据连接超时时间
				.build();
		// 为httpPost实例设置配置
		httpPost.setConfig(requestConfig);
		// 设置请求头
		if (null != headers && headers.size() > 0) {
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// 封装post请求参数
		if (null != params && params.size() > 0) {
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();

			// 通过map集成entrySet方法获取entity
			Set<Entry<String, Object>> entrySet = params.entrySet();
			// 循环遍历，获取迭代器
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> mapEntry = iterator.next();
				nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
			}

			// 为httpPost设置封装好的请求参数
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			// httpClient对象执行post请求,并返回响应参数对象
			httpResponse = httpClient.execute(httpPost);
			// 从响应对象中获取响应内容
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != httpResponse) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
