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
import com.thenextmediagroup.dsod.web.dto.ContentTypePO;

import net.sf.json.JSONArray;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class ContentTypeServiceImplTest {
	
	@Autowired
	ContentTypeService service;

	@Test
	@Rollback(true)
	public void testFindAllContentType() {
		BaseResult baseResult = service.findAllContentType();
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testSave() {
		ContentTypePO contentTypePO = new ContentTypePO();
		service.save(contentTypePO);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteOneById() {
		BaseResult baseResult = service.deleteOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
