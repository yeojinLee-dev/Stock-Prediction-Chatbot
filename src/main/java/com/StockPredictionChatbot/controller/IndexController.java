package com.StockPredictionChatbot.controller;

import com.StockPredictionChatbot.config.BaseException;
import com.StockPredictionChatbot.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final StockService stockService;

    @GetMapping("/")
    public String index(Model date) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        date.addAttribute("date", localDate);
        return "index";
    }

    @GetMapping("/stock/{stock_name}")
    public String result(@PathVariable String stock_name, Model date, Model stock, Model prediction) throws BaseException {
        LocalDate localDate = LocalDate.now();
        date.addAttribute("date", localDate);

        stock.addAttribute("stock", stockService.getStockInfo(stock_name));
        prediction.addAttribute("prediction", stockService.getPrediction(stock_name));
        return "result";
    }
}
