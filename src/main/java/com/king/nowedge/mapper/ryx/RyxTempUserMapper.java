package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxTempUserDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.query.ryx.RyxTempUserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RyxTempUserMapper {
	
	public Boolean create(RyxTempUserDTO dto) throws BaseDaoException; 

	public List<RyxTempUserDTO> query(RyxTempUserQuery query) throws BaseDaoException;
	
	public RyxTempUserDTO getTempUserByRandomMobile(RyxTempUserQuery query) throws BaseDaoException;

	public Integer update(RyxTempUserDTO ryxTempUserDTO);

	public Integer countQuery(RyxTempUserQuery query)throws BaseDaoException;

	public Boolean createOrUpdate(RyxTempUserDTO tempUserDTO) throws BaseDaoException;

	public Boolean updateByTelephone(RyxTempUserDTO tempUserDTO)throws BaseDaoException;

}
