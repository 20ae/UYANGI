from PIL import Image
import pytesseract
import os
import numpy as np
import cv2
import sys

pytesseract.pytesseract.tesseract_cmd = R"C:\Program Files\Tesseract-OCR\tesseract"
config = ('-l kor+eng --oem 3 --psm 11')

#이미지 파일 불러오기
filename = 'img01.PNG'
if len(sys.argv) >1 :
    filename = sys.argv[1]

#이미지처리 : opencv 그레이스케일->이진화
image01 = cv2.imread(filename,cv2.IMREAD_GRAYSCALE)
_, sun = cv2.threshold(image01,240,255,cv2.THRESH_BINARY)

#이미지 -> text 추출
print01 = pytesseract.image_to_string(sun, config=config)
txt = print01.replace("\n\n","\n")

#text파일로 저장
with open('data01.txt','w',encoding='utf-8') as tx:
    tx.write(txt)


