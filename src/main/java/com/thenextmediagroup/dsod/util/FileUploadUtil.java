package com.thenextmediagroup.dsod.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class FileUploadUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
	/**
	 * upload file by file path 
	 * @param urlPath
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	public static String saveByFilePath(String urlPath, String fileName, String contentType, GridFsTemplate gridFsTemplate) {
		String objectid = null;
        LOGGER.info("Saving file..");
        TomcatURLStreamHandlerFactory.disable();
        DBObject metaData = new BasicDBObject();
        metaData.put("createdDate", new Date());
        try {
        	URL url = new URL(urlPath);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setConnectTimeout(6000);
			httpURLConnection.connect();
			url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            objectid=gridFsTemplate.store(bin, fileName, contentType, metaData).toString();
            LOGGER.info("File saved: " + fileName);
        } catch (IOException e) {
            LOGGER.error("IOException: " + e);
            System.out.println(e);
//            throw new RuntimeException("System Exception while handling request");
        }
        LOGGER.info("File return: " + fileName);
        //return new Response(fileName);
        return objectid;
    }
}
