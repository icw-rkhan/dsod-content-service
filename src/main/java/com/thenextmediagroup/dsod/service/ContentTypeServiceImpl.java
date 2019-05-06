package com.thenextmediagroup.dsod.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.thenextmediagroup.dsod.dao.CategoryDao;
import com.thenextmediagroup.dsod.dao.ContentTypeDao;
import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.model.ContentType;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.ContentTypePO;

@Service
public class ContentTypeServiceImpl implements ContentTypeService {

	private static Logger logger = Logger.getLogger(ContentTypeServiceImpl.class);
	@Autowired
	ContentTypeDao contentTypeDao;
	@Autowired
	MongoTemplate mongoTemplate;
	@Override
	public BaseResult findAllContentType() {
		// TODO Auto-generated method stub
		BaseResult baseResult =new BaseResult();
		try {
			Query query = new Query();
			query.with(new Sort(Direction.ASC, "sort"));
			
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", mongoTemplate.find(query, ContentType.class));
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}
		return baseResult;
	}
	@Override
	public BaseResult save(ContentTypePO contentTypePO) {
		// TODO Auto-generated method stub
		ContentType contentType = new ContentType();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(contentType, contentTypePO);
			contentTypeDao.save(contentType);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} 
		
		return baseResult;
	}
	@Override
	public BaseResult deleteOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		DeleteResult count = mongoTemplate.remove(query, ContentType.class);
		if(count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}

}
