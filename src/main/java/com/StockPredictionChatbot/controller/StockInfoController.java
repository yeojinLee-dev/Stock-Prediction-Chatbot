package com.StockPredictionChatbot.controller;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.config.BaseResponse;
import com.StockPredictionChatbot.domain.Stock;
import com.StockPredictionChatbot.dto.SaveStockInfoReq;
import com.StockPredictionChatbot.service.StockService;
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

    private final StockService stockService;

    @Scheduled(cron = "0 30 18 * * 1-5", zone = "Asia/Seoul")
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

        stockService.bulkUpdateStockInfo(saveStockInfoReqs);
        System.out.println(today + ".json : Update All Crawling Data");

        return new BaseResponse<>(today + " : Update All Crawling Data Success");
    }

    @PostMapping("/api/stock")
    public Stock getStock(@RequestParam("stock_name") String stock_name) throws BaseException {
        System.out.println("getStock() ");
        return stockService.getStockInfo(stock_name);

    }

}
