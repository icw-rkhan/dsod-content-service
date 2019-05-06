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
import com.thenextmediagroup.dsod.web.dto.QueryPO;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类

public class RetrievalServiceImplTest {

	@Autowired
	RetrievalService service;

	@Test
	@Rollback(true)
	public void testRetrievalContentsByType() {
		QueryPO queryPO = new QueryPO();
		queryPO.setLimit(10);
		queryPO.setSearchValue("ss");
		queryPO.setContentTypeId("4dcfe56594d84990be8d9a7100c87111");
		BaseResult baseResult = service.retrievalContentsByType(queryPO);
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testRetrievalContentsByValue() {
		QueryPO queryPO = new QueryPO();
		queryPO.setLimit(10);
		queryPO.setSearchValue("ss");
		BaseResult baseResult = service.retrievalContentsByValue(queryPO);
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testRetrievalContentsByValueAdmin() {
		QueryPO queryPO = new QueryPO();
		queryPO.setLimit(10);
		queryPO.setSearchValue("ss");
		BaseResult baseResult = service.retrievalContentsByValueAdmin(queryPO);
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
