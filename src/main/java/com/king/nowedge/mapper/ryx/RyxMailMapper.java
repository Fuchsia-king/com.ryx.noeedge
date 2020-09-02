package com.king.nowedge.mapper.ryx;

import org.apache.ibatis.annotations.Mapper;

import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.excp.BaseDaoException;

@Mapper
public interface RyxMailMapper {
  
    public void processRegister(RyxUsersDTO user, String serverName)throws BaseDaoException;
	
}
