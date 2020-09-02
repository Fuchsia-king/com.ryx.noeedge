package com.king.nowedge.service.ryx2.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumTeacherType;
import com.king.nowedge.dto.enums.EnumUserLevel;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUserFollowTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxTeacherQuery;
import com.king.nowedge.dto.ryx.query.RyxUserFollowTeacherQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxTeacherMapper;
import com.king.nowedge.mapper.ryx.RyxUserFollowTeacherMapper;
import com.king.nowedge.mapper.ryx.RyxUserMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx2.RyxTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxTeacherService")
public class RyxTeacherServiceImpl   extends BaseService implements RyxTeacherService {
	

	@Autowired
	RyxTeacherMapper ryxTeacherMapper;
	

	@Autowired
	RyxUserMapper ryxUserMapper;
	
	@Override
	public ResultDTO<List<RyxTeacherDTO>> getList() {
		return this.getHotestTeacher();
//		return list;
	}
	
	
	@Override
	public ResultDTO<List<RyxTeacherDTO>> getHotestTeacher() {
		
		
		ResultDTO<List<RyxTeacherDTO>> result = null;
		try{			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(8);;
			query.setOrderBy("sort");
			query.setSooort("desc");
			List<RyxTeacherDTO> val = ryxTeacherMapper.query(query );
			result = new ResultDTO<List<RyxTeacherDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxTeacherDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxTeacherDTO>>("error", e.getMessage());
		}
		return result;
		
//		String sql = "select * from ryx_teacher t order by t.sort desc limit 0, 8";
//		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper<TeacherDTO>(TeacherDTO.class));
	}
	
	
	@Override
	public ResultDTO<RyxTeacherQuery> queryTeacher(RyxTeacherQuery query) {
		
		
		ResultDTO<RyxTeacherQuery> result = null;
		try{			
			List<RyxTeacherDTO> val = ryxTeacherMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxTeacherMapper.countQuery(query));
			result = new ResultDTO<RyxTeacherQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<RyxTeacherQuery> queryTeacher1(RyxTeacherQuery query) {
		
		
		ResultDTO<RyxTeacherQuery> result = null;
		try{			
			List<Long> val = ryxTeacherMapper.query1(query);
			query.setList(val);
			query.setTotalItem(ryxTeacherMapper.countQuery1(query));
			result = new ResultDTO<RyxTeacherQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Integer> countQueryTeacher(RyxTeacherQuery query) {
		
		
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxTeacherMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
		
//		String sql = new StringBuilder("select count(distinct t.id) from ryx_teacher t left join ryx_course c on t.id=c.tid").append(hql).toString();
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForInt(sql);
	}
	
//	private String getHQL(String category, String keywords, int currentPage) {
//		StringBuilder sb = new StringBuilder(" where 1=1");
//		log.debug("keywords=" + keywords);
//		if (!Tools.isEmpty(category)) {
//			sb.append(" and c.category='").append(category).append("'");
//		}
//		if (!Tools.isEmpty(keywords)) {
//			sb.append(" and t.nickname like '%").append(keywords).append("%'");
//		} else {
//			sb.append(" and t.flag=0");
//		}
//		return sb.toString();
//	}
	
	
	@Override
	public ResultDTO<RyxTeacherDTO> getTeacherById(Long id) {
		ResultDTO<RyxTeacherDTO> result = null;
		try{			
			RyxTeacherDTO val = ryxTeacherMapper.getTeacherById(id);
			result = new ResultDTO<RyxTeacherDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxTeacherDTO> getTeacherByNick(String nick) {
		ResultDTO<RyxTeacherDTO> result = null;
		try{			
			RyxTeacherDTO val = ryxTeacherMapper.getTeacherByNick(nick);
			result = new ResultDTO<RyxTeacherDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<RyxTeacherDTO> getTeacherByUserId(Long userId) {
		ResultDTO<RyxTeacherDTO> result = null;
		try{			
			RyxTeacherDTO val = ryxTeacherMapper.getTeacherByUserId(userId);
			result = new ResultDTO<RyxTeacherDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	//首页名师
	@Override
	public ResultDTO<List<RyxTeacherDTO>> getMostHotTeacher() {
		
		ResultDTO<List<RyxTeacherDTO>> result = null;
		try{			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxTeacherDTO> val = ryxTeacherMapper.query(query );
			result = new ResultDTO<List<RyxTeacherDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxTeacherDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxTeacherDTO>>("error", e.getMessage());
		}
		return result;
		
//		String sql = "select * from ryx_teacher t where t.flag=0";
//		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper<TeacherDTO>(TeacherDTO.class));
	}


	@Override
	public ResultDTO<Integer> countQueryTeacher1(RyxTeacherQuery query) {
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxTeacherMapper.countQuery1(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<RyxTeacherQuery> getMyFollowTeacher(RyxTeacherQuery query) {
		ResultDTO<RyxTeacherQuery> result = null;
		try{			
			List<RyxTeacherDTO> val = ryxTeacherMapper.getMyFollowTeacher(query);
			query.setList(val);
			query.setTotalItem(ryxTeacherMapper.getMyFollowTeacherCount(query));
			result = new ResultDTO<RyxTeacherQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxTeacherQuery>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> updateTeacher(RyxTeacherDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxTeacherMapper.update(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Long> createTeacher(RyxTeacherDTO dto) {
		ResultDTO<Long> result = null;
		try{
			Long val = ryxTeacherMapper.create(dto);
			
			
			/**
			 * 更新用户角色
			 */
			RyxUsersDTO user = new RyxUsersDTO();
			user.setId(dto.getUid());
			if(EnumTeacherType.ORG.getCode().equals(dto.getType())){
				user.setFlag(EnumUserLevel.ORG_TEACHER.getCode());
			}
			else{
				user.setFlag(EnumUserLevel.PERSONAL_TEACHER.getCode());
			}
			ryxUserMapper.update(user);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Long> createOrUpdateTeacher(RyxTeacherDTO dto) {
		ResultDTO<Long> result = null;
		try{
			Long val = ryxTeacherMapper.createOrUpdate(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	@Autowired
	RyxUserFollowTeacherMapper ryxUserFollowTeacherMapper;
	
	@Override
	public ResultDTO<Boolean> saveUserFollowTeacher(RyxUserFollowTeacherDTO userFollowTeacherDTO) {
		
		ResultDTO<Boolean> result = null;
		try{		
			Boolean val = false;
			List<RyxUserFollowTeacherDTO> list = ryxUserFollowTeacherMapper.getByTeacherIdUserId(userFollowTeacherDTO);
			if(null==list || list.size() ==0){
				val = ryxUserFollowTeacherMapper.create(userFollowTeacherDTO);
			}
			else{
				val = true;
			}
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<List<RyxUserFollowTeacherDTO>> listUserFollowTeacher(Long userId) {
		
		ResultDTO<List<RyxUserFollowTeacherDTO>> result = null;
		try{			
			RyxUserFollowTeacherQuery query = new RyxUserFollowTeacherQuery();
			query.setUid(userId.toString());
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxUserFollowTeacherDTO> val = ryxUserFollowTeacherMapper.query(query);
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>("error", e.getMessage());
		}
		return result;
		
		
	}
	
	@Override
	public ResultDTO<Integer> countQueryUserFollowTeacher(RyxUserFollowTeacherQuery query) {
		
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxUserFollowTeacherMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
		
		
	}

	@Override
	public ResultDTO<List<RyxUserFollowTeacherDTO>> getUserFollowTeacherByTeacherIdAndUserId(Long teacherId, Long userId) {
		
		ResultDTO<List<RyxUserFollowTeacherDTO>> result = null;
		try{			
			RyxUserFollowTeacherQuery query = new RyxUserFollowTeacherQuery();
			query.setUserId(userId);
			query.setTeacherId(teacherId);
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxUserFollowTeacherDTO> val = ryxUserFollowTeacherMapper.query(query );
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxUserFollowTeacherDTO>>("error", e.getMessage());
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<Boolean> deleteUserFollowTeacherByTeacherIdAndUserId(Long teacherId, Long userId) {
		
		ResultDTO<Boolean> result = null;
		try{			
			RyxUserFollowTeacherDTO dto = new RyxUserFollowTeacherDTO();
			dto.setUserId(userId);
			dto.setTeacherId(teacherId);
			Boolean val = ryxUserFollowTeacherMapper.deleteByTeacherIdAndUserId(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
}
