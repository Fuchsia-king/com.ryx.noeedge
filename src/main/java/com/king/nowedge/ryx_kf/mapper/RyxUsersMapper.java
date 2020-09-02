package com.king.nowedge.ryx_kf.mapper;

import com.king.nowedge.ryx_kf.pojo.RyxUsers;
import com.king.nowedge.ryx_kf.pojo.RyxUsersExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RyxUsersMapper {
    long countByExample(RyxUsersExample example);

    int deleteByExample(RyxUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RyxUsers record);

    int insertSelective(RyxUsers record);

    List<RyxUsers> selectByExampleWithBLOBs(RyxUsersExample example);

    List<RyxUsers> selectByExample(RyxUsersExample example);

    RyxUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RyxUsers record, @Param("example") RyxUsersExample example);

    int updateByExampleWithBLOBs(@Param("record") RyxUsers record, @Param("example") RyxUsersExample example);

    int updateByExample(@Param("record") RyxUsers record, @Param("example") RyxUsersExample example);

    int updateByPrimaryKeySelective(RyxUsers record);

    int updateByPrimaryKeyWithBLOBs(RyxUsers record);

    int updateByPrimaryKey(RyxUsers record);
}