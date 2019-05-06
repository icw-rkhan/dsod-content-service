package com.thenextmediagroup.dsod.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thenextmediagroup.dsod.service.AuthorService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.util.ValidationUtil;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.AuthorQueryPO;

@RestController
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	private static Logger logger = Logger.getLogger(AuthorController.class);
	
	/**
	 * save author
	 * @param authorPO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/author/save")
	@ResponseBody
	public BaseResult save(@RequestBody AuthorPO authorPO) {
		logger.info("AuthorController class save function:" + authorPO);
		BaseResult baseResult = new BaseResult();
		if (null == authorPO) {
			baseResult.setCode(StateCodeInformationUtil.AUTHOR_IS_NULL);
			return baseResult;
		}
		if (null == authorPO.getFirstName() || "".equals(authorPO.getFirstName().trim())) {
			baseResult.setCode(StateCodeInformationUtil.FIRST_NAME_IS_NULL);
			return baseResult;
		}
		//Temporary annotation verification code
		/*if (null == authorPO.getLastName() || "".equals(authorPO.getLastName().trim())) {
			baseResult.setCode(StateCodeInformationUtil.LAST_NAME_IS_NULL);
			return baseResult;
		}
		if((authorPO.getFirstName().length() + authorPO.getLastName().length()) > 100) {
			baseResult.setCode(StateCodeInformationUtil.OVERLENGTH_AUTHOR_NAME);
			return baseResult;
		}
		if(null == authorPO.getCellPhone()) {
			baseResult.setCode(StateCodeInformationUtil.CELL_PHONE_IS_NULL);
			return baseResult;
		}
		if(!ValidationUtil.isNumber(authorPO.getCellPhone().toString())) {
			baseResult.setCode(StateCodeInformationUtil.CELL_PHONE_IS_WRONG);
			return baseResult;
		}
		if(null == authorPO.getRole() || "".equals(authorPO.getRole().trim())) {
			baseResult.setCode(StateCodeInformationUtil.ROLE_IS_NULL);
			return baseResult;
		}
		if(null == authorPO.getEmail() || "".equals(authorPO.getEmail().trim())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL);
			return baseResult;
		}
		if(!ValidationUtil.isEmail(authorPO.getEmail())) {
			baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID);
			return baseResult;
		}*/
		return authorService.save(authorPO);
	}
	
	/**
	 * get all author 
	 * @param email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/author/getAll")
	@ResponseBody
	public BaseResult getAllAuthor(@RequestBody AuthorQueryPO authorQueryPO) {
		
		return authorService.getAllAuthor(authorQueryPO);
	}
	
	/**
	 * delete author by id
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/v1/author/deleteOneById")
	@ResponseBody
	public BaseResult deleteOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = authorService.deleteOneById(id);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/author/findOneById")
	@ResponseBody
	public BaseResult findOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = authorService.findOneById(id);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/author/editOneById")
	@ResponseBody
	public BaseResult editOneById(@RequestBody AuthorPO authorPO) {
		BaseResult baseResult = new BaseResult();
		if (ValidationUtil.isBlank(authorPO.get_id())) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = authorService.editOneById(authorPO);
		return baseResult;
	}
}
