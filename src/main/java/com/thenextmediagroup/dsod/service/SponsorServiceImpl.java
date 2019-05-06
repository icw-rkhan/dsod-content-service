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
import com.thenextmediagroup.dsod.model.Sponsor;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.SponsorPO;

@Service
public class SponsorServiceImpl implements SponsorService  {
	
	private static Logger logger = Logger.getLogger(SponsorServiceImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;
	
	public BaseResult save(SponsorPO sponsorPO) {
		// TODO Auto-generated method stub
		Sponsor sponsor = new Sponsor();
		BaseResult baseResult = new BaseResult();
		try {
			BeanUtils.copyProperties(sponsor, sponsorPO);
			mongoTemplate.save(sponsor);
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
	public BaseResult getAllSponsor() {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		List<Sponsor> sponsorList = mongoTemplate.findAll(Sponsor.class);
		final List<SponsorPO> sponsors = new ArrayList<SponsorPO>();
		for (int i = 0; i < sponsorList.size(); i++) {
			try {
				SponsorPO sponsorPO = new SponsorPO();
				BeanUtils.copyProperties(sponsorPO, sponsorList.get(i));
				sponsorPO.setId(sponsorList.get(i).getId());
				sponsors.add(sponsorPO);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("sponsors", sponsors);
		baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		baseResult.setResultMap(map);
		return baseResult;
	}
	
	@Override
	public BaseResult deleteOneById(String id) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		DeleteResult count = mongoTemplate.remove(query, Sponsor.class);
		if(count.getDeletedCount() == 0) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
		}else {
			baseResult.setCode(StateCodeInformationUtil.SUCCESS);
		}
		return baseResult;
	}
	
	@Override
	public BaseResult editSponsorById(SponsorPO sponsorPO) {
		// TODO Auto-generated method stub
		BaseResult baseResult = new BaseResult();
		Sponsor sponsor = new Sponsor();
		try {
			BeanUtils.copyProperties(sponsor, sponsorPO);
			sponsorPO.setId(sponsorPO.getId());
			mongoTemplate.save(sponsor);
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
