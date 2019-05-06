package com.thenextmediagroup.dsod.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.MagazineService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.MagazinePO;
import com.thenextmediagroup.dsod.web.dto.MagazineQueryPO;

@RestController
public class MagazineController {

	private static Logger logger = Logger.getLogger(CategoryController.class);
	
	@Autowired
	MagazineService magazineService;
	
	/**
	 * save magazine
	 * @param magazinePO
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/save")
	@ResponseBody
	public BaseResult save(@RequestBody MagazinePO magazinePO) {
		logger.info("CategoryController class save function:" + magazinePO);
		BaseResult baseResult = new BaseResult();
		if (null == magazinePO) {
			baseResult.setCode(StateCodeInformationUtil.MAGAZINE_IS_NULL);
			return baseResult;
		}
		return magazineService.save(magazinePO);
	}
	
	/**
	 * save magazine
	 * @param magazinePO
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/update")
	@ResponseBody
	public BaseResult update(@RequestBody MagazinePO magazinePO) {
		logger.info("CategoryController class save function:" + magazinePO);
		BaseResult baseResult = new BaseResult();
		if (null == magazinePO) {
			baseResult.setCode(StateCodeInformationUtil.MAGAZINE_IS_NULL);
			return baseResult;
		}
		return magazineService.update(magazinePO);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/findAll")
	@ResponseBody
	public BaseResult findAll(@RequestBody MagazineQueryPO queryPO) {
		BaseResult baseResult = new BaseResult();
		if (null == queryPO) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		//1、review;2、published；3、draft；
		queryPO.setStatus(2);
		return magazineService.findAll(queryPO);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/admin/findAll")
	@ResponseBody
	public BaseResult findAllByAdmin(@RequestBody MagazineQueryPO queryPO) {
		BaseResult baseResult = new BaseResult();
		if (null == queryPO) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		return magazineService.findAll(queryPO);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/findOneById")
	@ResponseBody
	public BaseResult findOneById(String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		return magazineService.findOneById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/findContentByMagazine")
	@ResponseBody
	public BaseResult findContentByMagazine(String magazineId,String searchValue) {
		BaseResult baseResult = new BaseResult();
		if(null == magazineId || "".equals(magazineId)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		return magazineService.findContentByMagazine(magazineId, searchValue);
	}
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/magazine/deleteById")
	@ResponseBody
	public BaseResult deleteById(String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		return magazineService.deleteById(id);
	}
}
