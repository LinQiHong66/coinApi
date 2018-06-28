package com.warrior.virtualcurrency;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lin
 * 
 */
public class HttpUtil {

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            : 请求的连接
	 * @param params
	 *            ： 请求参数，无参时传null
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> params,
			int timeOut) {
		try {
			System.out.println("url:" + url);
			HttpRequest request = HttpRequest.get(url);
			if (params != null) {
				request.query(params);
			}
			HttpResponse response = request.send();
			String respJson = response.bodyText();
			return respJson;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            : 请求的连接
	 * @param params
	 *            ： 请求参数，无参时传null
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> params) {
		HttpRequest request = HttpRequest.get(url);
		if (params != null) {
			request.query(params);
		}
		HttpResponse response = request.send();
		String respJson = response.bodyText();
		return respJson;
	}

	/**
	 * 发送json-Post请求
	 * 
	 * @param url
	 *            : 请求的连接
	 * @param params
	 *            ： 请求参数，无参时传null
	 * 
	 * @return
	 */
	public static String sendPost(String url, Map<String, Object> params) {
		HttpRequest request = HttpRequest.post(url);
		request.contentType("application/json");
		request.charset("utf-8");

		// 参数详情
		if (params != null) {
			request.body(JSON.toJSONString(params));
		}

		HttpResponse response = request.send();
		String respJson = response.bodyText();
		return respJson;
	}

	/**
	 * 普通post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPost_common(String url, Map<String, Object> params) {
		HttpRequest request = HttpRequest.post(url);

		// 参数详情
		if (params != null) {
			request.form(params);
		}

		HttpResponse response = request.send();
		String respJson = response.bodyText();
		return respJson;
	}

	/**
	 * 发送Delete请求
	 * 
	 * @param url
	 *            : 请求的连接
	 * @param params
	 *            ： 请求参数，无参时传null
	 * @return
	 */
	public static String sendDelete(String url, Map<String, Object> params) {
		HttpRequest request = HttpRequest.delete(url);

		if (params != null) {
			request.form(params);
		}
		HttpResponse response = request.send();
		String respJson = response.bodyText();
		return respJson;
	}

	/**
	 * post请求数据
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postData(String url, String param) {
		StringBuffer sb = new StringBuffer();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStreamWriter oos = new OutputStreamWriter(
					conn.getOutputStream());
			oos.write(param);
			oos.flush();
			InputStream ips = conn.getInputStream();
			byte[] bts = new byte[1024];
			int len;
			while ((len = ips.read(bts)) != -1) {
				sb.append(new String(bts, 0, len, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	// 测试
	public static void main(String[] args) {
		/*
		 * //Get String responbody = HttpUtil.sendGet(
		 * "http://api.etherscan.io/api?module=account&action=txlist&address=0xdce284c4e0c71bc7aae6bdd3b108886cfe5f1a18&startblock=0&endblock=99999999&sort=asc&apikey=YourApiKeyToken"
		 * , null); System.out.println(responbody); Map<String,Object> resultMap
		 * = (Map<String, Object>) JSON.parseObject(responbody, Map.class);
		 * Object result = resultMap.get("result"); List<Map<String,String>>
		 * trans = JSON.parseObject(result.toString(), List.class);
		 * for(Map<String,String> map : trans) {
		 * 
		 * String confirmations = map.get("confirmations"); String from =
		 * map.get("from"); String to = map.get("to"); String hash =
		 * map.get("hash"); String value = map.get("value");
		 * System.out.println("from:"+from+",to:"+to+",value:"+value);
		 * 
		 * } System.out.println(resultMap);
		 */
		Map<String, Object> params = new HashMap<String, Object>();

		String token = "NTWAE0RH/q0ZFtqh3ds6+iSgcC8jBYmbOONwuVmfnrh0ngbQW/YRV/6lRS353CsDN0jJTuJCs+zaRVutccDD/mefGf94iNe6pD6ZMSyyEqBa18BkhECONzwEf2DLnuKIv2XhsnjpmGtgWyvIt6RbtUn1ylGuc+937ltmO6S0TJw=";

		String url = "http://47.92.122.8:8099/coinEntrsut/delCoinEntrustByAdmin.do";
		try {
			if (token.equals(new String(token.getBytes("iso-8859-1"),
					"iso-8859-1"))) {
				token = new String(token.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(token);
		// try {
		// token = URLEncoder.encode(token, "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println(token);
		params.put("userNo", "18869");
		params.put("entrustNo", "3796");
		params.put("token", token);

		System.out.println("params:" + JSON.toJSONString(params));
		String test = HttpUtil.sendPost_common(url, params);
		System.out.println("=============================== " + test);

	}

}
