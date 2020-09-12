package com.king.nowedge.service;

import com.king.nowedge.dto.NoticeDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.NoticeQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeService")
public interface NoticeService   {
	
	
	ResultDTO<Boolean> createNotice(NoticeDTO noticeDTO);
	
	ResultDTO<Boolean> updateNotice(NoticeDTO noticeDTO);
	
	ResultDTO<NoticeDTO> queryNoticeByUid(String uid);
	
	ResultDTO<List<NoticeDTO>> queryNotice(NoticeQuery noticeQuery);
	
	ResultDTO<Integer> countQueryNotice(NoticeQuery noticeQuery);
	
	ResultDTO<Boolean> deleteNotice(String uid) ;
	
	
	
}
