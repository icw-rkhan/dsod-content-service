package com.thenextmediagroup.dsod.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thenextmediagroup.dsod.ContentApplication;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.AuthorPO;
import com.thenextmediagroup.dsod.web.dto.AuthorQueryPO;

import net.sf.json.JSONArray;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class AuthorServiceImplTest {

	@Autowired
	AuthorService authorService;
	
	@Test
	@Rollback(true)
	public void testSave() {
		AuthorPO authorPO = new AuthorPO();
		authorService.save(authorPO);
		
	}

	@Test
	@Rollback(true)
	public void testGetAllAuthor() {
		AuthorQueryPO authorQueryPO = new AuthorQueryPO();
		authorQueryPO.setLimit("10");
		authorQueryPO.setSkip("0");
		BaseResult baseResult = authorService.getAllAuthor(authorQueryPO);
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteOneById() {
		BaseResult baseResult = authorService.deleteOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testFindOneById() {
		BaseResult baseResult = authorService.findOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testEditOneById() {
		AuthorPO authorPO = new AuthorPO();
		authorPO.set_id("4dcfe56594d84990be8d9a7100c87111");
		authorService.editOneById(authorPO);
		
	}

}
