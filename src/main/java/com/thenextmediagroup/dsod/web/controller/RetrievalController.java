package com.thenextmediagroup.dsod.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.RetrievalService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@RestController
public class RetrievalController {
	
	@Autowired
	private RetrievalService retrievalService;
	@Autowired
	private TokenStore tokenStore;
	@Value("${authorization-service-secret}")
	private String secret;
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
	
	/**
	 * not include title and content file
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/v1/content/findAllBySearch", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult retrievalContents(@RequestBody QueryPO query) {
		LOGGER.info("retrievalContents方法入参：" + query);
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == query.getSearchValue() || "".equals(query.getSearchValue())) {
			baseResult.setCode(StateCodeInformationUtil.SERRCH_VALUE_IS_NULL);
			return baseResult;
		}
		
		baseResult = retrievalService.retrievalContentsByType(query);
		return baseResult;
	}
	
	@RequestMapping(value = "/v1/content/public/findAllBySearch", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult findAllBySearchNotLoggedIn(@RequestBody QueryPO query) {
		LOGGER.info("retrievalContents方法入参：" + query);
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == query.getSearchValue() || "".equals(query.getSearchValue())) {
			baseResult.setCode(StateCodeInformationUtil.SERRCH_VALUE_IS_NULL);
			return baseResult;
		}
		query.setIsAdmin(false);
		baseResult = retrievalService.retrievalContentsByType(query);
		return baseResult;
	}
	
	/**
	 * include title and content file
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/v1/content/public/findAllByValue", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult findAllPublicByValue(@RequestBody QueryPO query) {
		LOGGER.info("retrievalContents方法入参：" + query);
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == query.getSearchValue() || "".equals(query.getSearchValue())) {
			baseResult.setCode(StateCodeInformationUtil.SERRCH_VALUE_IS_NULL);
			return baseResult;
		}
		query.setIsAdmin(false);
		baseResult = retrievalService.retrievalContentsByValue(query);
		return baseResult;
	}
	
	@RequestMapping(value = "/v1/content/findAllByValue", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult findAllByValue(@RequestBody QueryPO query) {
		LOGGER.info("retrievalContents方法入参：" + query);
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == query.getSearchValue() || "".equals(query.getSearchValue())) {
			baseResult.setCode(StateCodeInformationUtil.SERRCH_VALUE_IS_NULL);
			return baseResult;
		}

		baseResult = retrievalService.retrievalContentsByValue(query);

		return baseResult;
	}
	
	@RequestMapping(value = "/v1/content/admin/findAllByValue", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult findAllByValueAdmin(@RequestBody QueryPO query) {
		LOGGER.info("retrievalContents方法入参：" + query);
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		if(null == query.getSearchValue() || "".equals(query.getSearchValue())) {
			baseResult.setCode(StateCodeInformationUtil.SERRCH_VALUE_IS_NULL);
			return baseResult;
		}
		
		baseResult = retrievalService.retrievalContentsByValueAdmin(query);
		return baseResult;
	}
}
