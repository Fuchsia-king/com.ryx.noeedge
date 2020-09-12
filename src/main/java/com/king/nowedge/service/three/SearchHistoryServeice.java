package com.king.nowedge.service.three;

import com.king.nowedge.dto.comm.LoreInputDTO;

public interface SearchHistoryServeice {
    void saveHistory(LoreInputDTO loreInputDTO);
    void deleteHistory(LoreInputDTO loreInputDTO);
}
