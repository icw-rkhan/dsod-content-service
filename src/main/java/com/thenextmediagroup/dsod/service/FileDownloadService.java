package com.thenextmediagroup.dsod.service;

import javax.servlet.http.HttpServletResponse;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

public interface FileDownloadService {
	
	/**
	 * download File By ObjectId
	 * @param objectId
	 * @return
	 */
	void downloadFileByObjectId(String objectId, HttpServletResponse response);
	
	/**
	 * get All File By Email
	 * @param query
	 * @return 
	 */
	BaseResult getAllFile(QueryPO query);

	/**
	 * delete File By Id
	 * @param objectId
	 * @return
	 */
	BaseResult deleteFileById(String objectId);

}
