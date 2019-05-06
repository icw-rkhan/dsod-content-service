package com.thenextmediagroup.dsod.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.FileDownloadService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@RestController
public class FileDownloadController {
	@Autowired
	private FileDownloadService fileDownloadService;
	@Resource
	private MongoDbFactory mongoDbFactory;
	private static Logger LOGGER = LoggerFactory.getLogger(FileDownloadController.class);
	@RequestMapping(value = "/v1/file/downloadFileByObjectId", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFileByObjectId(HttpServletRequest request, HttpServletResponse response, @RequestParam String objectId) {
		long startTime = new Date().getTime();
		LOGGER.info("下载开始时间："+ startTime);
		if(null != objectId && !"".equals(objectId)) {
			fileDownloadService.downloadFileByObjectId(objectId, response);
			long endTime = new Date().getTime();
			LOGGER.info("下载结束时间："+ endTime);
			LOGGER.info("下载耗时："+ (endTime - startTime));
		}
	}
	
	@RequestMapping(value = "/v1/file/getAllFile", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getAllFile(@RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();
		if(null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(query.getFileType() > 5) {
			baseResult.setCode(StateCodeInformationUtil.FILE_TYPE_IS_WRONG);
			return baseResult;
		}
		baseResult = fileDownloadService.getAllFile(query);
		return baseResult;
	}
	
	@RequestMapping(value = "/v1/file/deleteFileById", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult deleteFileById(@RequestParam String objectId) {
		BaseResult baseResult = new BaseResult();
		if(null == objectId || "".equals(objectId)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = fileDownloadService.deleteFileById(objectId);
		return baseResult;
	}
	
}
