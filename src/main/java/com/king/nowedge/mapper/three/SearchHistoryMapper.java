package com.king.nowedge.mapper.three;

import com.king.nowedge.dto.comm.LoreInputDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SearchHistoryMapper {
    @Insert("insert into lore_input (str,user_id) values (#{str},#{userId})")
    void saveHistory(LoreInputDTO loreInputDTO);
    @Delete("delete from lore_input where user_id = #{userId}")
    void deleteHistory(LoreInputDTO loreInputDTO);
}
