package com.king.nowedge.service;

import com.king.nowedge.dto.ProjectDTO;
import com.king.nowedge.dto.ProjectStatusDTO;
import com.king.nowedge.dto.ProjectTypeDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.ProjectQuery;
import com.king.nowedge.dto.query.ProjectStatusQuery;
import com.king.nowedge.dto.query.ProjectTypeQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public interface ProjectService   {
	
	
	
	/**------------------------------------------------
	 * 
	 * @param taskTypeDTO
	 * @return
	 ------------------------------------------------*/
	
	ResultDTO<Boolean> createProjectType(ProjectTypeDTO taskTypeDTO);
	
	ResultDTO<Boolean> updateProjectType(ProjectTypeDTO taskTypeDTO);
	
	ResultDTO<ProjectTypeDTO> queryProjectTypeByUid(String uid);
	
	ResultDTO<List<ProjectTypeDTO>> queryProjectType(ProjectTypeQuery taskTypeQuery);
	
	ResultDTO<Integer> countQueryProjectType(ProjectTypeQuery taskTypeQuery);
	
	ResultDTO<Boolean> deleteProjectType(String uid) ;
	
	ResultDTO<List<ProjectTypeDTO>> queryAllProjectType();
	
	
	
	/**------------------------------------------------
	 * 
	 * @param taskActionDTO
	 * @return
	 * 
	 ------------------------------------------------*/
	

	
	
	
	/*-----------------------------------------
	 * 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createProjectStatus(ProjectStatusDTO taskStatusDTO);
	
	ResultDTO<Boolean> updateProjectStatus(ProjectStatusDTO taskStatusDTO);
	
	ResultDTO<ProjectStatusDTO> queryProjectStatusByUid(String uid);
	
	ResultDTO<List<ProjectStatusDTO>> queryProjectStatusByType(String type);
	
	ResultDTO<List<ProjectStatusDTO>> queryProjectStatus(ProjectStatusQuery taskStatusQuery);
	
	ResultDTO<Integer> countQueryProjectStatus(ProjectStatusQuery taskStatusQuery);
	
	ResultDTO<Boolean> deleteProjectStatus(String uid) ;
	
	
	
	
	
	/*-----------------------------------------
	 *   task 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createProject(ProjectDTO taskDTO);
	
	ResultDTO<Boolean> updateProject(ProjectDTO taskDTO);
	
	ResultDTO<ProjectDTO> queryProjectByUid(String uid);
	
	ResultDTO<List<ProjectDTO>> queryProject(ProjectQuery taskQuery);
	
	ResultDTO<List<ProjectDTO>> queryProjectInvo(ProjectQuery taskQuery);
	
	ResultDTO<Integer> countQueryProjectInvo(ProjectQuery taskQuery);
	
	ResultDTO<Integer> countQueryProject(ProjectQuery taskQuery);
	
	ResultDTO<Boolean> deleteProject(String uid) ;
	
	
}
