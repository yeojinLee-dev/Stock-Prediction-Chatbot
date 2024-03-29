package com.StockPredictionChatbot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Stock findByStockName(String stock_name);
}
