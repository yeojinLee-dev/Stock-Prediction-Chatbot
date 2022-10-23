package com.StockPredictionChatbot.dto;

import com.StockPredictionChatbot.domain.Prediction;

public class SavePredictionReq {

    private String code;
    private String stock;
    private String currPrice;
    private String prePrice;

    public Prediction toEntity() {
        Prediction prediction = Prediction.builder()
                .code(code)
                .stock(stock)
                .currPrice(currPrice)
                .prePrice(prePrice)
                .build();

        return prediction;
    }

}
