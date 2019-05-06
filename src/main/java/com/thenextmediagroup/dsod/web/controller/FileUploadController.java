package com.thenextmediagroup.dsod.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thenextmediagroup.dsod.service.FileUploadService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.FilePO;
import com.thenextmediagroup.dsod.web.dto.MediaFilePO;

@RestController
public class FileUploadController {
	@Autowired
	private GridFsTemplate gridFsTemplate;
	@Autowired
	private FileUploadService fileUploadService;
	@Resource
	private MongoDbFactory mongoDbFactory;
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping(value = "/v1/file/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult uploadFile(MultipartFile file, String email, String fileType, String title, String length, Date uploadDate,String caption,String altText) {
		long startTime = new Date().getTime();
		LOGGER.info("上传开始时间："+ startTime);
		BaseResult baseResult = new BaseResult();
		FilePO filePO = new FilePO();
		filePO.setEmail(email);
		filePO.setFileType(fileType);
		filePO.setTitle(title);
		filePO.setLength(length);
		filePO.setUploadDate(uploadDate);
		filePO.setCaption(caption);
		filePO.setAltText(altText);
		if(null == filePO.getEmail() || "".equals(filePO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if(file.isEmpty()) {
			baseResult.setCode(StateCodeInformationUtil.FILE_IS_NULL);
			return baseResult;
		}
		try {
			int ft = Integer.parseInt(filePO.getFileType());
			if(ft < 1 || ft > 5) {
				baseResult.setCode(StateCodeInformationUtil.FILE_TYPE_IS_ERROR);
				return baseResult;
			} 
		} catch (Exception e) {
			// TODO: handle exception
			baseResult.setCode(StateCodeInformationUtil.FILE_TYPE_IS_ERROR);
			return baseResult;
		}
		if(null == filePO.getTitle() || "".equals(filePO.getTitle())) {
			baseResult.setCode(StateCodeInformationUtil.TITLE_IS_NULL);
			return baseResult;
		}
		 baseResult = fileUploadService.uploadFile(gridFsTemplate, file, filePO);
		long endTime = new Date().getTime();
		LOGGER.info("上传结束时间："+ endTime);
		LOGGER.info("上传耗时："+ (endTime - startTime));
		return baseResult;
	}
	@RequestMapping(value = "/v1/file/updateFile", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult uploadFile(@RequestBody FilePO filePO) {
		BaseResult baseResult = new BaseResult();
		if(null == filePO.getFileIds()) {
			baseResult.setCode(StateCodeInformationUtil.FILE_IDS_IS_NULL);
			return baseResult;
		}
		return fileUploadService.updateFile(filePO);
	}
	
	@RequestMapping(value = "/v1/file/getMediaFileById", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getMediaFileById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		return fileUploadService.getMediaFileById(id);
	}
	
	@RequestMapping(value = "/v1/file/updateMediaFile", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult updateMediaFile(@RequestBody FilePO filePO) {
		BaseResult baseResult = new BaseResult();
		if(null == filePO.getObjectId()) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		return fileUploadService.updateMediaFile(filePO);
	}
}
