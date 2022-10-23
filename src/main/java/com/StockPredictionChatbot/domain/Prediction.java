package com.StockPredictionChatbot.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="prediction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
@Entity
public class Prediction {

    @Id
    private String code;

    private String stock;

    private String currPrice;

    private String prePrice;
}
