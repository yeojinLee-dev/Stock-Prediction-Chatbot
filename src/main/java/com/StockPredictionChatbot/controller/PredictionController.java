package com.StockPredictionChatbot.controller;

import com.StockPredictionChatbot.config.BaseResponse;
import com.StockPredictionChatbot.dto.SavePredictionReq;
import com.StockPredictionChatbot.service.PredictionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PredictionController {

    private PredictionService predictionService;

    public BaseResponse<String> bulkUpdate()
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String today = sdf.format(c1.getTime());

        String absolutePath = "/home/app/data";
        String filePath = absolutePath + today + ".json";

        List<SavePredictionReq> savePredictionReqList = objectMapper.readValue(new File(filePath),
                new TypeReference<List<SavePredictionReq>>() {
                });

        predictionService.bulkUpdate(savePredictionReqList);

        return new BaseResponse<>("Update All Success");
    }
}
