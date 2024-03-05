#Kako OCR api
import json
import cv2
import requests
import sys
import csv
import numpy as np
import re
import pandas as pd

LIMIT_PX = 1024
LIMIT_BYTE = 1024*1024  # 1MB
LIMIT_BOX = 40


def kakao_ocr_resize(image_path: str):
    """
    ocr detect/recognize api helper
    ocr api의 제약사항이 넘어서는 이미지는 요청 이전에 전처리가 필요.

    pixel 제약사항 초과: resize
    용량 제약사항 초과  : 다른 포맷으로 압축, 이미지 분할 등의 처리 필요. (예제에서 제공하지 않음)

    :param image_path: 이미지파일 경로
    :return:
    """

    image_path = 'info.JPG'
    image = cv2.imread(image_path)
    height, width, _ = image.shape

    if LIMIT_PX < height or LIMIT_PX < width:
        ratio = float(LIMIT_PX) / max(height, width)
        image = cv2.resize(image, None, fx=ratio, fy=ratio)
        height, width, _ = height, width, _ = image.shape

        # api 사용전에 이미지가 resize된 경우, recognize시 resize된 결과를 사용해야함.
        image_path = "{}_resized.jpg".format(image_path)
        cv2.imwrite(image_path, image)

        return image_path
    return None


def kakao_ocr(image_path: str, appkey: str):
    """
    OCR api request example
    :param image_path: 이미지파일 경로
    :param appkey: 카카오 앱 REST API 키
    """
    image_path = 'info.JPG'
    appkey = '9c88efdee0f345632665798d38cd1167'
    API_URL = 'https://dapi.kakao.com/v2/vision/text/ocr'

    headers = {'Authorization': 'KakaoAK {}'.format(appkey)}

    image = cv2.imread(image_path)
    jpeg_image = cv2.imencode(".jpg", image)[1]
    data = jpeg_image.tobytes()


    return requests.post(API_URL, headers=headers, files={"image": data})

def main():
    if len(sys.argv) != 3:
        print("Please run with args: $ python example.py /path/to/image appkey")
    image_path, appkey = 'info.PNG', '9c88efdee0f345632665798d38cd1167'

    resize_impath = kakao_ocr_resize(image_path)
    if resize_impath is not None:
        image_path = resize_impath
        print("원본 대신 리사이즈된 이미지를 사용합니다.")
        #카카오 API에서 범위, 인식한 글씨 받기
    output = kakao_ocr(image_path, appkey).json()
    outputdata = json.dumps(output, ensure_ascii=False,sort_keys=True, indent=2)
    #print("[OCR] output:\n{}\n".format(outputdata))

    #받은 데이터 array로 변환
    outputdata = json.loads(outputdata)
    data_list=[]

    for i in range(len(outputdata['result'])):
     #print(outputdata['result'][i]['recognition_words'][0])
     data_list.append(outputdata['result'][i]['recognition_words'][0])

    #카테고리 구분
    is_english = re.compile('[-a-zA-Z]')
    category = ['상품명','색상','사이즈','제조국','소재','세탁 방법','두께감','계절감']

    name = []
    color = []
    size = []
    nation = []
    textile = []
    wash = []
    thickness = []
    weather = []


    func(data_list,2,size)
    func(data_list,3,nation)
    func(data_list,4,textile)
    func(data_list,5,wash)
    func(data_list,6,thickness)
    func(data_list,7,weather)

    data = []
    data.append(" ")
    data.append(" ")
    data.append(" ".join(size))
    data.append(" ".join(nation))
    data.append(" ".join(textile))
    data.append(" ".join(wash))
    data.append(" ".join(thickness))
    data.append(" ".join(weather))
    print(data)

  #csv 파일로 저장
    socks = pd.DataFrame(data, index=['상품명','색상','사이즈','제조국','소재','세탁 방법','두께감','계절감'])
    t = socks.transpose()
    print(t)

    t.to_csv("filename.csv",encoding='utf-8-sig')

def func(data_list,index,name):
    n = index
    category = ['상품명','색상','사이즈','제조국','소재','세탁 방법','두께감','계절감']
    for i in range(0,len(data_list)):
        if(data_list[i]==category[n]):
            for j in range(i+1, len(data_list)):
                if (len(category)==n+1):
                    name.append(data_list[j])
                elif(data_list[j] == category[n+1]):
                    break
                else:
                    name.append(data_list[j])

if __name__ == "__main__":
    main()