package com.StockPredictionChatbot.service;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.domain.StockInfo;
import com.StockPredictionChatbot.domain.StockInfoRepository;
import com.StockPredictionChatbot.dto.SaveStockInfoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockInfoService {
    private final StockInfoRepository stockInfoRepository;

    public void bulkUpdateStockInfo(List<SaveStockInfoReq> requestList) {

        // 테이블 내 모든 데이터 삭제
        // deleteAll은 select를 먼저 수행하므로 상당한 비용을 동반
        // (대체할 수 있는 메서드가 무엇인지)
        stockInfoRepository.deleteAll();

        // 모든 신규 데이터들 insert
        for (SaveStockInfoReq request : requestList) {
            stockInfoRepository.save(request.toEntity());
        }

    }

    public StockInfo getStockInfo(String stock) throws BaseException {
        return stockInfoRepository.findByStock(stock);
    }
}
