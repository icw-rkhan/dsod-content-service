package com.thenextmediagroup.dsod.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.el.parser.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.thenextmediagroup.dsod.model.Content;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpUtils {
	
	
	public static Map cache = new HashMap();
	public static Map tags = new HashMap();
	
	static {
		tags.put("260", "SPS:ALIGN");
		tags.put("197", "SPS:GSK");
		tags.put("259", "SPS:NOBEL");
		tags.put("501", "SPS:TNMG");
	}
	
	
	public void getAllPost(String json) {
		JSONArray array = JSONArray.fromObject(json);
		for (Object obj : array) {
			getPostById((JSONObject)obj);
		}
	}
	
	
	public void getPostById(JSONObject json) {
		Content cont = new Content();
		String categories = json.getString("categories");
		String author = json.getString("author");
		String tagStr = json.getString("tags");
		String email="";
		String title = json.getString("title");
		String content = json.getString("content");
//		cont.setEmail(json.getString("email"));
		cont.setTitle(title);
		
		//合作伙伴
//		System.out.println(tagStr);
		String[]tag = tagStr.substring(1, tagStr.length()-1).split(",");
		for (String t : tag) {
			if(tags.get(t)!=null) {
				cont.setSponsorId(t);
//				System.out.println(t+":"+tags.get(t).toString());
			}
		}
		// 解析category 
		String[] category = categories.substring(1, categories.length()-1).split(",");
		String categoryType = "";
		String contentType="";
		for (String str : category) {
			Object obj = cache.get(str)!=null?cache.get(str):null;
			if(obj!=null && obj.toString().startsWith("*")) {
				contentType = str;
				cont.setContentTypeId(contentType);
//				System.out.println(str+":"+obj.toString());
			}else {
				categoryType += str+",";
			}
		}
		cont.setCategoryId(categoryType.substring(0, categoryType.length()-1));
//		System.out.println(categoryType.substring(0, categoryType.length()-1));
//		System.out.println(categories.substring(1, categories.length()-1));
//		System.out.println(categories);
//		解析文章内容
//		System.out.println(content);
		if(cache.get(contentType).toString().equals("*Articles")) {
			JSONObject o = JSONObject.fromObject(content);
			content = delHTMLTag(o.getString("rendered"));
			cont.setContent(content);
//			System.out.println(content);
		}
		if(cache.get(contentType).toString().equals("*Videos")) {
//			System.out.println(content);
			String url = parseVideo(content);
//			TDOD 调用上传视频接口，返回ID后保存到Content对象中
		}
		if(cache.get(contentType).toString().equals("*Animations")) {
			
		}
		if(cache.get(contentType).toString().equals("*Interviews")) {
			
		}
		if(cache.get(contentType).toString().equals("*Podcasts")) {
			
		}
		if(cache.get(contentType).toString().equals("*Tech Guides")) {
			
		}
		if(cache.get(contentType).toString().equals("*Tip Sheets")) {
			
		}
		if(cache.get(contentType).toString().equals("*Visual Essay")) {
			
		}
	}
	
	/**
	 * 初始化categories 数据，加'*'内容为文章类型，其他为文章专业类型
	 * @param json
	 */
	public void initCategories(String json) {
		JSONArray array = JSONArray.fromObject(json);
		String id,name;
		for (Object obj : array) {
			id = ((JSONObject)obj).get("id").toString();
			name =((JSONObject)obj).get("name").toString();
			if(name.startsWith("*")) {
				//TDOD 数据存储到文章类型
			}else {
				//TDOD 数据存储到专业类型
			}
//			System.out.println(id+":"+name);
			cache.put(id, name);
		}
	}
	
	
	/**
	 * 解析video url
	 * @param con
	 * @return url
	 */
	public String parseVideo(String con) {
		String videoUrl = "";
		con = con.substring(con.indexOf("<source"));
		con= con.substring(0, con.indexOf("/>"));
		videoUrl = con.substring(con.indexOf("src")).trim();
		videoUrl = videoUrl.substring(videoUrl.indexOf("\"")+1, videoUrl.length()-1);
		return videoUrl;
	}
	
	
	public static String delHTMLTag(String htmlStr){
			String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
			String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
			String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
			
			Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
			Matcher m_script=p_script.matcher(htmlStr); 
			htmlStr=m_script.replaceAll(""); //过滤script标签 
			
			Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
			Matcher m_style=p_style.matcher(htmlStr); 
			htmlStr=m_style.replaceAll(""); //过滤style标签 
			
			Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
			Matcher m_html=p_html.matcher(htmlStr); 
			htmlStr=m_html.replaceAll(""); //过滤html标签
			
			htmlStr = htmlStr.replace("&nbsp;","");
			return htmlStr.trim();
	}
	
	public static void main(String[] args) throws ParseException, IOException, KeyManagementException, NoSuchAlgorithmException{
		String url = "https://wp.dsodentist.com/wp-json/wp/v2/posts/976?_embed";
//		String body = send(url, null, "utf-8");
//		System.out.println("交易响应结果长度："+body.length());
//		System.out.println(body);
		HttpUtils utils = new HttpUtils();
//		初始化字典
		File file1 = FileUtils.getFile("/Users/amazingz/categories.json");
		utils.initCategories(FileUtils.readFileToString(file1));
		
//		
		File file = FileUtils.getFile("/Users/amazingz/posts.json");
		utils.getAllPost(FileUtils.readFileToString(file));
//		getPostById(JSONObject.fromObject(FileUtils.readFileToString(file)));
		
		
//		System.out.println("-----------------------------------");
//		
//		url = "https://kyfw.12306.cn/otn/";
//		body = send(url, null, "utf-8");
//		System.out.println("交易响应结果长度："+body.length());
	}


}

