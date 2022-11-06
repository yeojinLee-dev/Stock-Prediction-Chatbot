#-*-coding: utf-8-*-

from bs4 import BeautifulSoup
import urllib.request as req
import pandas as pd
from datetime import datetime

import json


#종목명과 종목코드 크롤링
url = f"https://vip.mk.co.kr/newSt/rate/best.php?gubn=kospi"

tables = pd.read_html(url)
CompanyTable = pd.DataFrame(tables[4], columns=['종목코드', '종목명', '현재가'])
CompanyTable = CompanyTable.astype({'종목코드':'str'})
CompanyTable = CompanyTable.astype({'현재가':'str'})

i = 0
data = []

for code in CompanyTable['종목코드']:
  if len(code) == 3:
    code = '000' + code
  elif len(code) == 4:
    code = '00' + code
  elif len(code) == 5:
    code = '0' + code
  CompanyTable['종목코드'][i] = code
  list = {'code' : code, 'stock' : CompanyTable['종목명'][i], 'curr_price' : CompanyTable['현재가'][i]}
  data.append(list)
  i += 1

file_path = "/home/app/data/" + datetime.today().strftime("%Y%m%d") + ".json"

#json 파일로 저장
with open(file_path, 'w') as outfile:
    json.dump(data, outfile, ensure_ascii=False)

print(datetime.today().strftime("%Y%m%d") + "crawling success")