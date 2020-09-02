package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxConfigDTO;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxConfigMapper {	
	
	public RyxConfigDTO getById(Integer id) throws BaseDaoException;
	public RyxConfigDTO getByName(String name)throws BaseDaoException;
}
