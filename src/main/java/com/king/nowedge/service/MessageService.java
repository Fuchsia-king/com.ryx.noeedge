package com.king.nowedge.service;

import com.king.nowedge.dto.MessageDTO;
import com.king.nowedge.dto.MessageUserDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.MessageQuery;
import com.king.nowedge.query.MessageUserQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public interface MessageService   {
	
	
	/*  --------------------------------------------
	 *   message 
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createMessage(MessageDTO messageDTO);
	
	ResultDTO<Boolean> updateMessage(MessageDTO messageDTO);
	
	ResultDTO<MessageDTO> queryMessageByUid(String uid);
	
	ResultDTO<List<MessageDTO>> queryMessage(MessageQuery messageQuery);
	
	ResultDTO<Integer> countQueryMessage(MessageQuery messageQuery);
	
	ResultDTO<Boolean> deleteMessage(String uid) ;
	
	
	/*  --------------------------------------------
	 *   message  user
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createMessageUser(MessageUserDTO messageUserDTO);
	
	ResultDTO<Boolean> updateMessageUser(MessageUserDTO messageUserDTO);
	
	ResultDTO<MessageUserDTO> queryMessageUserByUid(String uid);
	
	ResultDTO<List<MessageUserDTO>> queryMessageUser(MessageUserQuery messageUserQuery);
	
	ResultDTO<Integer> countQueryMessageUser(MessageUserQuery messageUserQuery);
	
	ResultDTO<Boolean> deleteMessageUser(String uid) ;
	
	
	
}
