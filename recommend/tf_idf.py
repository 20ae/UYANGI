#content-based filtering -> 사이킷런 TF-IDF 사용

import pandas as pd

import csv
from ast import literal_eval
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
data = pd.read_csv('socks_data.csv')

#print(data.head(2))
#print(data["상품명"])

count_vector = CountVectorizer(min_df=0,ngram_range=(1, 2))
c_vector_genres = count_vector.fit_transform(data['상품명'])
gerne_c_sim = cosine_similarity(c_vector_genres, c_vector_genres).argsort()[:, ::-1]

#제품명을 기준으로 찾기
def get_recommend_list(df, product, top=10):
    target_product_index = df[df['상품명'] == product].index.values
    sim_index = gerne_c_sim[target_product_index, :top].reshape(-1)

    result = df.iloc[sim_index].sort_values('index',ascending=False)[:10]
    return result

#두께를 기준으로 찾기
c_vector_thickness = count_vector.fit_transform(data['두께감'])
thickness_c_sim = cosine_similarity(c_vector_thickness, c_vector_thickness).argsort()[:, ::-1]
def get_thickness_list(df, product, top=5):
    target_product_index = df[df['두께감'] == product].index.values
    sim_index = gerne_c_sim[target_product_index, :top].reshape(-1)

    result = df.iloc[sim_index].sort_values('index',ascending=False)[:5]
    return result

#검색 메뉴
menu = 0
while(menu != 9):
    menu = int(input("검색하고 싶은 카테고리를 입력해주세요.\n1.제품 2.색상 3.두께 4.계절 (검색:숫자입력/나가기:9)"))
    if(menu==1):
        print("제품명으로 검색하기")
        print("찾고 싶은 양말 검색: ")
        socks = str(input())
        print(get_recommend_list(data, socks))
        print("-----------------------------------------------------\n")
    elif(menu==2):
        print("색상으로 검색하기")
        color = str(input("선호 색상 : "))
        color_data = data[data['색상'].str.contains(color)]
        print(color_data.sort_values('index', ascending=True)[:10])
    elif(menu==3):
        print("두께감으로 검색하기")
        print("원하는 두께감 : ")
        thickness = str(input())
        print(get_thickness_list(data, thickness))
    elif(menu==4):
        print("계절감으로 검색하기")
        weather = str(input("원하는 계절감 : "))
        weather_list = data[data['계절감'].str.contains(weather)]
        print(weather_list.sort_values('index', ascending=True)[:10])
