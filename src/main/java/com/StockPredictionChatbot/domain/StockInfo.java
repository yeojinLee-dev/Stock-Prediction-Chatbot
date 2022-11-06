package com.StockPredictionChatbot.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="stock_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
@Entity
public class StockInfo {
    @Id
    private String code;

    private String stock;

    private String curr_price;
}
