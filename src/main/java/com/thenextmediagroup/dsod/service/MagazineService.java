package com.thenextmediagroup.dsod.service;

import com.thenextmediagroup.dsod.util.BaseResult;
import com.thenextmediagroup.dsod.web.dto.MagazinePO;
import com.thenextmediagroup.dsod.web.dto.MagazineQueryPO;
import com.thenextmediagroup.dsod.web.dto.QueryPO;

public interface MagazineService {
	
	BaseResult save(MagazinePO magazinePO);
	
	BaseResult findAll(MagazineQueryPO queryPO);
	
	BaseResult findOneById(String id);
	
	BaseResult deleteById(String id);
	
	BaseResult update(MagazinePO magazinePO);
	
	BaseResult findContentByMagazine(String magazineId ,String searchValue);
}
