package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.comm.MemberDTO;
import com.king.nowedge.query.MemberQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
	
	public Boolean create(MemberDTO memberDTO) throws BaseDaoException; 

	public List<MemberDTO> query(MemberQuery memberQuery) throws BaseDaoException;
	
	public Integer countQuery(MemberQuery memberQuery)throws BaseDaoException;

	public Boolean update(MemberDTO memberDTO)throws BaseDaoException;; 
	
	public MemberDTO queryByUid(String uid)throws BaseDaoException;
	
	public MemberDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(MemberDTO memberDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException; 
	
	public List<MemberDTO> queryAll() throws BaseDaoException;
	
}
