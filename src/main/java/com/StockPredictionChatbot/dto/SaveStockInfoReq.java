package com.StockPredictionChatbot.dto;

import com.StockPredictionChatbot.domain.Stock;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveStockInfoReq {
    private String code;
    private String stock;
    private String curr_price;

    @JsonCreator
    public Stock toEntity() {
        Stock stockInfo = Stock.builder()
                .code(code)
                .stockName(stock)
                .currPrice(curr_price)
                .build();

        return stockInfo;
    }
}
