package com.thenextmediagroup.dsod.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.thenextmediagroup.dsod.dao.FileUploadDao;
import com.thenextmediagroup.dsod.model.FileEntity;
import com.thenextmediagroup.dsod.model.Magazine;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.FilePO;
import com.thenextmediagroup.dsod.web.dto.MediaFilePO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import com.sun.image.codec.jpeg.JPEGCodec;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadServiceImpl.class);
	@Value("${thumbnail.width}")
	private int width;
	@Value("${thumbnail.height}")
	private int height;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Value("${spring.server.currentPath}")
	private String currentPath;
	@Value("${spring.server.placeholder}")
	private String placeHolder;
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public BaseResult uploadFile(GridFsTemplate gridFsTemplate, MultipartFile file, FilePO filePO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		FileEntity fileEntity = new FileEntity();
		FileEntity f = null;
		final Map<String, Object> map = new HashMap<String, Object>();
		String objectId;
		try {
			objectId = saveFileToMongo(gridFsTemplate, file);
			BeanUtils.copyProperties(fileEntity, filePO);
			fileEntity.setCreateDate(new Date());
			fileEntity.setEmail(filePO.getEmail());
			fileEntity.setFileName(file.getOriginalFilename());
			fileEntity.setOriginalFigureId(objectId);
			fileEntity.setFileType(filePO.getFileType());
			FileEntity originalFile = fileUploadDao.save(fileEntity);
			FileEntity thumbnailFile = null;
//			file.getContentType().indexOf("image") != -1 && file.getContentType().indexOf("application") != -1&& 
			if (filePO.getFileType().equals("1")) {
				f = new FileEntity();
				BeanUtils.copyProperties(f, fileEntity);
				String thumbnailId = saveThumbnail(gridFsTemplate, file, width, height);
				f.setOriginalFigureId(thumbnailId);
				f.setFileType("4");
				f.set_id(null);
				f.setParentId(originalFile.get_id());
//				f.setThumbnailUrl(currentPath + "?objectId=" + thumbnailId);
				thumbnailFile = fileUploadDao.save(f);
				thumbnailFile.setThumbnailUrl(
						placeHolder + "/file/downloadFileByObjectId?objectId=" + thumbnailFile.get_id());
				thumbnailFile
						.setFileUrl(placeHolder + "/file/downloadFileByObjectId?objectId=" + originalFile.get_id());
				map.put("fileEntity", thumbnailFile);
			} else {
				originalFile.setFileUrl(placeHolder + "/file/downloadFileByObjectId?objectId=" + originalFile.get_id());
				map.put("fileEntity", originalFile);
			}
			baseResult.setResultMap(map);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseResult;
	}

	private static String saveFileToMongo(GridFsTemplate gridFsTemplate, MultipartFile file) throws IOException {
		String objectid;
		DBObject metaData = new BasicDBObject();
		metaData.put("createdDate", new Date());
		String fileName = file.getOriginalFilename();
		InputStream inputStream = null;
		inputStream = file.getInputStream();
		objectid = gridFsTemplate.store(inputStream, fileName, file.getContentType(), metaData).toString();
		LOGGER.info("File return: " + fileName);
		// return new Response(fileName);
		return objectid;
	}

	private static String saveThumbnail2(GridFsTemplate gridFsTemplate, MultipartFile file, int width, int height)
			throws IOException {
		Builder<? extends InputStream> thumbnail;
		String objectid = null;
		String fileName = file.getOriginalFilename();
		DBObject metaData = new BasicDBObject();
		thumbnail = Thumbnails.of(file.getInputStream());
		thumbnail.size(width, height);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		thumbnail.toOutputStream(out);
		ByteArrayInputStream swapStream = new ByteArrayInputStream(out.toByteArray());
		objectid = gridFsTemplate.store(swapStream, fileName, file.getContentType(), metaData).toString();
		return objectid;
	}

	private static String saveThumbnail(GridFsTemplate gridFsTemplate, MultipartFile file, int width, int height)
			throws IOException {
		Builder<? extends InputStream> thumbnail;
		String objectid = null;
		String fileName = file.getOriginalFilename();
		DBObject metaData = new BasicDBObject();
		InputStream is = file.getInputStream();
		ByteArrayInputStream swapStream = null;

		if ("JPEG".equals(imageType(file))) {
			BufferedImage img = JPEGCodec.createJPEGDecoder(file.getInputStream()).decodeAsBufferedImage();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "JPEG", os);
			swapStream = new ByteArrayInputStream(os.toByteArray());
		} else {
			thumbnail = Thumbnails.of(is);
			thumbnail.size(width, height);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			thumbnail.toOutputStream(out);
			swapStream = new ByteArrayInputStream(out.toByteArray());
		}

		objectid = gridFsTemplate.store(swapStream, fileName, file.getContentType(), metaData).toString();
		return objectid;
	}

	private static String imageType(MultipartFile file) {

		ImageInputStream iis;
		ImageReader reader;
		String result = "";
		try {
			iis = ImageIO.createImageInputStream(file.getInputStream());
			;
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			System.out.println(file.getContentType());
			if (iter.hasNext()) {
				reader = iter.next();
				result = reader.getFormatName();
			}

			iis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toUpperCase();
	}

	@Override
	public BaseResult updateFile(FilePO filePO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {

			for (String fileId : filePO.getFileIds()) {
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(fileId));
				FileEntity fileEntity = mongoTemplate.findOne(query, FileEntity.class);
				if (fileEntity != null) {
//					BeanUtils.copyProperties(magazineone, magazinePO);
					fileEntity.setAltText(filePO.getAltText());
					fileEntity.setCaption(filePO.getCaption());
					fileEntity.setTitle(filePO.getTitle());
					fileEntity.setFileType(filePO.getFileType());
					fileUploadDao.save(fileEntity);
				}
			}
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	@Override
	public BaseResult getMediaFileById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			FilePO filePO = new FilePO();
			FileEntity fileEntity = mongoTemplate.findById(id, FileEntity.class);
			if (null != fileEntity) {
				BeanUtils.copyProperties(filePO, fileEntity);
				filePO.setObjectId(fileEntity.get_id());
				final Map<String, Object> map = new HashMap<String, Object>();
				map.put("data", filePO);
				baseResult.setResultMap(map);
				baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			} else {
				baseResult.setCode(StateCodeInformationUtil.ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}
		return baseResult;
	}

	@Override
	public BaseResult updateMediaFile(FilePO filePO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			FileEntity fileEntity = mongoTemplate.findById(filePO.getObjectId(), FileEntity.class);
			if (null != fileEntity) {
				fileEntity.setAltText(filePO.getAltText());
				fileEntity.setCaption(filePO.getCaption());
				fileEntity.setTitle(filePO.getTitle());
				fileEntity.setFileType(filePO.getFileType());
				mongoTemplate.save(fileEntity);
			}
			if (fileEntity.getFileType().equals("4")) {
				Query query = new Query();
				query.addCriteria(Criteria.where("parentId").is(fileEntity.get_id()));
				fileEntity = mongoTemplate.findOne(query, FileEntity.class);
				if (null != fileEntity) {
					fileEntity.setAltText(filePO.getAltText());
					fileEntity.setCaption(filePO.getCaption());
					fileEntity.setTitle(filePO.getTitle());
					mongoTemplate.save(fileEntity);
				}
			}
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}
		return baseResult;
	}
}
