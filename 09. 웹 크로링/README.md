# Conda 가상 환경 설정 및 `requests` 설치

VS Code 터미널에서 아래 명령어로 가상 환경을 확인하고 필요한 경우 초기화 후, PowerShell을 재시작하여 환경을 설정합니다.

## 1. Conda 환경 확인
먼저, 터미널에서 아래 명령어를 실행하여 `firstenv`와 `base` 환경이 있는지 확인합니다.

```
conda env list
```
로 firstenv랑 base가 있는지를 확인을 합니다.

## 2. Conda 초기화

필요한 경우, `conda init` 명령어로 Conda를 초기화합니다. 

```
conda init
```

PowerShell을 재시작한 후 conda activate firstenv 실행: PowerShell을 다시 시작한 후, 다음 명령어로 가상 환경을 활성화할 수 있습니다.
```
conda activate firstenv
```
## 3. `requests` 패키지 설치

`firstenv` 환경을 활성화한 후, `requests` 패키지를 설치합니다.
```
conda install requests
```
![image](https://github.com/user-attachments/assets/1704e892-9231-432c-825e-db43f13bb928)

## 4. 설치 확인
`requests`가 제대로 설치되었는지 확인합니다.
```
conda list requests
```
![image](https://github.com/user-attachments/assets/70572133-0539-4454-8772-3c0b6d5c4ecf)

## 5. `07-01 파이썬 웹크로링 기본.ipynb` 파일 생성

위 과정을 완료한 후, `07-01 파이썬 웹크로링 기본.ipynb` 파일을 생성합니다.

![image](https://github.com/user-attachments/assets/40e98aaa-3036-49c9-8e87-11991ce5936d)

## 6. `requests` 기본 활용

### 0. 모듈 소개

- requests 모듈을 http 요청을 쉽게 보내고 결과를 받는 기능을 가지고 있다
- 요청(request)  : get, post
- 응답(response)         

### 1. 기본 활용

```
# 기본적인 get 요청
# get(url) : 해당 url에 http get 요청을 보낸다 
# status_code : 응답 코드(200(정상처리), 400(요청 잘못), 500(서버 에러))

import requests

# 크로링한 웹페이지 url 저장
url = 'https://www.naver.com' #메인 페이지

#get 요청
response = requests.get(url)

#응답 코드 확인
print(response)
print('응답 상태 코드 :',response.status_code)
```

위의 코드를 작성 해 보면 `200`으로 정상적으로 response 가 오는 것을 확인 할 수 있습니다.

#### 결과 화면
![image](https://github.com/user-attachments/assets/5d756aad-7cca-40df-83cb-5ea944d4c9be)

### 2. 페이지 코드 출력
```
#페이지  내용 출력(html 코드)
# text : 웹페이지를 문자열로 변환
print('='*100)
print(response.text)
print('='*100)
```

#### 결과 화면
![image](https://github.com/user-attachments/assets/c5dcd04f-c145-414e-9340-2da480aba0a3)

### 3. 페이지 코드 단어수 확인

```
print('단어수:',len(response.text))
```

#### 결과 화면

![image](https://github.com/user-attachments/assets/f5968eac-a3a6-4bf7-8db1-e7363112cfe8)

### 4. 요청 헤더(User-Agent) 변경하기

```
# 요청 헤더(User-Agent) 변경하기
# 사유 : 일부 웹사이트는 User-Agent가 없는 요청을 차단 할 수 있다.
# 일부 사이트는 기본적인 봇(bot) 요청을 차단한다.
# 서버입장에서 코드로 실행하는 것이 아니고 브라우저에서 접속한 것처럼 보이도록 설정

import requests

url = 'https://example.com'

# 요청 헤더 설정(일반적인 브라우저 사용하는 것 처럼 활용)
getheaders = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}

# get 요청

response = requests.get(url,headers=getheaders)

# response 상태 확인, 텍스트 추출
print('응답 상태 코드 :',response.status_code)
print(response.text)
```

#### 결과 화면

![image](https://github.com/user-attachments/assets/fde57dca-cce8-4f6f-a007-f92e0b7d5773)

### 5. 구글에서 검색한 결과 갖고 오기

`딥러닝` 이라고 검색을 해보면 아래와 같이 나옵니다.

![image](https://github.com/user-attachments/assets/87e4aa57-d54f-4a1a-beec-3a2c4069d30a)

![image](https://github.com/user-attachments/assets/53f81eb6-d36c-4015-a56d-4bb772a09bf5)


이를 활용해서 코드로 갖고오는 방법은 아래와 같습니다.

```
# 구글 검색
# https://www.google.com/search?q=%EB%94%A5%EB%9F%AC%EB%8B%9D&oq=%EB%94%A5%EB%9F%AC%EB%8B%9D&gs_lcrp=EgZjaHJvbWUyDAgAEEUYORixAxiABDIMCAEQABhDGIAEGIoFMgwIAhAuGEMYgAQYigUyBwgDEAAYgAQyBwgEEAAYgAQyBwgFEAAYgAQyBwgGEAAYgAQyBwgHEAAYgAQyBwgIEAAYgAQyBwgJEAAYgATSAQo0MzEwNWowajE1qAIAsAIA&sourceid=chrome&ie=UTF-8
# URL에서 Query String은 URL 뒤에 오는 물음표(?) 이후의 부분을 말합니다. 보통 검색 결과나 특정 데이터를 서버에 전달할 때 사용됩니다. 이 부분은 key=value 형태로 이루어져 있으며, 여러 개의 쿼리 파라미터가 있을 경우 & 기호로 구분됩니다. 

import requests

url = 'https://www.google.com/search'

# get 요청에 사용할 파마리터 설정 : 딕셔너리 형태로 설정

getparam = {'q':'딥러닝'}

# 요청 헤더 설정(일반적인 브라우저 사용하는 것 처럼 활용)
getheaders = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}


# 요청 보내기 : get
response = requests.get(url,params=getparam,headers=getheaders)

# response 상태 확인, 텍스트 추출
print('응답 상태 코드 :',response.status_code)
print(response.text)
```

#### 결과 화면

![image](https://github.com/user-attachments/assets/dac6634a-f6a7-47c5-bf56-17c7726e4687)

실제로 코드를 실행하면 아래와 같이 오류가 발생합니다.
![image](https://github.com/user-attachments/assets/70ff9fa7-4fb9-41bf-b661-2f3d6b68b59a)

### 6. 네이버 검색

`딥러닝` 이라고 검색을 해보면 아래와 같이 나옵니다.

![image](https://github.com/user-attachments/assets/78178fdd-777d-485c-aef5-a09a91e92467)

![image](https://github.com/user-attachments/assets/6f86e279-8153-44a2-aabe-48a876dcdca2)



```
# naver 검색
query = '딥러닝'
url = f'https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query={query}'


# get 요청에 사용할 파라미터 설정 : 딕셔너리 형태 설정
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}


# 요청 보내기 : get
response = requests.get(url,  headers=headers)


print(response.text)
```
#### 결과 화면
정상적으로 출력
![image](https://github.com/user-attachments/assets/88a6e98c-e6de-4616-9e43-6b2ec282c67a)


