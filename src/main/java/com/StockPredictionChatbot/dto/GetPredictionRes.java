package com.StockPredictionChatbot.dto;

import com.StockPredictionChatbot.domain.Prediction;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPredictionRes {
    private String code;
    private String stock;
    private String curr_price;
    private String pre_price;

    @JsonCreator
    public Prediction toEntity() {
        Prediction prediction = Prediction.builder()
                .code(code)
                .stock(stock)
                .curr_price(curr_price)
                .pre_price(pre_price)
                .build();

        return prediction;
    }
}
