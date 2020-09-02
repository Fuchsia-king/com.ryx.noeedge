package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.AttrValueDTO;
import com.king.nowedge.dto.query.AttrValueQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttrValueMapper {
	
	public Boolean create(AttrValueDTO attrValueDTO) throws BaseDaoException;
	
	public Boolean createOrUpdate(AttrValueDTO attrValueDTO) throws BaseDaoException;

	public List<AttrValueDTO> query(AttrValueQuery attrValueQuery) throws BaseDaoException;
	
	public List<AttrValueDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(AttrValueQuery attrValueQuery)throws BaseDaoException;
	
	public Integer countByUid(String uid)throws BaseDaoException;

	public Boolean update(AttrValueDTO attrValueDTO)throws BaseDaoException;;
	
	public Boolean update1(AttrValueDTO attrValueDTO)throws BaseDaoException;;
	
	public AttrValueDTO queryByUid(String uid)throws BaseDaoException;
	
	public AttrValueDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}
