package com.king.nowedge.service.three.impl;

import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.mapper.three.SearchHistoryMapper;
import com.king.nowedge.service.three.SearchHistoryServeice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchHistoryServeiceImpl implements SearchHistoryServeice {

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Override
    public void saveHistory(LoreInputDTO loreInputDTO) {
        searchHistoryMapper.saveHistory(loreInputDTO);
    }

    @Override
    public void deleteHistory(LoreInputDTO loreInputDTO) {
        searchHistoryMapper.deleteHistory(loreInputDTO);
    }
}
