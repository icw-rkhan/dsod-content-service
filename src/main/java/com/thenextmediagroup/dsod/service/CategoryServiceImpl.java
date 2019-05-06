package com.thenextmediagroup.dsod.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.thenextmediagroup.dsod.dao.CategoryDao;
import com.thenextmediagroup.dsod.model.Author;
import com.thenextmediagroup.dsod.model.Category;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.CategoryPO;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public BaseResult findAllCategory() {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
//			for (Category c : categoryDao.findAll()) {
//				c.setSponsorId("");
//				categoryDao.save(c);
//			}
			Query query = new Query();
			query.addCriteria(Criteria.where("sponsorId").is(""));
			List<Category> l = mongoTemplate.find(query, Category.class);
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", l);
			params.put("toatlFound", l.size());
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
	public BaseResult save(CategoryPO categoryPO) {
		// TODO Auto-generated method stub
		Category category = new Category();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(category, categoryPO);
			categoryDao.save(category);
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
		DeleteResult count = mongoTemplate.remove(query, Category.class);
		if (count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}

	@Override
	public BaseResult findAllCatogoryBySponsor(String sponsorId) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		List<Category> categorys = new ArrayList<Category>();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("sponsorId").is(sponsorId));
			List<Category> l = mongoTemplate.find(query, Category.class);
			Query queryAll = new Query();
			queryAll.addCriteria(Criteria.where("sponsorId").is(""));
			List<Category> lAll = mongoTemplate.find(queryAll, Category.class);
			categorys.addAll(l);
			categorys.addAll(lAll);
//			List<Category> l=categoryDao.findAll();
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", categorys);
			params.put("toatlFound", categorys.size());
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			// TODO: handle exception
			logger.error(e);
		}
		return baseResult;
	}

}
