from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.alert import Alert
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
import time
import pandas as pd
import re
#import pymysql
#from sqlalchemy import create_engine


# MySQL Connector using pymysql
#pymysql.install_as_MySQLdb()
#engine = create_engine("mysql+mysqldb://root:"+"990912"+"@localhost/shop_db", encoding='utf-8')
#conn = engine.connect()


# 스토어 주소 입력
store_url = 'https://smartstore.naver.com/sdsc/products/5678620836'

# 셀레니움 - argument 설정 - 크롬드라이버 위치 설정
s = Service('chromedriver.exe')
driver = webdriver.Chrome(service=s)
driver.get(store_url)  # url접속
time.sleep(2)  # 접속 대기


# 키1 = 옵션1, value = 가격
# 옵션 버튼은 항상 처음부터 찾아야함 그래서 함수로 구현해서 불러올때마다 새로운 값으로 인식시킴

def item_option(x):
    try:
        return driver.find_element(by=By.TAG_NAME,value='body').find_elements(By.CLASS_NAME,'bd_3hLoi')[x]
    except:
        print("error")

product_price_list = [] # 상품 가격 리스트
product_number_list = [] # 상품 번호 리스트

'''
수작업
'''
gender = "남성용"
size = "남성free(250~280mm)"
design = "무지"
purpose = "정장"
quantity = 1
manufacturer = "서울도봉양말협동조합"


# 옵션1을 키값으로 지정한 딕셔너리 만들기

opt_dict = {} # 상품번호를 받아올 빈 딕셔너리

item_option_list_1 = []  # 옵션1번을 받아올 빈 리스트
item_option(0).click()  # 옵션1 버튼 클릭

# 옵션1번값을 리스트로 저장 - 이후 딕셔너리에 키값으로 바뀜
for i in item_option(0).find_element(By.TAG_NAME,'ul').find_elements(By.TAG_NAME,"li"):
    item_option_list_1.append(i.text)

# 옵션1번리스트 딕셔너리 키값으로 바꿈
opt_dict = dict.fromkeys(item_option_list_1)

# 옵션1번 다시 클릭
item_option(0).click()

# 여기까지 옵션1을 전체 값 키값으로 지정한 딕셔너리 만들기

# opt_dict으로 만들어진 키값에 가격을 value로 저장하기

for i in opt_dict:
    try:
        item_option(0).click()  # 옵션1 다시 클릭
        driver.find_element(By.LINK_TEXT,i).click()  # 딕셔너리 키값으로 링크 옵션1 클릭

        # 옵션1 클릭을 통해 얻은 가격 딕셔너리 값으로 저장
        opt_dict[i] = driver.find_element(By.XPATH,"""//*[@id="content"]/div/div[2]/div[2]/fieldset/div[8]/div[2]/strong/span""").text
        product_price_list.append(opt_dict[i])
        # 옵션1을 클릭해서 생긴 결제창 닫음
        driver.find_element(By.XPATH,"""//*[@id="content"]/div/div[2]/div[2]/fieldset/div[7]/ul/li/button""").click()

    except:
        # 품절되면 alert 뜨며 품절창 나옴 그래서 품절인걸 딕셔너리에 저장하고
        # 알러트창 확인눌러서 닫음
        # 그리고 다음으로 넘어감
        opt_dict[i] = "품절"
        product_price_list.append(opt_dict[i])
        item_option(0).click()
        driver.find_element(By.LINK_TEXT,i).click()
        pass

# 상품번호 추출해 저장
product_number = driver.find_element(By.CSS_SELECTOR,'td.ABROiEshTD').text

'''
상품명에 알파벳 붙이기
'''
pd_num_list = [] # 상품명 + 알파벳 저장할 빈 리스트

count = len(item_option_list_1) # 옵션 개수 구하기
#print(count)
i = 0
j = 0
alphabet = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
alphabet2 = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']
while True:
    pd_num_list.append(driver.find_element(By.CSS_SELECTOR,'td.ABROiEshTD').text + alphabet2[j] + alphabet[i])
    i += 1
    if i == 25:
        i = 0
        j += 1
    if i + j == count: # i가 옵션 개수와 같아지면 반복문 끝냄
        break

# 상품명 추출해 저장하기
item_name = driver.find_element(By.XPATH,"""//*[@id="content"]/div/div[2]/div[2]/fieldset/div[1]/div[1]/h3""").text
#item_name = ""

print('상품명: ' + item_name, '상품번호: ' + product_number, "\n", opt_dict) # 확인용


#MySQL에 INSERT 하기(판다스 DataFrame -> to_sql() 메소드 이용해 MySQL에 저장하기)

#socks = pd.DataFrame({'pd_id': pd_num_list, 'pds_id':product_number, 'name': item_name, 'color':item_option_list_1,'price':product_price_list})
socks = pd.DataFrame({'pd_id': pd_num_list, 'name': item_name, 'color':item_option_list_1, 'gender':gender, 'size':size, 'design':design, 'purpose':purpose, 'price':product_price_list,'pd_url':store_url,'pds_id':product_number})
#socks.to_sql(name='product_option_1', con=engine, if_exists='append', index=False)


## csv 파일로 저장하기 ##

memo = str(input("상품명을 입력하세요: "))

while True:
     try:
         socks.to_csv("c:/sockshopping/양말 정보" + memo + '.csv', encoding='cp949', index=False)
         break
     except Exception as e: # 인코딩 에러 예외처리
         error_character = str(e).split(' ')[5].replace('\'', '')
         socks = socks.replace(u'{}'.format(error_character), u'', regex=True)




