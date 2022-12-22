package com.StockPredictionChatbot.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="stock")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
@Entity
public class Stock {
    @Id
    private String code;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "curr_price")
    private String currPrice;

    @ColumnDefault("0")
    @Column(name = "pre_price")
    private String prePrice;


}
