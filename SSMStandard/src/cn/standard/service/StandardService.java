package cn.standard.service;

import java.util.List;

import cn.standard.pojo.Standard;

public interface StandardService {
	
	public List<Standard> findStandardList(Integer id,String keys,String stdNum,int pageIndex,int pageSize);
	
	public Integer deleteStandardById(Integer id);
	
	public Integer updateStandardById(Standard standard);
	
	public Integer insertStandard(Standard standard);
	
	public int findTotalCount(String keys);
}
