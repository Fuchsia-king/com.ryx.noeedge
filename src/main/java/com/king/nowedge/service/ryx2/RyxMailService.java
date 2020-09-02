package com.king.nowedge.service.ryx2;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import org.springframework.stereotype.Service;

@Service("ryxMailService")

public interface RyxMailService {  
    public ResultDTO<Boolean> processRegister(RyxUsersDTO user, String serverName);	
}
