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
import com.thenextmediagroup.dsod.web.dto.MagazinePO;
import com.thenextmediagroup.dsod.web.dto.MagazineQueryPO;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ContentApplication.class) // 指定spring-boot的启动类
public class MagazineServiceImplTest {
	
	@Autowired
	MagazineService service;

	@Test
	@Rollback(true)
	public void testSave() {
		MagazinePO magazinePO = new MagazinePO();
		service.save(magazinePO);
		
	}

	@Test
	@Rollback(true)
	public void testFindAll() {
		MagazineQueryPO magazineQueryPO = new MagazineQueryPO();
		magazineQueryPO.setLimit(10);
		magazineQueryPO.setSkip(0);
		magazineQueryPO.setStatus(1);
		BaseResult baseResult = service.findAll(magazineQueryPO);
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testFindOneById() {
		BaseResult baseResult = service.findOneById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		BaseResult baseResult = service.deleteById("4dcfe56594d84990be8d9a7100c87111");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

	@Test
	@Rollback(true)
	public void testUpdate() {
		MagazinePO magazinePO = new MagazinePO();
		magazinePO.setId("4dcfe56594d84990be8d9a7100c87111");
		service.update(magazinePO);
		
	}

	@Test
	@Rollback(true)
	public void testFindContentByMagazine() {
		BaseResult baseResult = service.findContentByMagazine("4dcfe56594d84990be8d9a7100c87111", "aa");
		String json=JSONArray.fromObject(baseResult).toString();
		System.out.println(json);
		
	}

}
