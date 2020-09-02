package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.query.LoreQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoreMapper {
	
	public Boolean create(LoreDTO loreDTO) throws BaseDaoException;
	
	public List<LoreDTO> query(LoreQuery loreQuery) throws BaseDaoException;
	
	public List<LoreDTO> search(LoreQuery loreQuery) throws BaseDaoException;
	
	Integer countQuery(LoreQuery loreQuery) throws BaseDaoException;	
	
	Integer countSearch(LoreQuery loreQuery) throws BaseDaoException;
	
	public LoreDTO queryById(Long id) throws BaseDaoException;
	
	public LoreDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(LoreDTO loreDTO) throws BaseDaoException;

	public Boolean updateEcology(LoreDTO loreDTO) throws BaseDaoException;
	 

}
