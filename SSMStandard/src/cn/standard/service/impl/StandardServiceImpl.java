package cn.standard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.standard.dao.StandardMapper;
import cn.standard.pojo.Standard;
import cn.standard.service.StandardService;

@Transactional
@Service("standarService")
public class StandardServiceImpl implements StandardService{
	@Resource
	private StandardMapper standardMapper;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Standard> findStandardList(Integer id,String keys,String stdNum,int pageIndex, int pageSize) {
		return standardMapper.getStandardList(id,keys,stdNum,pageIndex, pageSize);
	}

	@Override
	public Integer deleteStandardById(Integer id) {
		return standardMapper.getStandardDeleteById(id);
	}

	@Override
	public Integer updateStandardById(Standard standard) {
		return standardMapper.getStandardUpdateById(standard);
	}

	@Override
	public Integer insertStandard(Standard standard) {
		return standardMapper.getStandardInsert(standard);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public int findTotalCount(String keys) {
		return standardMapper.getTotalCount(keys);
	}

}
