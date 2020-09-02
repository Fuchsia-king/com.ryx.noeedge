package com.king.nowedge.ryx_kf.mapper;

import com.king.nowedge.ryx_kf.pojo.RyxUserExt;
import com.king.nowedge.ryx_kf.pojo.RyxUserExtExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RyxUserExtMapper {
    long countByExample(RyxUserExtExample example);

    int deleteByExample(RyxUserExtExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RyxUserExt record);

    int insertSelective(RyxUserExt record);

    List<RyxUserExt> selectByExample(RyxUserExtExample example);

    RyxUserExt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RyxUserExt record, @Param("example") RyxUserExtExample example);

    int updateByExample(@Param("record") RyxUserExt record, @Param("example") RyxUserExtExample example);

    int updateByPrimaryKeySelective(RyxUserExt record);

    int updateByPrimaryKey(RyxUserExt record);
}