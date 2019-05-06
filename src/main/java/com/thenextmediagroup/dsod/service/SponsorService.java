package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.SponsorPO;

public interface SponsorService {

	/**
	 * save sponsor
	 * 
	 * @param sponsorPO
	 * @return
	 */
	BaseResult save(SponsorPO sponsorPO);

	/**
	 * get all sponsor
	 * 
	 * @param email
	 * @return
	 */
	BaseResult getAllSponsor();
	
	/**
	 * delete One By Id
	 * @param id
	 * @return
	 */
	BaseResult deleteOneById(String id);

	/**
	 * edit Sponsor By Id
	 * @param sponsorPO
	 * @return
	 */
	BaseResult editSponsorById(SponsorPO sponsorPO);

}
