package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.GroupDTO;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper {
	
	public Boolean create(GroupDTO dto) throws BaseDaoException; 
	
	
}
