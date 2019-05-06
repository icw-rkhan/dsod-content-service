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
import com.thenextmediagroup.dsod.web.dto.SponsorPO;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class SponsorServiceImplTest {
	
	@Autowired
	SponsorService service;

	@Test
	@Rollback(true)
	public void testSave() {
		SponsorPO sponsorPO = new SponsorPO();
		service.save(sponsorPO);
		
	}

	@Test
	@Rollback(true)
	public void testGetAllSponsor() {
		BaseResult baseResult = service.getAllSponsor();
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
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
	public void testEditSponsorById() {
		SponsorPO sponsorPO = new SponsorPO();
		sponsorPO.setId("4dcfe56594d84990be8d9a7100c87111");
		service.editSponsorById(sponsorPO);
		
	}

}
