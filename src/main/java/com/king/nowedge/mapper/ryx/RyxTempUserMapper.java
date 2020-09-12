package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxTempUserDTO;
import com.king.nowedge.query.ryx.RyxTempUserQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxTempUserMapper {
	
	public Boolean create(RyxTempUserDTO dto) throws BaseDaoException; 

	public List<RyxTempUserDTO> query(RyxTempUserQuery query) throws BaseDaoException;
	
	public RyxTempUserDTO getTempUserByRandomMobile(RyxTempUserQuery query) throws BaseDaoException;


	public Integer countQuery(RyxTempUserQuery query)throws BaseDaoException;

	public Boolean createOrUpdate(RyxTempUserDTO tempUserDTO) throws BaseDaoException;

	public Boolean updateByTelephone(RyxTempUserDTO tempUserDTO)throws BaseDaoException;

}
