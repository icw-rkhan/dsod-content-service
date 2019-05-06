package com.thenextmediagroup.dsod.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thenextmediagroup.dsod.ContentApplication;
import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.BookmarkPO;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class BookmarkServiceImplTest {

	@Autowired
	BookmarkService service;

	@Test
	@Rollback(true)
	public void testSave() {
		BookmarkPO bookmarkPO = new BookmarkPO();
		service.save(bookmarkPO);
		
	}

	@Test
	@Rollback(true)
	public void testGetALLByEmail() {
		BookmarkPO bookmarkPO = new BookmarkPO();
		bookmarkPO.setEmail("qwefeng888@163.com");
		BaseResult baseResult = service.getALLByEmail(bookmarkPO);
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteOneById() {
		BaseResult baseResult = service.deleteOneById("4dcfe56594d84990be8d9a7100c87111");
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteOneByEmailAndContentId() {
		BaseResult baseResult = service.deleteOneByEmailAndContentId("qwefeng888@163.com",
				"4dcfe56594d84990be8d9a7100c87111");
		String json = JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
