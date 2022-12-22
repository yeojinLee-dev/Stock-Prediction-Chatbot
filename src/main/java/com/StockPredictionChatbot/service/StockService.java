package com.StockPredictionChatbot.service;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.domain.Stock;
import com.StockPredictionChatbot.domain.StockRepository;
import com.StockPredictionChatbot.dto.SaveStockInfoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository stockRepository;

    public void bulkUpdateStockInfo(List<SaveStockInfoReq> requestList) {

        // 테이블 내 모든 데이터 삭제
        // deleteAll은 select를 먼저 수행하므로 상당한 비용을 동반
        // (대체할 수 있는 메서드가 무엇인지)
        stockRepository.deleteAll();

        // 모든 신규 데이터들 insert
        for (SaveStockInfoReq request : requestList) {
            stockRepository.save(request.toEntity());
        }

    }

    public Stock getStockInfo(String stock) throws BaseException {
        return stockRepository.findByStockName(stock);
    }

    public String getPrediction(String stockName) {
        Stock stock = stockRepository.findByStockName(stockName);
        int prePrice = Integer.parseInt(stock.getPrePrice());
        int currPrice = Integer.parseInt(stock.getCurrPrice());

        if (prePrice > currPrice)
            return "+" + (prePrice - currPrice);
        else if (prePrice == currPrice)
            return "steady";
        else return "-" + (currPrice - prePrice);


    }
}
