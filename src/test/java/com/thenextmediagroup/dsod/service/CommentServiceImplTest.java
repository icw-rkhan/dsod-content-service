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
import com.thenextmediagroup.dsod.web.dto.CommentPO;
import com.thenextmediagroup.dsod.web.dto.CommentQueryPO;

import net.sf.json.JSONArray;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class CommentServiceImplTest {
	
	@Autowired
	CommentService service;

	@Test
	@Rollback(true)
	public void testSaveComment() {
		CommentPO commentPO = new CommentPO();
		service.saveComment(commentPO);
		
	}

	@Test
	@Rollback(true)
	public void testFindAllCommentByContentId() {
		CommentQueryPO commentQueryPO = new CommentQueryPO();
		commentQueryPO.setContentId("4dcfe56594d84990be8d9a7100c87111");
		commentQueryPO.setLimit(10);
		commentQueryPO.setSkip(0);
		BaseResult baseResult = service.findAllCommentByContentId(commentQueryPO);
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
