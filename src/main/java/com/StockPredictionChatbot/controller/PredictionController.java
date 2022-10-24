package com.StockPredictionChatbot.controller;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.config.BaseResponse;
import com.StockPredictionChatbot.domain.Prediction;
import com.StockPredictionChatbot.dto.GetPredictionReq;
import com.StockPredictionChatbot.dto.GetPredictionRes;
import com.StockPredictionChatbot.dto.SavePredictionReq;
import com.StockPredictionChatbot.service.PredictionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping("/")
    public BaseResponse<String> bulkUpdate()
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String today = sdf.format(c1.getTime());

        String absolutePath = "/home/app/data/";
        String filePath = absolutePath + today + ".json";

        System.out.println("file path : " + filePath);


        List<SavePredictionReq> savePredictionReqList =
                objectMapper.readValue(new File(filePath), new TypeReference<>() {
                });

        //System.out.println(savePredictionReqList.get(0).getStock());

        predictionService.bulkUpdate(savePredictionReqList);

        return new BaseResponse<>("Update All Success");
    }

    @PostMapping("/chatbot")
    public BaseResponse<Prediction> getPrediction(@RequestBody GetPredictionReq getPredictionReq) {
        try {
            return new BaseResponse<>(predictionService.getPrediction(getPredictionReq.getStock()));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
