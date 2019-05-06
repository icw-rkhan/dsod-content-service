package com.thenextmediagroup.dsod.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;

public class GridFSApi {

	private static Logger LOGGER = LoggerFactory.getLogger(GridFSApi.class);
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Resource
    private MongoDbFactory mongoDbFactory;
    @Resource
    private GridFSBucket gridFSBucket;
	public String saveByFile(MultipartFile file) {

		String objectid;
        LOGGER.info("Saving file..");
        DBObject metaData = new BasicDBObject();
        metaData.put("createdDate", new Date());

        String fileName = file.getOriginalFilename();

        LOGGER.info("File Name: " + fileName);

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            objectid=gridFsTemplate.store(inputStream, fileName, file.getContentType(), metaData).toString();
            LOGGER.info("File saved: " + fileName);
        } catch (IOException e) {
            LOGGER.error("IOException: " + e);
            throw new RuntimeException("System Exception while handling request");
        }
        LOGGER.info("File return: " + fileName);
        //return new Response(fileName);
        return objectid;
    }
	
	public void downloadByFileName(String filename,HttpServletResponse response) {
		
		GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
        if(file != null){
            System.out.println("_id:"+file.getId());
            System.out.println("_objectId:"+file.getObjectId());
            GridFSDownloadStream in = gridFSBucket.openDownloadStream(file.getObjectId());

            GridFsResource resource = new GridFsResource(file,in);
            InputStream inputStream;
			try {
				inputStream = resource.getInputStream();
				byte[] buffer = new byte[10240];
				OutputStream out = response.getOutputStream();
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");  
				response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getFilename());
				int len;
				 while ((len = inputStream.read(buffer)) > 0)
					 out.write(buffer,0,len);
				 inputStream.close();
				out.close();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }
	}
	
	public void downloadByObjectId(String objectId,HttpServletResponse response) {
		
		GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(objectId)));
        if(file != null){
            System.out.println("_id:"+file.getId());
            System.out.println("_objectId:"+file.getObjectId());
            GridFSDownloadStream in = gridFSBucket.openDownloadStream(file.getObjectId());

            GridFsResource resource = new GridFsResource(file,in);
            InputStream inputStream;
            
            String fileName=file.getFilename();
			try {
				inputStream = resource.getInputStream();
				byte[] buffer = new byte[10240];
				OutputStream out = response.getOutputStream();
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");  
				response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				int len;
				 while ((len = inputStream.read(buffer)) > 0)
					 out.write(buffer,0,len);
				 inputStream.close();
				out.close();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }
	}
}
