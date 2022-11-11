package com.StockPredictionChatbot.controller;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.config.BaseResponse;
import com.StockPredictionChatbot.domain.Prediction;
import com.StockPredictionChatbot.domain.StockInfo;
import com.StockPredictionChatbot.dto.GetPredictionReq;
import com.StockPredictionChatbot.dto.GetStockInfoReq;
import com.StockPredictionChatbot.dto.SavePredictionReq;
import com.StockPredictionChatbot.dto.SaveStockInfoReq;
import com.StockPredictionChatbot.service.StockInfoService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class StockInfoController {

    private final StockInfoService stockInfoService;

    @Scheduled(cron = "0 16 17 * * 1-5", zone = "Asia/Seoul")
    @GetMapping("/bulk/crawling")
    public BaseResponse<String> bulkUpdateStockInfo()
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String today = sdf.format(c1.getTime());

        String absolutePath = "/home/app/data/";
        String filePath = absolutePath + today + ".json";

        System.out.println("file path : " + filePath);


        List<SaveStockInfoReq> saveStockInfoReqs =
                objectMapper.readValue(new File("/home/app/data/" + today + ".json"), new TypeReference<>() {
                });

        //System.out.println(savePredictionReqList.get(0).getStock());

        stockInfoService.bulkUpdateStockInfo(saveStockInfoReqs);

        return new BaseResponse<>(today + " : Update All Crawling Data Success");
    }

    @PostMapping("/stock")
    public BaseResponse<StockInfo> getStockInfo(@RequestBody GetStockInfoReq getStockInfoReq) {
        try {
            System.out.println("get stock information data -> 자동배포 후");
            return new BaseResponse<>(stockInfoService.getStockInfo(getStockInfoReq.getStock()));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
