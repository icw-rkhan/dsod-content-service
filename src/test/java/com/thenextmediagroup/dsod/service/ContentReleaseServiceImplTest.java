package com.thenextmediagroup.dsod.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thenextmediagroup.dsod.ContentApplication;
import com.thenextmediagroup.dsod.model.VisualEssay;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.ContentPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

import net.sf.json.JSONArray;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class ContentReleaseServiceImplTest {
	
	@Autowired
	ContentReleaseService service;

	@Test
	@Rollback(true)
	public void testReleaseContent() {
		ContentPO content = new ContentPO();
		service.releaseContent(content);
		
	}

	@Test
	@Rollback(true)
	public void testFindAll() {
		QueryPO query = new QueryPO();
		BaseResult baseResult = service.findAll(query);
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testFindOneByIdStringString() {
		BaseResult baseResult = service.findOneById("4dcfe56594d84990be8d9a7100c87111","qwefeng888@163.com");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testFindOneByIdAndSearchValue() {
		BaseResult baseResult = service.findOneByIdAndSearchValue("4dcfe56594d84990be8d9a7100c87111", "aa", "qwefeng888@163.com");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testFindAllSortReadNumber() {
		QueryPO query = new QueryPO();
		query.setLimit(10);
		BaseResult baseResult = service.findAllSortReadNumber(query);
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testUpdatePost() {
		ContentPO content = new ContentPO();
		content.setId("4dcfe56594d84990be8d9a7100c87111");
		service.updatePost(content);
		
	}

	@Test
	@Rollback(true)
	public void testUpdateContentRelativeTopicById() {
		ContentPO content = new ContentPO();
		content.setId("4dcfe56594d84990be8d9a7100c87111");
		service.updateContentRelativeTopicById("4dcfe56594d84990be8d9a7100c87111", new String[5]);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteOneById() {
		BaseResult baseResult = service.deleteOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testSaveVisualEssay() {
		VisualEssay visualEssay = new VisualEssay();
		service.saveVisualEssay(visualEssay);
		
	}

	@Test
	@Rollback(true)
	public void testFindOneByIdString() {
		BaseResult baseResult = service.findOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteVisualEssayById() {
		BaseResult baseResult = service.deleteOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
