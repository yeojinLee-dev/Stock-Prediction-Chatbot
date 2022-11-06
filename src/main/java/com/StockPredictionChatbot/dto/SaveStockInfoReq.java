package com.StockPredictionChatbot.dto;

import com.StockPredictionChatbot.domain.StockInfo;
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
    public StockInfo toEntity() {
        StockInfo stockInfo = StockInfo.builder()
                .code(code)
                .stock(stock)
                .curr_price(curr_price)
                .build();

        return stockInfo;
    }
}
