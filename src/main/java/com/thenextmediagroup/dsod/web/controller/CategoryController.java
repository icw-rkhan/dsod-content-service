package com.thenextmediagroup.dsod.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.CategoryService;
import com.thenextmediagroup.dsod.service.ContentTypeService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.JwtTokenUtil;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.CategoryPO;
import com.thenextmediagroup.dsod.web.dto.ContentTypePO;

@RestController
public class CategoryController {

	@Autowired
	private TokenStore tokenStore;
	@Value("${authorization-service-secret}")
	private String secret;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ContentTypeService contentTypeService;
	
	private static Logger logger = Logger.getLogger(CategoryController.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/findAllCategory")
	@ResponseBody
	public BaseResult findAllCategory() {
		BaseResult baseResult = new BaseResult();
		baseResult = categoryService.findAllCategory();
		return baseResult;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/findAllCatogoryBySponsor")
	@ResponseBody
	public BaseResult findAllCatogoryBySponsor(String sponsorId) {
		BaseResult baseResult = new BaseResult();
		baseResult = categoryService.findAllCatogoryBySponsor(sponsorId);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/findAllContentType")
	@ResponseBody
	public BaseResult findAllContentType() {
		BaseResult baseResult = new BaseResult();
		baseResult = contentTypeService.findAllContentType();
		return baseResult;
	}
	
	/**
	 * save category
	 * @param categoryPO
	 * @return
	 */
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/saveCategory")
	@ResponseBody
	public BaseResult save(@RequestBody CategoryPO categoryPO) {
		logger.info("CategoryController class save function:" + categoryPO);
		BaseResult baseResult = new BaseResult();
		
		if (null == categoryPO) {
			baseResult.setCode(StateCodeInformationUtil.CATEGORY_IS_NULL);
			return baseResult;
		}
		if (null == categoryPO.getName() || "".equals(categoryPO.getName().trim())) {
			baseResult.setCode(StateCodeInformationUtil.CATEGORY_NAME_IS_NULL);
			return baseResult;
		}
		if(categoryPO.getSponsorId() == null) {
			categoryPO.setSponsorId("");
		}
		return categoryService.save(categoryPO);
	}
	
	/**
	 * delete category by id
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/deleteOneByCategoryId")
	@ResponseBody
	public BaseResult deleteOneByCategoryId(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = categoryService.deleteOneById(id);
		return baseResult;
	}
	
	/**
	 * save saveContentType
	 * @param categoryPO
	 * @return
	 */
	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/saveContentType")
	@ResponseBody
	public BaseResult save(@RequestBody ContentTypePO contentTypePO) {
		logger.info("CategoryController class save function:" + contentTypePO);
		BaseResult baseResult = new BaseResult();
		if (null == contentTypePO) {
			baseResult.setCode(StateCodeInformationUtil.CONTENTTYPE_IS_NULL);
			return baseResult;
		}
		if (null == contentTypePO.getName() || "".equals(contentTypePO.getName().trim())) {
			baseResult.setCode(StateCodeInformationUtil.CONTENTTYPE_NAME_IS_NULL);
			return baseResult;
		}
		return contentTypeService.save(contentTypePO);
	}
	
	/**
	 * delete ContentType by id
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/category/deleteOneByContentTypeId")
	@ResponseBody
	public BaseResult deleteOneByContentTypeId(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = contentTypeService.deleteOneById(id);
		return baseResult;
	}
}
