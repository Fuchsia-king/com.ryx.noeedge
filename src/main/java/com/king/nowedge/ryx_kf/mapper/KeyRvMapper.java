package com.king.nowedge.ryx_kf.mapper;

import com.king.nowedge.ryx_kf.pojo.KeyRv;
import com.king.nowedge.ryx_kf.pojo.KeyRvExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KeyRvMapper {
    long countByExample(KeyRvExample example);

    int deleteByExample(KeyRvExample example);

    int deleteByPrimaryKey(Long id);

    int insert(KeyRv record);

    int insertSelective(KeyRv record);

    List<KeyRv> selectByExample(KeyRvExample example);

    KeyRv selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") KeyRv record, @Param("example") KeyRvExample example);

    int updateByExample(@Param("record") KeyRv record, @Param("example") KeyRvExample example);

    int updateByPrimaryKeySelective(KeyRv record);

    int updateByPrimaryKey(KeyRv record);
}