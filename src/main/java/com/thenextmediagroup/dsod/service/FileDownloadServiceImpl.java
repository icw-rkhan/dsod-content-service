package com.thenextmediagroup.dsod.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.thenextmediagroup.dsod.model.FileEntity;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.FilePO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	public GridFsResource convertGridFSFile2Resource(GridFSFile gridFsFile) {
		GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
		return new GridFsResource(gridFsFile, gridFSDownloadStream);
	}

	@Resource
	private MongoDbFactory mongoDbFactory;

	@Bean
	public GridFSBucket getGridFSBuckets() {
		MongoDatabase db = mongoDbFactory.getDb();
		return GridFSBuckets.create(db);
	}

	@Resource
	private GridFSBucket gridFSBucket;

	// 获得SpringBoot提供的mongodb的GridFS对象
	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	MongoTemplate mongoTemplate;
	@Value("${spring.server.currentPath}")
	private String currentPath;

	@Override
	public void downloadFileByObjectId(String objectId, HttpServletResponse response) {
		// TODO Auto-generated method stub
		GridFSFile file = null;
		FileEntity fileEntity = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(objectId)),
				FileEntity.class);
		if (fileEntity != null)
			file = gridFsTemplate
					.findOne(new Query().addCriteria(Criteria.where("_id").is(fileEntity.getOriginalFigureId())));
		if (file != null) {
			GridFSDownloadStream in = gridFSBucket.openDownloadStream(file.getObjectId());
			GridFsResource resource = new GridFsResource(file, in);
			InputStream inputStream;
			try {
				inputStream = resource.getInputStream();
				byte[] buffer = new byte[10240];
				OutputStream out = response.getOutputStream();
				String fileName = new String(file.getFilename().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
				int len;
				while ((len = inputStream.read(buffer)) > 0)
					out.write(buffer, 0, len);
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

	/**
	 * get All File By Email
	 * 
	 * @return
	 */
	@Override
	public BaseResult getAllFile(QueryPO querys) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		try {
			String[] fileTypes = { "1", "2", "3", "4", "5" };
			if (Arrays.asList(fileTypes).contains(querys.getFileType() + "")) {
				query.addCriteria(Criteria.where("fileType").is(querys.getFileType() + ""));
			} else {
				Object[] o = new Object[] { "2", "3", "4", "5" };
				query.addCriteria(Criteria.where("fileType").in(o));
			}
			query.limit(querys.getLimit());
			query.skip(querys.getSkip());
			query.with(new Sort(Direction.DESC, "createDate"));
			List<FileEntity> files = mongoTemplate.find(query, FileEntity.class);
			List<FilePO> filePOs = new ArrayList<FilePO>();
			for (int i = 0; i < files.size(); i++) {
				FilePO filePO = new FilePO();
				BeanUtils.copyProperties(filePO, files.get(i));
				filePO.setCreateTime(files.get(i).getCreateDate());
				filePO.setFileName(files.get(i).getFileName());
				filePO.setCaption(files.get(i).getCaption());
				filePO.setFileUrl(currentPath + "?objectId=" + files.get(i).get_id());
				filePO.setObjectId(files.get(i).get_id());
				filePOs.add(filePO);
			}
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("filePOs", filePOs);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			baseResult.setResultMap(map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseResult;

	}

	/**
	 * delete File By Id
	 */
	@Override
	public BaseResult deleteFileById(String objectId) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(objectId));
		FileEntity fileEntity = mongoTemplate.findOne(query, FileEntity.class);
		if (null != fileEntity) {
			Query mongoQuery = new Query();
			mongoQuery.addCriteria(Criteria.where("_id").is(fileEntity.getOriginalFigureId()));
			if (fileEntity.getFileType().equals("4")) {
				Query mq = new Query();
				mq.addCriteria(Criteria.where("_id").is(fileEntity.getParentId()));
				gridFsTemplate.delete(mq);
			}
			gridFsTemplate.delete(mongoQuery);
			mongoTemplate.remove(query, FileEntity.class);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} else {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}
		return baseResult;
	}
}
