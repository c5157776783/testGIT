package cn.standard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.standard.pojo.Standard;

public interface StandardMapper {
	public List<Standard> getStandardList(@Param("id")Integer id,@Param("keys")String keys,@Param("stdNum")String stdNum,@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize);
	
	public Integer getStandardDeleteById(@Param("id")Integer id);
	
	public Integer getStandardUpdateById(Standard standard);
	
	public Integer getStandardInsert(Standard standard);
	
	public int getTotalCount(@Param("keys")String keys);
}
