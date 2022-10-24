package com.StockPredictionChatbot.domain;

import com.StockPredictionChatbot.dto.GetPredictionRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, String> {

    Prediction findByStock(String stock);
}
