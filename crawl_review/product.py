from bs4 import BeautifulSoup
from numpy import average
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By


import pandas as pd
# from tqdm.notebook import tqdm
import time
import re
import sys
from selenium.webdriver.common.keys import Keys #키보드 키를 제어하는 라이브러리


keys = Keys() #키보드 키를 제어
url = 'https://smartstore.naver.com/sdsc/category/580171a2a2e348fe812b9234b5a148c0?cp=1' # 크롤링 하고자 하는 제품 페이지
s = Service("chromedriver.exe")
driver = webdriver.Chrome(service=s)
driver.get(url)
time.sleep(2)

# 안의 내용
pd_num_list = []    # 상품명번호(int) 
name_list = []      # 상품명(char)
gender_list = []    # 사용 성별(char)
length_list = []    # 길이(char)
design_list = []    # 무늬(char)
purpose_list = []   # 용도(char)
price_list = []     # 가격(int)
material_list = []  # 재료(set)
thickness_list = [] # 두께(set)
weather_list = []   # 계절(set)    


next_btn = []

btn_num = ['a:nth-child(2)', 'a:nth-child(3)', 'a:nth-child(4)', 'a:nth-child(5)', 'a:nth-child(6)', 'a:nth-child(7)',
        'a:nth-child(8)', 'a:nth-child(9)', 'a:nth-child(10)', 'a:nth-child(11)', 'a.fAUKm1ewwo._2Ar8-aEUTq']

#next_producturl = ['li:nth-child(1)', 'li:nth-child(2)','li:nth-child(3)','li:nth-child(4)','li:nth-child(5)',
#       'li:nth-child(1)','li:nth-child(1)','li:nth-child(1)','li:nth-child(1)','li:nth-child(1)','li:nth-child(1)','li:nth-child(1)','li:nth-child(1)']

date = str(input("저장날짜를 입력하세요: "))
stop = int(input("몇 페이지?: "))

for Bi in range(0, stop):
    next_btn.append(btn_num[Bi]) 
print(len(next_btn))



for pagenum in next_btn:
    driver.find_element(By.CSS_SELECTOR, '#CategoryProducts > div._1HJarNZHiI._2UJrM31-Ry > '+str(pagenum)+'').send_keys(keys.ENTER)
    num = int(input("한 페이당 몇개 상품 가져올까요?: "))
    for Pi in range(1, num+1):
        driver.find_element(By.CSS_SELECTOR, '#CategoryProducts > ul > li:nth-child(' + str(Pi) + ') > a').send_keys(keys.ENTER)
        do = str(input("진행할까요?(ㅇ/N): "))
        if do == 'ㅇ':     
            pd_num_list.append(driver.find_element(By.CSS_SELECTOR, '#INTRODUCE > div > div.attribute_wrapper > div > div._2E4i2Scsp4._copyable > table > tbody > tr:nth-child(1) > td:nth-child(2) > b').text)
            
            name_list.append(driver.find_element(By.CSS_SELECTOR, '#INTRODUCE > div > div.attribute_wrapper > div > div._2E4i2Scsp4._copyable > table > tbody > tr:nth-child(3) > td:nth-child(2)').text)
            
            gender_list.append(driver.find_element(By.CSS_SELECTOR, '#INTRODUCE > div > div.attribute_wrapper > div > div.detail_attributes > div > table > tbody > tr > td:nth-child(2)').text)
            
            length = str(input("길이?(fake,sneaker,ankle,middle,crew,kneel...): "))
            length_list.append(length)
            
            design = str(input("무늬?(non,charac,2,3,4...): "))
            design_list.append(design)
            
            purpose = str(input("용도?(casual,sport,suit...): "))
            purpose_list.append(purpose)
            
            price_list.append(driver.find_element(By.CSS_SELECTOR, '#content > div > div._2-I30XS1lA > div._2QCa6wHHPy > fieldset > div._1ziwSSdAv8 > div.WrkQhIlUY0 > div > strong > span._1LY7DqCnwR').text)
            
            material = str(input("재료?(ex)A,B,C,): "))
            mat = material.split(',')
            material_list.append(mat)
            
            thickness = str(input("두께?(ex)A,B,C,): "))
            mat = material.split(',')
            thickness_list.append(mat)
            
            wea = driver.find_element(By.CSS_SELECTOR, '#INTRODUCE > div > div.attribute_wrapper > div > div.detail_attributes > div > table > tbody > tr > td:nth-child(4)').text.split(',')
            weather_list.append(wea)
        driver.back()
        time.sleep(1)
        
            

socks = pd.DataFrame({'상품명번호':pd_num_list, '상품명':name_list, '사용 성별':gender_list, '길이':length_list,'무늬':design_list, '용도':purpose_list, '가격':price_list, '재료':material_list, '두께':thickness_list, '계절':weather_list,})
do = str(input("마무리 아무거나 눌러도 상관 없음?(Y/N): "))
print(pd_num_list)
socks.to_csv("c:/sockshopping/crawling_all_" + date + '.csv', encoding='cp949')
