package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx2.validate.VideoDTO;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxVideoMapper {	
	

	public Long create(VideoDTO dto) throws BaseDaoException;
	
}
