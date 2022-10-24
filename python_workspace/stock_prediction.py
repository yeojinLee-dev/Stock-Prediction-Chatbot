#!pip install finance-datareader

from bs4 import BeautifulSoup
import urllib.request as req
import pandas as pd  
import FinanceDataReader as fdr  
from datetime import datetime

from sklearn.preprocessing import MinMaxScaler 
import numpy as np

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM, Conv1D, Lambda
from tensorflow.keras.losses import Huber
from tensorflow.keras.optimizers import Adam

import json

#종목명과 종목코드 크롤링
url = f"https://vip.mk.co.kr/newSt/rate/best.php?gubn=kospi"

tables = pd.read_html(url)
CompanyTable = pd.DataFrame(tables[4], columns=['종목코드', '종목명', '현재가'])
CompanyTable = CompanyTable.astype({'종목코드':'str'})
CompanyTable = CompanyTable.astype({'현재가':'str'})

i = 0

for code in CompanyTable['종목코드']:
  if len(code) == 3:
    code = '000' + code
  elif len(code) == 4:
    code = '00' + code
  elif len(code) == 5:
    code = '0' + code
  CompanyTable['종목코드'][i] = code
  i += 1


#종목별 OHLCV 정보 수집
stock = fdr.DataReader(CompanyTable['종목코드'][0], '2012 -01-01', datetime.today().strftime("%Y-%m-%d") )

"""주가 예측"""

def make_dataset(data, label, window_size=20):
    feature_list = []
    label_list = []
    for i in range(len(data) - window_size):
        feature_list.append(np.array(data.iloc[i:i+window_size]))
        label_list.append(np.array(label.iloc[i+window_size]))
    return np.array(feature_list), np.array(label_list)

"""주가예측분석 함수"""

def predictStockPrice(code):
  #훈련 데이터
  stock = fdr.DataReader(code, '2012 -01-01', datetime.today().strftime("%Y-%m-%d") )

  #스케일링
  scaler = MinMaxScaler().fit(stock)
  scaled = scaler.transform(stock)
  scale_cols = ['Open', 'High', 'Low', 'Close', 'Volume', 'Change']
  df = pd.DataFrame(scaled, columns=scale_cols)

  #학습 데이터 추출
  feature_cols = ['Open', 'High', 'Low', 'Volume']
  label_cols = ['Close']

  train_feature = df[feature_cols]  #  N by 4
  train_label = df[label_cols]      # N by 1


  train_feature, train_label = make_dataset(train_feature, train_label, 20)

  # print(train_feature.shape)
  # print(train_label.shape)

  # print(train_feature[0, :, :])
  # print(train_label[0])

  # 학습 데이터를 시리얼 데이터로 변경
  model = Sequential()
  model.add(LSTM(16, 
                input_shape=(train_feature.shape[1], train_feature.shape[2]), 
                activation='relu', 
                return_sequences=False)
            )
  model.add(Dense(1))
  model.compile(loss='mean_squared_error', optimizer='adam', metrics=['mse'])

  #학습 시킴
  history = model.fit(train_feature, train_label,  epochs=10,  batch_size=16)

  #마지막 데이터를 통해 다음 날 주가 예측
  p = model.predict(train_feature[-1,:,:].reshape(1,20,4))
  p = scaler.inverse_transform([[0,0,0, p[0][0], 0, 0]])[0][3]

  return str(p)

#이후 Ubuntu 기반의 서버 환경에서 돌리게 되면 파일 경로를 절대경로로 변경해주어야 함.
file_path = "/home/app/data/" + datetime.today().strftime("%Y%m%d") + ".json"

"""50개 기업의 주가 분석 및 예측"""

i = 0
data = []

for code in CompanyTable['종목코드']:
  # if i == 5:
  #   break
  p = predictStockPrice(code)
  list = {'code' : code, 'stock' : CompanyTable['종목명'][i], 'curr_price' : CompanyTable['현재가'][i], 'pre_price' : p}
  data.append(list)
  i += 1

#json 파일로 저장
with open(file_path, 'w') as outfile:
    json.dump(data, outfile, ensure_ascii=False)

