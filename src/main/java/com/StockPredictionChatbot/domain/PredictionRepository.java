package com.StockPredictionChatbot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, String> {
}
