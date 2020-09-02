package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxMessageDTO;
import com.king.nowedge.dto.ryx.query.RyxMessageQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxMessageMapper {	
	
	public RyxMessageDTO getById(Long id)throws BaseDaoException;

	public List<RyxMessageDTO> query(RyxMessageQuery query) throws BaseDaoException;

	public Integer countQuery(RyxMessageQuery query) throws BaseDaoException;	
	
	public Integer countMyQuery(RyxMessageQuery query) throws BaseDaoException;	
	
	public Long create(RyxMessageDTO admin) throws BaseDaoException;

	public Boolean update(RyxMessageDTO dto) throws BaseDaoException;

	public Boolean delete(RyxMessageDTO dto) throws BaseDaoException;	
	
	public List<RyxMessageDTO> queryMyMessage(RyxMessageQuery query) throws BaseDaoException;
}
