package com.thenextmediagroup.dsod.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.SponsorService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.SponsorPO;

@RestController
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;

	private static Logger logger = Logger.getLogger(SponsorController.class);
	
	/**
	 * save author
	 * @param bookmarkPO
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/sponsor/save")
	@ResponseBody
	public BaseResult save(@RequestBody SponsorPO sponsorPO) {
		logger.info("AuthorController class save function:" + sponsorPO);
		BaseResult baseResult = new BaseResult();
		if (null == sponsorPO) {
			baseResult.setCode(StateCodeInformationUtil.SPONSOR_IS_NULL);
			return baseResult;
		}
		if (null == sponsorPO.getName() || "".equals(sponsorPO.getName().trim())) {
			baseResult.setCode(StateCodeInformationUtil.SPONSOR_NAME_IS_NULL);
			return baseResult;
		}
		return sponsorService.save(sponsorPO);
	}
	
	/**
	 * get all sponsor
	 * @param email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/sponsor/getAll")
	@ResponseBody
	public BaseResult getAllSponsor() {
		
		return sponsorService.getAllSponsor();
	}
	
	/**
	 * delete sponsor by id
	 * @param id
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/sponsor/deleteOneById")
	@ResponseBody
	public BaseResult deleteOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = sponsorService.deleteOneById(id);
		return baseResult;
	}
	
	/**
	 * edit sponsor by id
	 * @param id
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/sponsor/editSponsorById")
	@ResponseBody
	public BaseResult editSponsorById(@RequestBody SponsorPO sponsorPO) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(sponsorPO.getId())) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = sponsorService.editSponsorById(sponsorPO);
		return baseResult;
	}
}
