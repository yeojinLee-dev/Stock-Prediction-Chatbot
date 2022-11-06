package com.StockPredictionChatbot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInfoRepository extends JpaRepository<Prediction, String> {
    StockInfo findByStock(String stock);
}
