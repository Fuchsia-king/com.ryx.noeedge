package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxUserMessageDTO;
import com.king.nowedge.query.ryx.RyxUserMessageQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxUserMessageMapper {	
	
	public RyxUserMessageDTO getById(Long id)throws BaseDaoException;

	public List<RyxUserMessageDTO> query(RyxUserMessageQuery query) throws BaseDaoException;

	public Integer countQuery(RyxUserMessageQuery query) throws BaseDaoException;	
	
	public Long create(RyxUserMessageDTO admin) throws BaseDaoException;

	public Boolean update(RyxUserMessageDTO dto) throws BaseDaoException;

	public Boolean delete(RyxUserMessageDTO dto) throws BaseDaoException;	
	
	public Boolean deleteByUserIdAndMsgId(RyxUserMessageDTO dto) throws BaseDaoException;	
}
