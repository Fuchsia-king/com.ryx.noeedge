package com.king.nowedge.service.ryx2.impl;

import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.enums.EnumOrderStatus;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.UserHelper;
import com.king.nowedge.mapper.ryx.*;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx2.RyxCourseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("ryxCourseService")
public class RyxCourseServiceImpl extends BaseService implements RyxCourseService {
	private static final Log logger = LogFactory.getLog(RyxCourseServiceImpl.class);

	@Autowired
	RyxCourseMapper courseMapper;
	
	
	@Autowired
	RyxUserEcourseMapper ryxUserEcourseMapper;
	
	
	@Autowired
	RyxCourseOutlineMapper courseOutlineMapper;
	
	@Autowired
	RyxAuditRecordMapper auditReportMapper;
	
	
	@Autowired
	RyxObjectLimitMapper ryxObjectLimitMapper;

	// 获取课程价格
	@Override
	public ResultDTO<Double> getCoursePrice(Long courseId) {

		ResultDTO<Double> result = null;
		try {
			Double val = courseMapper.getCoursePriceById(courseId);
			result = new ResultDTO<Double>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;


	}

	@Override
	public ResultDTO<RyxCourseQuery> queryCourse(RyxCourseQuery query) {

		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.query(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countQuery(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<RyxCourseQuery> queryOnlineCourse(RyxCourseQuery query) {

		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.queryOnline(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countQuery(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	

	@Override
	public ResultDTO<RyxCourseQuery> queryDefaultCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.defaultQuery(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countDefaultQuery(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> queryVideo(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.queryVideo(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countQueryVideo(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> queryMyVideo(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.queryMyVideo(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countQueryMyVideo(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Integer> countQueryCourse(RyxCourseQuery query) {

		ResultDTO<Integer> result = null;
		try {
			Integer val = courseMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxCourseDTO> getCourseById(Long id) {

		ResultDTO<RyxCourseDTO> result = null;
		try {
			RyxCourseDTO val = courseMapper.getCourseById(id);
			result = new ResultDTO<RyxCourseDTO>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

	}

	// 获取课程的价格
	@Override
	public ResultDTO<Double> getCoursePriceById(Long courseId) {

		ResultDTO<Double> result = null;
		try {
			Double val = courseMapper.getCoursePriceById(courseId);
			result = new ResultDTO<Double>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

		// String sql = "select t.price from ryx_course t where t.id=" +
		// courseId;
		// return
		// namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql,
		// Double.class);
	}

	@Override
	public ResultDTO<List<RyxCourseDTO>> getCourseByVId(String vid) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getCourseByVId(vid);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

	}
	
	@Override
	public ResultDTO<List<RyxCourseDTO>> getCourseByIds(String ids) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getByIds(ids);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

	}
	

	// 最新课程
	@Override
	public ResultDTO<List<RyxCourseDTO>> getLastCourse(Integer recordCount) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(recordCount);
			query.setStatus(1);
			query.setFlag(0);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			
			List<RyxCourseDTO> val = courseMapper.query(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	// 猜你喜欢
	@Override
	public ResultDTO<List<RyxCourseDTO>> getCnxh(Integer recordCount,
			String title) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(recordCount);
			query.setStatus(1);
			query.setFlag(0);
			query.setTitle(title);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			List<RyxCourseDTO> val = courseMapper.query(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	// 最多播放
	@Override
	public ResultDTO<List<RyxCourseDTO>> getZdbfCourse(Integer recordCount) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(recordCount);
			query.setStatus(1);
			query.setFlag(0);
			query.setOrderBy("hits");
			query.setSooort("desc");
			List<RyxCourseDTO> val = courseMapper.query(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

		

	}

	// 最具人气
	@Override
	public ResultDTO<List<RyxCourseDTO>> getZjrqCourse(Integer recordCount) {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(recordCount);
			query.setStatus(1);
			query.setFlag(0);
			query.setOrderBy("renqi");
			query.setSooort("desc");
			List<RyxCourseDTO> val = courseMapper.query(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

		
	}

	// 首页线下课程
	@Override
	public ResultDTO<List<RyxCourseDTO>> getOfflineCourse() {

		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(1);
			query.setFlag(1);
			query.setOrderBy("hits");
			query.setSooort("desc");
			List<RyxCourseDTO> val = courseMapper.query(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

	
	}

	// 收藏课程
	@Override
	public ResultDTO<Boolean> collectCourse(Long courseId, Integer userId) {

		ResultDTO<Boolean> result = null;

		return result;

		// this.sessionFactory.getCurrentSession();
		// return false;
	}

	// 更新课程学习数
	@Override
	public ResultDTO<Boolean> updateCourseStudyCount(Long courseId) {

		ResultDTO<Boolean> result = null;
		try {
			Boolean val = courseMapper.updateCourseStudyCount(courseId);
			result = new ResultDTO<Boolean>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

		
	}
	
	@Override
	public ResultDTO<Boolean> refreshCourse(RyxCourseDTO ryxCourseDTO) {
		ResultDTO<Boolean> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxCourseMapper mapper = sqlSession.getMapper(RyxCourseMapper.class);
			courseMapper.refresh(ryxCourseDTO);
//			sqlSession.commit();
			result =  new ResultDTO<Boolean>(true);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}finally {
//			sqlSession.close();
		}
		
		return result ;
		
	}
	
	
	// 更新课程学习数
		@Override
		public ResultDTO<Boolean> updateCourse(RyxCourseDTO course) {

			ResultDTO<Boolean> result = null;
			try {
				Boolean val = courseMapper.update(course);
				result = new ResultDTO<Boolean>(val);
			} catch (BaseDaoException e) {
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
			}
			return result;
		}
		
		
		
		@Override
		public ResultDTO<Boolean> updateCourse1(RyxCourseDTO course) {

			ResultDTO<Boolean> result = null;
			try {
				Boolean val = courseMapper.update1(course);
				result = new ResultDTO<Boolean>(val);
			} catch (BaseDaoException e) {
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
			}
			return result;
		}

	// 更新课程查看数
	@Override
	public ResultDTO<Integer> updateCourseViewCount(Long courseId) {

		ResultDTO<Integer> result = null;
		try {
			Integer val = courseMapper.updateCourseViewCount(courseId);
			result = new ResultDTO<Integer>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

		// Query query =
		// sessionFactory.getCurrentSession().createQuery("update CourseDTO c set c.hits=c.hits+1 where c.id=:id");
		// query.setInteger("id", courseId);
		// return query.executeUpdate();
	}

	// 未过期的课程
	@Override
	public ResultDTO<List<RyxCourseDTO>> getMyUnexpiredCourse(Long userId) {
		
		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setUserId(userId);
			List<RyxCourseDTO> val = courseMapper.getMyUnexpiredCourse(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		
		return result;
		
		
		// String sql =
		// "select t.*,d.*,c.* from ryx_order t join ryx_order_detail d on t.id=d.order_id join ryx_course c on d.obj_id=c.id where t.status = 2 and c.flag=0 and t.order_uid="
		// + userId
		// + " and d.limit_time>"
		// + System.currentTimeMillis()
		// / 1000;
		// return namedParameterJdbcTemplate.getJdbcOperations().query(sql,
		// new RowMapper<CourseDTO>() {
		// private CourseDTO course = null;
		//
		// @Override
		// public CourseDTO mapRow(ResultSet rs, int arg1)
		// throws SQLException {
		// course = new CourseDTO();
		// course.setId(rs.getInt("c.id"));
		// course.setTitle(rs.getString("c.title"));
		// course.setImageUrl(rs.getString("c.image_url"));
		// course.setPrice(rs.getDouble("c.price"));
		// course.setStudyCount(rs.getInt("c.study_count"));
		// return course;
		// }
		//
		// });
	}

	// 已过期的课程
	@Override
	public ResultDTO<List<RyxCourseDTO>> getMyExpiredCourse(Long userId) {
		
		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setUserId(userId);
			List<RyxCourseDTO> val = courseMapper.getMyExpiredCourse(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	// 线下课程
	@Override
	public ResultDTO<List<RyxCourseDTO>> getMyOfflineCourse(Long userId) {
		
		
		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setUserId(userId);
			List<RyxCourseDTO> val = courseMapper.getMyOfflineCourse(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		
		
		return result;
		
		
		// String sql =
		// "select t.*,d.*,c.* from ryx_order t join ryx_order_detail d on t.id=d.order_id join 
		//	ryx_course c on d.obj_id=c.id where t.status = 2 and c.flag=1 and t.order_uid="
		// + userId;
		// return namedParameterJdbcTemplate.getJdbcOperations().query(sql,
		// new RowMapper<CourseDTO>() {
		//
		// @Override
		// public CourseDTO mapRow(ResultSet rs, int arg1)
		// throws SQLException {
		// CourseDTO course = new CourseDTO();
		// course.setId(rs.getInt("c.id"));
		// course.setTitle(rs.getString("c.title"));
		// course.setImageUrl(rs.getString("c.image_url"));
		// course.setPrice(rs.getDouble("c.price"));
		// course.setStudyCount(rs.getInt("c.study_count"));
		// return course;
		// }
		//
		// });
	}

	@Override
	public ResultDTO<RyxCourseQuery> getMyOfflineCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyOfflineCourse(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyOfflineCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxCourseQuery> getMyExpiredCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyExpiredCourse(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyExpiredCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> getMyExpiredCourse1(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyExpiredCourse1(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyExpiredCount1(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> getMyExpiredCourse2(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxObjectLimitDTO> val = courseMapper.getMyExpiredCourse2(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyExpiredCount2(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	@Override
	public ResultDTO<RyxCourseQuery> getMyUnexpiredCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyUnexpiredCourse(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyUnexpiredCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Integer> getMyUnexpiredCourseCount1(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyUnexpiredCount1(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Deprecated
	@Override
	public ResultDTO<Integer> getMyUnexpiredCourseCount(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyUnexpiredCount(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> getMyUnexpiredCourse1(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyUnexpiredCourse1(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyUnexpiredCount1(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Integer> getMyUnexpiredCount1(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyUnexpiredCount1(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Integer> getMyUnexpiredCount2(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyUnexpiredCount2(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<RyxCourseQuery> getMyUnexpiredCourse2(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxObjectLimitDTO> val = courseMapper.getMyUnexpiredCourse2(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyUnexpiredCount2(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> getMyFollowCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getMyFollowCourse(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyFollowCourseCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Long> createCourse(RyxCourseDTO course) {
		ResultDTO<Long> result = null;
		try {
			Long val = courseMapper.create(course);
			result = new ResultDTO<Long>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Long> createCourseOutline(RyxCourseOutlineDTO dto) {
		ResultDTO<Long> result = null;
		try {
			Long val = courseOutlineMapper.create(dto);
			result = new ResultDTO<Long>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> updateCourseOutline(RyxCourseOutlineDTO dto) {
		ResultDTO<Boolean> result = null;
		try {
			Boolean val = courseOutlineMapper.update(dto);
			result = new ResultDTO<Boolean>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteCourseOutline(RyxCourseOutlineDTO dto) {
		ResultDTO<Boolean> result = null;
		try {
			Boolean val = courseOutlineMapper.update(dto);
			result = new ResultDTO<Boolean>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteCourse1(Long id) {
		ResultDTO<Boolean> result = null;
		try {
			Boolean val = courseMapper.delete1(id);
			result = new ResultDTO<Boolean>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseOutlineQuery> queryCourseOutline(RyxCourseOutlineQuery query) {

		ResultDTO<RyxCourseOutlineQuery> result = null;
		try {
			List<RyxCourseOutlineDTO> val = courseOutlineMapper.query(query);
			if(null==val) val = new ArrayList<RyxCourseOutlineDTO>();
			query.setList(val);
			query.setTotalItem(courseOutlineMapper.countQuery(query));
			result = new ResultDTO<RyxCourseOutlineQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseOutlineQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseOutlineQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	@Override
	public ResultDTO<RyxCourseOutlineDTO> getCourseOutlineById(Long id) {

		ResultDTO<RyxCourseOutlineDTO> result = null;
		try {
			RyxCourseOutlineDTO val = courseOutlineMapper.getById(id);
			result = new ResultDTO<RyxCourseOutlineDTO>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseOutlineDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseOutlineDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;

	}

	@Override
	public ResultDTO<Boolean> addDownloadCount(Long id) {
		ResultDTO<Boolean> result = null;
		try {
			Boolean val = courseMapper.addDownloadCount(id);
			result = new ResultDTO<Boolean>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Autowired
	RyxUserFollowCourseMapper ryxUserFollowCourseMapper;
	
	@Override
	public ResultDTO<Boolean> saveUserFollowCourse(RyxUserFollowCourseDTO userFollowCourseDTO) {
		
		ResultDTO<Boolean> result = null;
		try{		
			
			Boolean val = false;
			List<RyxUserFollowCourseDTO> list = ryxUserFollowCourseMapper.getByCourseIdUserId(userFollowCourseDTO);
			if(null==list || list.size() ==0){
				val = ryxUserFollowCourseMapper.create(userFollowCourseDTO);
			}
			else{
				val = true;
			}
			result = new ResultDTO<Boolean>(val);
			
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	@Override
	public ResultDTO<List<RyxUserFollowCourseDTO>> listUserFollowCourse(Long userId) {
		
		ResultDTO<List<RyxUserFollowCourseDTO>> result = null;
		try{			
			RyxUserFollowCourseQuery query = new RyxUserFollowCourseQuery();
			query.setUid(userId.toString());
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxUserFollowCourseDTO> val = ryxUserFollowCourseMapper.query(query);
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<Integer> countUserFollowCourse(RyxUserFollowCourseQuery query) {
		
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxUserFollowCourseMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}


	@Override
	public ResultDTO<List<RyxUserFollowCourseDTO>> getUserFollowCourseByCourseIdAndUserId(Long courseId, Long userId) {
		
		ResultDTO<List<RyxUserFollowCourseDTO>> result = null;
		try{			
			RyxUserFollowCourseQuery query = new RyxUserFollowCourseQuery();
			query.setUserId(userId);
			query.setCourseId(courseId);
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxUserFollowCourseDTO> val = ryxUserFollowCourseMapper.query(query );
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxUserFollowCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteUserFollowCourseByCourseIdAndUserId(Long courseId, Long userId) {
		
		ResultDTO<Boolean> result = null;
		try{			
			RyxUserFollowCourseDTO dto = new RyxUserFollowCourseDTO();
			dto.setUserId(userId);
			dto.setCourseId(courseId);
			Boolean val = ryxUserFollowCourseMapper.deleteByCourseIdAndUserId(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * audit report
	 */
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	
	@Override
	public ResultDTO<Boolean> createAuditRecord(RyxAuditRecordDTO dto) {

		ResultDTO<Boolean> result = null;
		try{
			Boolean val = auditReportMapper.create(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	public ResultDTO<RyxAuditRecordQuery> queryAuditRecord(RyxAuditRecordQuery query){
		
		ResultDTO<RyxAuditRecordQuery> result = null;
		try{
			query.setList(auditReportMapper.query(query));
			query.setTotalItem(auditReportMapper.countQuery(query));
			result = new ResultDTO<RyxAuditRecordQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxAuditRecordQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxAuditRecordQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}


	@Override
	public ResultDTO<List<RyxCourseDTO>> getCourseByTeacherId(RyxCourseQuery query) {
		ResultDTO<List<RyxCourseDTO>> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.getCourseByTeacherId(query);
			result = new ResultDTO<List<RyxCourseDTO>>(val);
		} catch (BaseDaoException e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<List<RyxCourseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<Integer> countCourseHits(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try{			
			Integer val = courseMapper.countHits(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxObjectLimitDTO> getObjectLimitById(Long id) {
		ResultDTO<RyxObjectLimitDTO> result = null;
		try{			
			RyxObjectLimitDTO val = ryxObjectLimitMapper.getById(id);
			result = new ResultDTO<RyxObjectLimitDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxObjectLimitDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxObjectLimitDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxObjectLimitQuery> queryObjectLimit(RyxObjectLimitQuery query) {
		ResultDTO<RyxObjectLimitQuery> result = null;
		try{			
			query.setList(ryxObjectLimitMapper.query(query));
			query.setTotalItem(ryxObjectLimitMapper.countQuery(query));
			result = new ResultDTO<RyxObjectLimitQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxObjectLimitQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxObjectLimitQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<RyxObjectLimitQuery> queryObjectLimitUnique(RyxObjectLimitQuery query) {
		ResultDTO<RyxObjectLimitQuery> result = null;
		try{			
			query.setList(ryxObjectLimitMapper.queryUnique(query));
			query.setTotalItem(ryxObjectLimitMapper.countQueryUnique(query));
			result = new ResultDTO<RyxObjectLimitQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxObjectLimitQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxObjectLimitQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<Integer> countQueryObjectLimit(RyxObjectLimitQuery query) {
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxObjectLimitMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<Long> createObjectLimit(RyxObjectLimitDTO dto) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxObjectLimitMapper.create(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Long> createBatchObjectLimit(RyxObjectLimitDTO dto) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxObjectLimitMapper.createBatch(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Long> createOrUpdateObjectLimit(RyxObjectLimitDTO dto) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxObjectLimitMapper.createOrUpdate(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	/**
	 *  机构赠送课程专用 
	 */
	@Override
	public ResultDTO<Boolean> createOrUpdateUndueObjectLimit(Long mainUserId,Integer currentPage,Integer pageSize) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try{			
			
//			RyxObjectLimitMapper ryxObjectLimitMapper = sqlSession.getMapper(RyxObjectLimitMapper.class);
			
			List<KeyrvDTO> accounts = UserHelper.getInstance().getSubAccountByUserId(mainUserId,currentPage,pageSize) ;
			
			RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
			objectLimitQuery.setUserId(mainUserId);
			objectLimitQuery.setPageSize(Integer.MAX_VALUE);
			List<RyxObjectLimitDTO> objLimitList = ryxObjectLimitMapper.query(objectLimitQuery);
	
			for(KeyrvDTO account : accounts){
				
				for(RyxObjectLimitDTO ryxObjectLimitDTO1 : objLimitList){
					
					RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
					objectLimitDTO.setOid(ryxObjectLimitDTO1.getOid());
					objectLimitDTO.setUserId(Long.valueOf(account.getRkey()));
					objectLimitDTO.setOtype(ryxObjectLimitDTO1.getOtype());
					objectLimitDTO.setCategory(ryxObjectLimitDTO1.getCategory());
					objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
					objectLimitDTO.setLimi(ryxObjectLimitDTO1.getLimi());
					objectLimitDTO.setOrderType(ryxObjectLimitDTO1.getOrderType());
					objectLimitDTO.setSort(ryxObjectLimitDTO1.getSort());
					objectLimitDTO.setMuserId(mainUserId);
					objectLimitDTO.setMoid(ryxObjectLimitDTO1.getMoid());
					
					
					RyxObjectLimitQuery objectLimitQuery1 = new RyxObjectLimitQuery();
					objectLimitQuery1.setOtype(objectLimitDTO.getOtype());
					objectLimitQuery1.setOid(objectLimitDTO.getOid());
					objectLimitQuery1.setUserId(objectLimitDTO.getUserId());
					RyxObjectLimitDTO objectLimitDTO1 = ryxObjectLimitMapper.queryByOou(objectLimitQuery1); // 原有的
					if(null == objectLimitDTO1){
						ryxObjectLimitMapper.create(objectLimitDTO)  ;
					}
					else{
						if(ryxObjectLimitDTO1.getLimi() > objectLimitDTO1.getLimi()){  // 新的时间比老的时间长，需要更新
							ryxObjectLimitMapper.updateLimitByOou(objectLimitDTO) ;
						}
					}
				}
			}

//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		} catch (Throwable t) {
			result = new ResultDTO<Boolean>("error", t.getMessage());
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	
	
	/**
	 *  机构赠送课程专用 
	 */
	@Override
	public ResultDTO<Boolean> createSubAccountLimit(Long mainUserId,Long limi) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try{			
			
//			RyxObjectLimitMapper ryxObjectLimitMapper = sqlSession.getMapper(RyxObjectLimitMapper.class);
					
			RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
			objectLimitDTO.setUserId(mainUserId);
			objectLimitDTO.setOid(mainUserId);
			objectLimitDTO.setOtype(EnumObjectType.SUB_ACCOUNT_MODULE.getCode());
			objectLimitDTO.setLimi(limi);
			objectLimitDTO.setStatus(2);
			objectLimitDTO.setOrderType(1);
			
			RyxObjectLimitQuery objectLimitQuery1 = new RyxObjectLimitQuery();
			objectLimitQuery1.setOtype(objectLimitDTO.getOtype());
			objectLimitQuery1.setOid(objectLimitDTO.getOid());
			objectLimitQuery1.setUserId(objectLimitDTO.getUserId());
			RyxObjectLimitDTO objectLimitDTO1 = ryxObjectLimitMapper.queryByOou(objectLimitQuery1);
			if(null == objectLimitDTO1){
				ryxObjectLimitMapper.create(objectLimitDTO)  ;
			}
			else{
				ryxObjectLimitMapper.updateLimitByOou(objectLimitDTO);
			}
//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		} catch (Throwable t) {
			result = new ResultDTO<Boolean>("error", t.getMessage());
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Long> createOrUpdateUndueObjectLimit(RyxObjectLimitDTO dto) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxObjectLimitMapper.createOrUpdateUndue(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> updateObjectLimit(RyxObjectLimitDTO dto) {
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.update(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxObjectLimitDTO> queryObjectLimitByOou(RyxObjectLimitQuery objectLimitQuery) {
		ResultDTO<RyxObjectLimitDTO> result = null;
		try{			
			RyxObjectLimitDTO val = ryxObjectLimitMapper.queryByOou(objectLimitQuery);
			result = new ResultDTO<RyxObjectLimitDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxObjectLimitDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxObjectLimitDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> updateObjectLimitByOou(RyxObjectLimitDTO objectLimitDTO) {
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.updateLimitByOou(objectLimitDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}	
	
	@Override
	public ResultDTO<Boolean> updateObjectLimitByUserId(RyxObjectLimitDTO objectLimitDTO) {
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.updateLimitByUserId(objectLimitDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteOrgAuthObjectLimit(RyxObjectLimitDTO objectLimitDTO){
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.deleteOrgAuth(objectLimitDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	@Override
	public ResultDTO<Boolean> deleteObjectLimitByMuserIdMoid(RyxObjectLimitDTO objectLimitDTO){
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.deleteByMuserIdMoid(objectLimitDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteObjectLimitByMuserIdUserId(RyxObjectLimitDTO objectLimitDTO){
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxObjectLimitMapper.deleteByMuserIdUserId(objectLimitDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxCourseQuery> queryCourseWidespread(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.queryCourseWidespread(query);
			query.setList(val);
			query.setTotalItem(courseMapper.getMyFollowCourseCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	

	@Override
	public ResultDTO<Integer> getMyUnexpiredCourseCount2(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyUnexpiredCount2(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Integer> getMyExpiredCount2(RyxCourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(courseMapper.getMyExpiredCount2(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<RyxCourseQuery> getMyCourse(RyxCourseQuery query) {
		ResultDTO<RyxCourseQuery> result = null;
		try {
			query.setList(courseMapper.getMyCourse(query));
			query.setTotalItem(courseMapper.getMyCourseCount(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<RyxUserEcourseQuery> queryEcourse(RyxUserEcourseQuery query) {
		ResultDTO<RyxUserEcourseQuery> result = null;
		try {
			List<RyxUserEcourseDTO> val = ryxUserEcourseMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxUserEcourseMapper.countQuery(query));
			result = new ResultDTO<RyxUserEcourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxUserEcourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxUserEcourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Long> createUserEcourse(RyxUserEcourseDTO userEcourseDTO) {
		ResultDTO<Long> result = null;
		try {
			result = new ResultDTO<Long>(ryxUserEcourseMapper.create(userEcourseDTO));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<RyxUserEcourseQuery> queryEcourse1(RyxUserEcourseQuery query) {
		ResultDTO<RyxUserEcourseQuery> result = null;
		try {
			List<Long> val = ryxUserEcourseMapper.query1(query);
			query.setList(val);
			query.setTotalItem(ryxUserEcourseMapper.countQuery1(query));
			result = new ResultDTO<RyxUserEcourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxUserEcourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxUserEcourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Integer> queryEcourseCount(RyxUserEcourseQuery query) {
		ResultDTO<Integer> result = null;
		try {
			result = new ResultDTO<Integer>(ryxUserEcourseMapper.countQuery(query));
		} catch (BaseDaoException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<RyxCourseQuery> queryDefaultCourse1(RyxCourseQuery query) {

		ResultDTO<RyxCourseQuery> result = null;
		try {
			List<RyxCourseDTO> val = courseMapper.defaultQuery1(query);
			if(null==val) val = new ArrayList<RyxCourseDTO>();
			query.setList(val);
			query.setTotalItem(courseMapper.countDefaultQuery1(query));
			result = new ResultDTO<RyxCourseQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxCourseQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	
	
	
	
}

