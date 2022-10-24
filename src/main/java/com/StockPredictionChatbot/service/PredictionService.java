package com.StockPredictionChatbot.service;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.domain.Prediction;
import com.StockPredictionChatbot.domain.PredictionRepository;
import com.StockPredictionChatbot.dto.GetPredictionRes;
import com.StockPredictionChatbot.dto.SavePredictionReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PredictionService {

    private final PredictionRepository predictionRepository;

    public void bulkUpdate(List<SavePredictionReq> requestList) {

        // 테이블 내 모든 데이터 삭제
        // deleteAll은 select를 먼저 수행하므로 상당한 비용을 동반
        // (대체할 수 있는 메서드가 무엇인지)
        predictionRepository.deleteAll();

        // 모든 신규 데이터들 insert
        for (SavePredictionReq request : requestList) {
            predictionRepository.save(request.toEntity());
        }

    }

    public Prediction getPrediction(String stock) throws BaseException {
        return predictionRepository.findByStock(stock);
    }

}
