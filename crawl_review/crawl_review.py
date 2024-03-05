from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By

import pandas as pd
import time
import re
import sys
from selenium.webdriver.common.keys import Keys #키보드 키를 제어하는 라이브러리

#import pymysql
#from sqlalchemy import create_engine


# MySQL Connector using pymysql
#pymysql.install_as_MySQLdb()
#engine = create_engine("mysql+mysqldb://root:"+"990912"+"@localhost/shop_db", encoding='utf-8')
#conn = engine.connect()

keys = Keys() #키보드 키를 제어

# 네이버 스마트 스토어 창 열기
url = 'https://smartstore.naver.com/sdsc/products/4982683792' # 크롤링 하고자 하는 제품 페이지
s = Service('chromedriver.exe')
driver = webdriver.Chrome(service=s)
driver.get(url)
time.sleep(2)
date = '4982683792'
#stop = int(input("몇 세트 크롤링 할까요?(1-11페이지가 한 세트: "))
stop=int(1)
next_btn = ['a:nth-child(2)', 'a:nth-child(3)', 'a:nth-child(4)', 'a:nth-child(5)', 'a:nth-child(6)', 'a:nth-child(7)',
        'a:nth-child(8)', 'a:nth-child(9)', 'a:nth-child(10)','a.fAUKm1ewwo._2Ar8-aEUTq']

review_list = [] #리뷰 리스트
rating_list = [] #평점 리스트
pdId_list = [] # 상품번호 리스트
product_option_list = [] #구매자 선택옵션 리스트
user_id = [] #유저 아이디 리스트

pdId = driver.find_element(By.CSS_SELECTOR,'td.ABROiEshTD').text
#pdId = "5104527893"

# 상품명 추출해 저장하기
item_name = driver.find_element(By.XPATH,"""//*[@id="content"]/div/div[2]/div[2]/fieldset/div[1]/div[1]/h3""").text


count = 0

while count < stop:
    for pagenum in next_btn: # 리뷰 페이지 넘기기
        try:
            driver.find_element(By.CSS_SELECTOR, '#REVIEW > div > div._2y6yIawL6t > div > div.cv6id6JEkg > div > div > '+str(pagenum)+'').send_keys(keys.ENTER)
            time.sleep(2)
        except:
            print("페이지 끝!")
            break
        for i in range(0,20): # 한 페이지 속 리뷰 20개 하나하나 접근
            try:
                html = driver.page_source
                soup = BeautifulSoup(html, "html.parser")
                #유저 아이디 저장
                userid = soup.find_all('strong', class_= "_3QDEeS6NLn")
                userid = userid[i].text
                userid = re.sub(r"[*]","",userid) # 특수문자 제거
                user_id.append(userid)

                # 리뷰가 담긴 div 엘리먼트 모두 찾아서 텍스트 추출해 리스트에 넣기
                review = soup.find_all('div', class_="YEtwtZFLDz")
                review = review[i].text
                review = re.sub('[^#0-9a-zA-Zㄱ-ㅣ가-힣 ]', "", review)  # 특수문자, 영어 제거
                review_list.append(review)

                # 평점이 담긴 em 엘리먼트 모두 찾아서 리스트에 넣기
                rating = soup.find_all('em',class_="_15NU42F3kT")
                rating = rating[i].text
                rating_list.append(rating)

            except IndexError:
                break
            # 구매자 선택옵션이 담긴 div 엘리먼트 모두 찾아서 텍스트 추출해 리스트에 넣기(옵션 안나오는 경우는 예외처리)
            while True:
                 try:
                     product_option = soup.find_all('div', class_="_38yk3GGMZq")
                     product_option = product_option[i].text
                     product_option = re.sub('[^#0-9a-zA-Zㄱ-ㅣ가-힣 ]', "", product_option)
                     product_option_list.append(product_option)
                     break
                 except Exception as error:
                     print("#####################################################################")
                     print(error)
                     product_option_list.append("옵션 찾을 수 없음 :: " + str(pagenum) + "페이지, " + str(i + 1) + "번째")
                     print("옵션 찾을 수 없음 :: " + str(pagenum) + "페이지, " + str(i + 1) + "번째")
                     print("#####################################################################\n")
                     break

    count = count + 1

'''
MySQL에 INSERT 하기
(판다스 DataFrame -> to_sql() 메소드 이용해 MySQL에 저장하기)
'''

socks = pd.DataFrame({'pd_Id':pdId,'rate':rating_list, 'pd_name':item_name,'content':review_list})
#socks.drop_duplicates(subset=['content'])
#socks.to_sql(name='product_review', con=engine, if_exists='append', index=False)

'''
csv 파일로 저장하기
'''
#date = str(input("저장날짜를 입력하세요: ")) # 파일이름에 들어갈 날짜 입력
# socks = pd.DataFrame({'평점':rating_list, '선택옵션':product_option_list,'리뷰':review_list})
socks = pd.DataFrame({'pd_Id':pdId,'rate':rating_list, 'user_id':user_id, 'product_option':product_option_list,'content':review_list})

while True:
    try:
        socks.to_csv("c:/sockshopping/product_review_" + date + '.csv', encoding='cp949', index=False)
        break
    except Exception as e: # 인코딩 에러 예외처리
        error_character = str(e).split(' ')[5].replace('\'', '')
        socks = socks.replace(u'{}'.format(error_character), u'', regex=True)

# # 확인용 print(len(review_list))