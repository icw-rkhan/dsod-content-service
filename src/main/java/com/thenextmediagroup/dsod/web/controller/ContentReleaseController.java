package com.thenextmediagroup.dsod.web.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.thenextmediagroup.dsod.model.VisualEssay;
import com.thenextmediagroup.dsod.service.ContentReleaseService;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.util.JwtTokenUtil;
import com.thenextmediagroup.dsod.util.StateCodeInformationUtil;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

@RestController
public class ContentReleaseController {
	@Autowired
	private ContentReleaseService contentReleaseService;
	@Autowired
	private TokenStore tokenStore;
	@Value("${authorization-service-secret}")
	private String secret;
	@Value("${spring.server.adminRole}")
	private String adminRole;

//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/savePost")
	@ResponseBody
	public BaseResult releaseContent(@RequestBody ContentPO content) {
		BaseResult baseResult = new BaseResult();
		if (null != content) {
			/*
			 * if (null == content.getEmail() || "".equals(content.getEmail())) {
			 * baseResult.setCode(StateCodeInformationUtil.EMAIL_IS_NULL); return
			 * baseResult; } if (!ValidationUtil.isEmail(content.getEmail())) {
			 * baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID); return
			 * baseResult; } if (null == content.getTitle() ||
			 * "".equals(content.getTitle())) {
			 * baseResult.setCode(StateCodeInformationUtil.TITLE_IS_NULL); return
			 * baseResult; } if (null == content.getContent() ||
			 * "".equals(content.getContent())) {
			 * baseResult.setCode(StateCodeInformationUtil.CONTENT_IS_NULL); return
			 * baseResult; } if (null == content.getContentTypeId() ||
			 * "".equals(content.getContentTypeId())) {
			 * baseResult.setCode(StateCodeInformationUtil.CONTENT_TYPE_ID_IS_NULL); return
			 * baseResult; } if (null == content.getCategoryId() ||
			 * "".equals(content.getCategoryId())) {
			 * baseResult.setCode(StateCodeInformationUtil.CATEGORY_ID_IS_NULL); return
			 * baseResult; } if (null == content.getSponsorId() ||
			 * "".equals(content.getSponsorId())) {
			 * baseResult.setCode(StateCodeInformationUtil.SPONSOR_ID_IS_NULL); return
			 * baseResult; } if (null == content.getFeaturedMediaId() ||
			 * "".equals(content.getFeaturedMediaId())) {
			 * baseResult.setCode(StateCodeInformationUtil.FEATURED_MEDIA_ID_IS_NULL);
			 * return baseResult; } if (null == content.getIsPrivate() ||
			 * "".equals(content.getIsPrivate())) {
			 * baseResult.setCode(StateCodeInformationUtil.IS_PRIVATE_IS_NULL); return
			 * baseResult; } if (null == content.getIsComplete() ||
			 * "".equals(content.getIsComplete())) {
			 * baseResult.setCode(StateCodeInformationUtil.IS_COMPLETE_IS_NULL); return
			 * baseResult; } if (null == content.getIsPublishNow() ||
			 * "".equals(content.getIsPublishNow())) {
			 * baseResult.setCode(StateCodeInformationUtil.IS_PUBLISH_NOW_IS_NULL); return
			 * baseResult; }
			 */
			String[] status = { "1", "2", "3", "4" };
			System.out.println(Arrays.asList(status).contains("1"));
			if (!Arrays.asList(status).contains(content.getStatus() + "")) {
				baseResult.setCode(StateCodeInformationUtil.STATUS_IS_WRONG);
				return baseResult;
			}
			baseResult = contentReleaseService.releaseContent(content);
		} else {
			baseResult.setCode(StateCodeInformationUtil.RELEASE_CONTENT_IS_NULL);
			return baseResult;
		}

		return baseResult;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/public/findAllContents")
	@ResponseBody
	public BaseResult findAllPublicContent(@RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		/*
		 * if (!ValidationUtil.isBlank(query.getEmail())) { if
		 * (!ValidationUtil.isEmail(query.getEmail())) {
		 * baseResult.setCode(StateCodeInformationUtil.EMAIL_TOKEN_INVALID); return
		 * baseResult; } }
		 */

		// is private : true(private) , false(public)
		query.setIsPrivate(false);
		// is admin : true(admin) , false(not admin)
		// query.setIsAdmin(false);

		return contentReleaseService.findAll(query);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/findAllContents")
	@ResponseBody
	public BaseResult findAllContent(OAuth2Authentication auth, HttpServletRequest request, @RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();

		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		Map<String, Object> userMap = null;
		if(null != auth) {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
			userMap = accessToken.getAdditionalInformation();
		}else {
			userMap = JwtTokenUtil.parserJavaWebToken(request.getHeader("Authorization").substring(7), secret);
		}
		query.setEmail(userMap.get("user_name").toString());
		return contentReleaseService.findAll(query);
	}

	//@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/admin/findAllContents")
	@ResponseBody
	public BaseResult findAllAdminContent(OAuth2Authentication auth, HttpServletRequest request, @RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();

		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		query.setIsAdmin(true);
		Map<String, Object> userMap = null;
		if(null != auth) {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
			userMap = accessToken.getAdditionalInformation();
		}else {
			userMap = JwtTokenUtil.parserJavaWebToken(request.getHeader("Authorization").substring(7), secret);
		}
		query.setEmail(userMap.get("user_name").toString());
		return contentReleaseService.findAll(query);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/public/trending")
	@ResponseBody
	public BaseResult findAllPublicContentsSortReadNumber(@RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		// query.setIsAdmin(false);

		// is private : true(private) , false(public)
		query.setIsPrivate(false);

		return contentReleaseService.findAllSortReadNumber(query);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/trending")
	@ResponseBody
	public BaseResult findAllContentsSortReadNumber(OAuth2Authentication auth, HttpServletRequest request, @RequestBody QueryPO query) {
		BaseResult baseResult = new BaseResult();
		if (null == query) {
			baseResult.setCode(StateCodeInformationUtil.QUERY_IS_NULL);
			return baseResult;
		}
		Map<String, Object> userMap = null;
		if(null != auth) {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
			userMap = accessToken.getAdditionalInformation();
		}else {
			userMap = JwtTokenUtil.parserJavaWebToken(request.getHeader("Authorization").substring(7), secret);
		}
		query.setEmail(userMap.get("user_name").toString());
		return contentReleaseService.findAllSortReadNumber(query);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/findOneContents")
	@ResponseBody
	public BaseResult findOneCotentById(OAuth2Authentication auth, String id, HttpServletRequest request) {
		BaseResult baseResult = new BaseResult();
		if (null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_ID_IS_NULL);
			return baseResult;
		}
		Map<String, Object> userMap = null;
		if(null != auth) {
			final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
			userMap = accessToken.getAdditionalInformation();
		}else {
			userMap = JwtTokenUtil.parserJavaWebToken(request.getHeader("Authorization").substring(7), secret);
		}
		baseResult = contentReleaseService.findOneById(id, userMap.get("user_name").toString());
		return baseResult;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/public/findOneContents")
	@ResponseBody
	public BaseResult findOnePublicCotentById(String id) {
		BaseResult baseResult = new BaseResult();
		if (null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_ID_IS_NULL);
			return baseResult;
		}
		baseResult = contentReleaseService.findOneById(id, null);
		return baseResult;
	}

//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/updateContent")
	@ResponseBody
	public BaseResult updateContent(@RequestBody ContentPO content) {
		BaseResult baseResult = new BaseResult();

		if (null == content) {
			baseResult.setCode(StateCodeInformationUtil.CONTENT_IS_NULL);
			return baseResult;
		}
		
		if (null == content.getId() || "".equals(content.getId())) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}

		String[] status = { "1", "2", "3", "4" };
		System.out.println(Arrays.asList(status).contains("1"));
		if (!Arrays.asList(status).contains(content.getStatus() + "")) {
			baseResult.setCode(StateCodeInformationUtil.STATUS_IS_WRONG);
			return baseResult;
		}
		baseResult = contentReleaseService.updatePost(content);
		return baseResult;
	}
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/updateContentRelativeTopicById")
	@ResponseBody
	public BaseResult updateContentRelativeTopicById(@RequestBody ContentPO contentPO) {
		BaseResult baseResult = new BaseResult();
		if(null == contentPO.getId() || "".equals(contentPO.getId()) || null == contentPO.getRelativeTopics() || 0 == contentPO.getRelativeTopics().length) {
			baseResult.setCode(StateCodeInformationUtil.ERROR);
			return baseResult;
		}
		baseResult = contentReleaseService.updateContentRelativeTopicById(contentPO.getId(), contentPO.getRelativeTopics());
		return baseResult;
	}
	
//	@PreAuthorize("hasRole('CMSADMIN') OR hasRole('DSODADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/deleteOneById")
	@ResponseBody
	public BaseResult deleteOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = contentReleaseService.deleteOneById(id);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/visualEssay/save")
	@ResponseBody
	public BaseResult saveVisualEssay(@RequestBody VisualEssay visualEssay) {
		BaseResult baseResult = new BaseResult();
//		if(null == visualEssay.getParentId() || "".equals(visualEssay.getParentId())) {
//			baseResult.setCode(StateCodeInformationUtil.PARENTID_IS_NULL);
//			return baseResult;
//		}
		baseResult = contentReleaseService.saveVisualEssay(visualEssay);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/visualEssay/update")
	@ResponseBody
	public BaseResult updateVisualEssay(@RequestBody VisualEssay visualEssay) {
		BaseResult baseResult = new BaseResult();
		if(null == visualEssay.getId() || "".equals(visualEssay.getId())) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		//if(null == visualEssay.getParentId() || "".equals(visualEssay.getParentId())) {
			//baseResult.setCode(StateCodeInformationUtil.PARENTID_IS_NULL);
			//return baseResult;
		//}
		baseResult = contentReleaseService.saveVisualEssay(visualEssay);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/visualEssay/findOneById")
	@ResponseBody
	public BaseResult findOneById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = contentReleaseService.findOneById(id);
		return baseResult;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/v1/content/visualEssay/deleteVisualEssayById")
	@ResponseBody
	public BaseResult deleteVisualEssayById(@RequestParam String id) {
		BaseResult baseResult = new BaseResult();
		if(null == id || "".equals(id)) {
			baseResult.setCode(StateCodeInformationUtil.ID_IS_NULL);
			return baseResult;
		}
		baseResult = contentReleaseService.deleteVisualEssayById(id);
		return baseResult;
	}
}
