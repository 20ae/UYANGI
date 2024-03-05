<h1>README</h1>

<h2>도봉양말협동조합 네이버 스토어</h2>
- 도봉양말협동조합 https://smartstore.naver.com/sdsc
<br><br>


<h2>image_processing.py</h2>
<h4>- pytesseract 패키지를 이용한 이미지에서 텍스트 추출</h4>
1. 이미지 처리 : [OpenCV] GRAYSCALE -> BINARY 이진화 <br>
2. TXT 파일로 저장 
<br><br>


<h2>kakaoOCR.py</h2>
<h4>- kakao에서 제공하는 광학문자인식 OCR API를 이용</h4>
-> tesseract보다 높은 정확성<br><br>
-> [Google Colab으로 OCR해보기] https://gimkuku0708.tistory.com/44 <br>
-> [Kakao OCR API] https://developers.kakao.com/docs/latest/ko/vision/dev-guide#ocr
<br><br><br>


<br>
<img src="/uploads/00cbc79a11254c6c0203c157e48affea/info.jpg"  width="700">
![data_csv](/uploads/dc0d6012c0382c8f363c4eb1a3f684dc/data_csv.png)
<br>
[문제점]<br>
1. 이미지 사이즈가 크면 클수록 정확도 떨어짐(글씨 작아지면)->크롤링한 이미지 자르는 과정 필요<br>
2. 체크박스 인식을 하긴 하는데 정확도 <br>
&nbsp; &nbsp; &nbsp; &nbsp;두께감 : 얇음 보통 ø도톰 ø두툼 <br>
&nbsp; &nbsp; &nbsp; &nbsp;계절감 : 봄 여름 가을 겨울 <br>
3. 이미지마다 PRODUCT INFO 형식이 다름
