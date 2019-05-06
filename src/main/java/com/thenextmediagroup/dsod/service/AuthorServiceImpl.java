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
import com.thenextmediagroup.dsod.model.Author;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.AuthorQueryPO;

@Service
public class AuthorServiceImpl implements AuthorService {

	private static Logger logger = Logger.getLogger(AuthorServiceImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	public BaseResult save(AuthorPO authorPO) {
		// TODO Auto-generated method stub
		Author author = new Author();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(author, authorPO);
			Query query = new Query();
			query.addCriteria(
					Criteria.where("firstName").is(authorPO.getFirstName()).and("lastName").is(authorPO.getLastName()));
			List<Author> authors = mongoTemplate.find(query, Author.class);
			final Map<String, Object> map = new HashMap<String, Object>();
			if (null == authors || 0 == authors.size()) {
				if(null != authorPO.getLastName())
					author.setFullName(authorPO.getFirstName() + " " + authorPO.getLastName());
				else
					author.setFullName(authorPO.getFirstName());
				mongoTemplate.save(author);
				map.put("author", author);
			}else {
				map.put("author", authors.get(0));
			}
			baseResult.setResultMap(map);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	@Override
	public BaseResult getAllAuthor(AuthorQueryPO authorQueryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			long totalFound = mongoTemplate.count(query, Author.class);
			if (null != authorQueryPO.getLimit() && !"".equals(authorQueryPO.getLimit().trim())
					&& null != authorQueryPO.getSkip() && !"".equals(authorQueryPO.getSkip().trim())) {
				query.limit(Integer.parseInt(authorQueryPO.getLimit()));
				query.skip(Integer.parseInt(authorQueryPO.getSkip()));
			}
			List<Author> authorList = mongoTemplate.find(query, Author.class);
			final List<AuthorPO> authors = new ArrayList<AuthorPO>();
			for (int i = 0; i < authorList.size(); i++) {
				AuthorPO authorPO = new AuthorPO();
				BeanUtils.copyProperties(authorPO, authorList.get(i));
				authorPO.set_id(authorList.get(i).get_id());
				authors.add(authorPO);
				
			}
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("authors", authors);
			map.put("totalFound", totalFound);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			baseResult.setResultMap(map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			e.printStackTrace();
		}
		return baseResult;
	}

	@Override
	public BaseResult deleteOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult count = mongoTemplate.remove(query, Author.class);
		if (count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}

	public BaseResult findOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Author author = mongoTemplate.findOne(query, Author.class);
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", author);
		baseResult.setResultMap(params);
		baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		return baseResult;
	}
	
	@Override
	public BaseResult editOneById(AuthorPO authorPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Author author = new Author();
		try {
			BeanUtils.copyProperties(author, authorPO);
			author.set_id(authorPO.get_id());
			if(null != authorPO.getLastName())
				author.setFullName(authorPO.getFirstName() + " " + authorPO.getLastName());
			else
				author.setFullName(authorPO.getFirstName());
			mongoTemplate.save(author);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			logger.error(e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			logger.error(e);
		}
		return baseResult;
	}
}
