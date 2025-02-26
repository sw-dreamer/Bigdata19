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
위 과정을 완료한 후, `07-01 파이썬 웹크로링 기본.ipynb` 파일을 생성합니다..
![image](https://github.com/user-attachments/assets/40e98aaa-3036-49c9-8e87-11991ce5936d)
