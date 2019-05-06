package com.thenextmediagroup.dsod.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.thenextmediagroup.dsod.dao.MagazineDao;
import com.thenextmediagroup.dsod.model.Content;
import com.thenextmediagroup.dsod.model.Magazine;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.MagazinePO;
import com.thenextmediagroup.dsod.web.dto.MagazineQueryPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@Service
public class MagazineServiceImpl implements MagazineService {

	private static Logger logger = Logger.getLogger(MagazineServiceImpl.class);

	@Autowired
	MagazineDao magazineDao;
	@Autowired
	MongoTemplate mongoTemplate;
	@Value("${spring.server.currentPath}")
	private String currentPath;
	@Autowired
	ContentReleaseService contentReleaseService;

	@Override
	public BaseResult save(MagazinePO magazinePO) {
		// TODO Auto-generated method stub
		Magazine magazine = new Magazine();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(magazine, magazinePO);
			
			//The vol and issue will be provided by front end directly.
/*			Calendar date = Calendar.getInstance();
			int number = date.get(Calendar.YEAR) - 2017;

			Query query = new Query();
			query.addCriteria(Criteria.where("vol").is("vol " + number));
			query.with(new Sort(Direction.DESC, "sort"));
			Magazine magazineone = mongoTemplate.findOne(query, Magazine.class);

			
			magazine.setVol("vol " + number);
			if (magazineone == null) {
				magazine.setSort(1);
				magazine.setIssue("issue 1");
			} else {
				magazine.setSort(magazineone.getSort() + 1);
				magazine.setIssue("issue " + (magazineone.getSort() + 1));
			}*/
			
			magazine.setCreateDate(new Date());
			
			if (magazinePO.getStatus() == 2) {
				magazine.setVersion(1);
			}
			Magazine m = magazineDao.save(magazine);
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", m);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	@Override
	public BaseResult findAll(MagazineQueryPO queryPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {
			Query query = new Query();
			List<Criteria> criterias = new ArrayList<Criteria>();
//			if (!ValidationUtil.isBlank(queryPO.getSerial())) {
//				criterias.add(Criteria.where("serial").is(queryPO.getSerial()));
//			}
			if (queryPO.getStatus() != 0) {
				criterias.add(Criteria.where("status").is(queryPO.getStatus()));
			}

			if (criterias.size() > 0) {
				Criteria criteriaContent = new Criteria();
				criteriaContent.andOperator(criterias.toArray(new Criteria[criterias.size()]));
				query.addCriteria(criteriaContent);
			}

			query.limit(queryPO.getLimit());
			query.skip(queryPO.getSkip());
			query.with(new Sort(Direction.DESC, "publishDate"));

			List<Magazine> l = mongoTemplate.find(query, Magazine.class);

			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", l);
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
	public BaseResult findOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id));
			Magazine magazine = mongoTemplate.findOne(query, Magazine.class);

			MagazinePO magazinePO = new MagazinePO();
			BeanUtils.copyProperties(magazinePO, magazine);

			if (null != magazine) {
				magazinePO.setPdfUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + magazine.getPdfId());
				String[] articles = magazine.getArticles();
				if (articles.length > 0) {
					List<ContentPO> content = new ArrayList<>();
					for (String ids : articles) {
//						Query queryContent = new Query();
//						queryContent.addCriteria(Criteria.where("_id").is(ids));
//						content.addAll(mongoTemplate.find(queryContent, Content.class));
						BaseResult baseResultContent = contentReleaseService.findOneById(ids, null);
						if (baseResultContent.getResultMap().get("data") != null) {
							content.add((ContentPO) baseResultContent.getResultMap().get("data"));
						}

					}
					magazinePO.setContents(content);
				}
			}

			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", magazinePO);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	@Override
	public BaseResult deleteById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult count = mongoTemplate.remove(query, Magazine.class);
		if (count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		} else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}

	@Override
	public BaseResult update(MagazinePO magazinePO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(magazinePO.getId()));
			Magazine magazineone = mongoTemplate.findOne(query, Magazine.class);
			if (magazineone != null) {

//				BeanUtils.copyProperties(magazineone, magazinePO);
				magazineone.setCover(magazinePO.getCover());
				magazineone.setStatus(magazinePO.getStatus());
				magazineone.setCreateUser(magazinePO.getCreateUser());
				magazineone.setPdfId(magazinePO.getPdfId());
				magazineone.setPublishDate(magazinePO.getPublishDate());
				magazineone.setArticles(magazinePO.getArticles());
				magazineone.setSerial(magazinePO.getSerial());
				magazineone.setIssue(magazinePO.getIssue());
				magazineone.setVol(magazinePO.getVol());
				if (magazineone.getStatus() == 2) {
					if (magazineone.getVersion() == null) {
						magazineone.setVersion(1);
					} else {
						magazineone.setVersion(magazineone.getVersion() + 1);
					}
				}
				magazineDao.save(magazineone);
				baseResult.setCode(StateCodeInformationUtil.SUCCESS);
			} else {
				baseResult.setCode(StateCodeInformationUtil.MAGAZINE_IS_NULL);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

	@Override
	public BaseResult findContentByMagazine(String magazineId, String searchValue) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(magazineId));
			Magazine magazine = mongoTemplate.findOne(query, Magazine.class);

			MagazinePO magazinePO = new MagazinePO();
			BeanUtils.copyProperties(magazinePO, magazine);

			if (null != magazine) {
				magazinePO.setPdfUrl(currentPath + "/file/downloadFileByObjectId?objectId=" + magazine.getPdfId());
				String[] articles = magazine.getArticles();
				if (articles.length > 0) {
					List<ContentPO> content = new ArrayList<>();
					for (String ids : articles) {
//						Query queryContent = new Query();
//						queryContent.addCriteria(Criteria.where("_id").is(ids));
//						content.addAll(mongoTemplate.find(queryContent, Content.class));
						BaseResult baseResultContent = contentReleaseService.findOneByIdAndSearchValue(ids, searchValue,
								null);
						if (baseResultContent.getResultMap().get("data") != null) {
							content.add((ContentPO) baseResultContent.getResultMap().get("data"));
						}

					}
					magazinePO.setContents(content);
				}
			}

			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("data", magazinePO);
			baseResult.setResultMap(params);
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}

		return baseResult;
	}

}
