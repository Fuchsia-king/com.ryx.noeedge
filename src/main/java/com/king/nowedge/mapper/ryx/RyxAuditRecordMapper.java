package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxAuditRecordDTO;
import com.king.nowedge.query.ryx.RyxAuditRecordQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxAuditRecordMapper {
	
	public Boolean create(RyxAuditRecordDTO dto)throws BaseDaoException;	
	
	public List<RyxAuditRecordDTO> query(RyxAuditRecordQuery query)throws BaseDaoException;

	public Integer countQuery(RyxAuditRecordQuery query)throws BaseDaoException;
}
