# Stock-Prediction-Chatbot
Travis CI, S3, Codedeploy를 이용한 자동 배포

## 참여 인원
- 1명

## 사용 기술

- Backend : **SpringBoot**
- Cloud : **AWS EC2/S3/RDS/CodeDeploy (+IAM)**
- Build : **Travis-CI**
- Data Crawling, Analysis : **Python3, Tensorflow**


## 프로젝트 설명

![내일의 주가예측 프로세스 구조도](https://github.com/yeojinLee-dev/Fintech-OpenAPI-Project/assets/80297591/a29d03eb-09cf-4b86-9a22-8d835bd903e2)
<aside>
💬 내일의 주가 예측 프로젝트

- 정해진 시간에 종가 데이터를 크롤링하여 가공하고, 데이터 분석 진행
    - linux의 crontab 이용
    - 데이터 분석 모델은 다음과 같이 구성
        
        (1) 종목코드 별로 FinanceDataReader에서 종목 OHLCV를 가지고 온다.
        
        (2) 아래와 같이 모델을 구현하여 트레이닝한다.
        
        - Input, X : t = 2012-01-01부터 현재까지의 OHLCV (0-1 Minmax Scale)
        - Model : LSTM, 16 hidden layers in between, activation = relu
        - Optimizer : Adam, Loss = mse
        - Training : batch_size = 16, epochs = 50
        - Output, y : t = 다음날의 Open Price
        
        (3) 다음날 Open Price를 살펴본다.
        
- 예측된 데이터를 정해진 시간에 DB에 업데이트한다.
    - Springboot의 @Scheduled 이용
    
    ```java
    @Scheduled(cron = "0 30 18 * * 1-5", zone = "Asia/Seoul")
        @GetMapping("/bulk/crawling")
        public BaseResponse<String> bulkUpdateStockInfo()
                throws JsonParseException, JsonMappingException, IOException {
    
            ObjectMapper objectMapper = new ObjectMapper();
    
    	      ...
    
            return new BaseResponse<>(today + " : Update All Crawling Data Success");
        }
    ```
    
- AWS를 이용하여 배포
    - Travis-CI, S3, Codedeploy를 통해 자동 배포 환경 구축
    

**결과 예시**
![내일의 주가예측 예시사진 1](https://github.com/yeojinLee-dev/Fintech-OpenAPI-Project/assets/80297591/bac49d87-7359-4611-954c-5173304dabb3)

![내일의 주가예측 예시사진 2](https://github.com/yeojinLee-dev/Fintech-OpenAPI-Project/assets/80297591/7bd467d2-655a-4fe3-a3e6-35cd51df3cde)
</aside>
