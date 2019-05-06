package com.thenextmediagroup.dsod.service;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.FilePO;
import com.thenextmediagroup.dsod.web.dto.MediaFilePO;

public interface FileUploadService {
	
	/**
	 * upload file
	 * @param file
	 * @param email
	 * @param fileType
	 * @return
	 */
	BaseResult uploadFile(GridFsTemplate gridFsTemplate, MultipartFile file, FilePO filePO);
	
	/**
	 * update file
	 * @return
	 */
	BaseResult updateFile(FilePO filePO);
	
	/**
	 * getMediaFileById
	 * @param mediaFilePO
	 * @return
	 */
	BaseResult getMediaFileById(String id);

	/**
	 * updateMediaFile
	 * @param mediaFilePO
	 * @return
	 */
	BaseResult updateMediaFile(FilePO filePO);


}
