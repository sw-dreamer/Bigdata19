# 웹 기반 연락처 관리 프로그램 (MySQL DB 활용)

이 프로젝트는 MySQL 데이터베이스를 사용하여 **웹 기반 연락처 관리 시스템**을 구현한 것입니다. 사용자는 웹 페이지를 통해 연락처 정보를 추가, 수정, 삭제 및 조회할 수 있으며, 회원가입과 로그인 기능을 통해 인증을 수행하고 개인별 연락처를 관리할 수 있습니다. 


**- 소프트웨어**

![image](https://github.com/user-attachments/assets/d86d1611-5755-46af-9642-f17643f9a1eb)

---

## 주요 기능

1. **회원가입**
   - 사용자는 웹 폼을 통해 이름, 이메일, 비밀번호 등을 입력하여 회원가입을 진행합니다.
   - 회원가입 정보는 MySQL 데이터베이스에 저장됩니다.

2. **로그인**
   - 사용자는 이메일과 비밀번호를 통해 로그인합니다.
   - 로그인 후 자신의 연락처 정보를 조회하고 관리할 수 있습니다.

3. **연락처 추가**
   - 사용자는 웹 폼에서 이름, 전화번호, 이메일 등의 정보를 입력하여 연락처를 추가합니다.
   - 입력된 데이터는 MySQL 데이터베이스에 저장됩니다.

4. **연락처 조회**
   - 저장된 모든 연락처 정보를 웹 페이지에서 확인하거나, 특정 조건에 맞는 연락처를 검색할 수 있습니다.

5. **연락처 수정**
   - 사용자는 연락처 정보를 수정할 수 있으며, 수정된 내용은 MySQL 데이터베이스에 반영됩니다.

6. **연락처 삭제**
   - 사용자는 연락처 목록에서 선택한 항목을 삭제할 수 있습니다.

---

## 프로그램 구조

### **1. Model **
- 프로그램의 데이터 구조와 비즈니스 로직을 담당하며, 데이터베이스와의 상호작용을 관리합니다.
- MySQL과 연동하여 연락처의 추가, 수정, 삭제, 조회, 회원가입, 로그인 등의 기능을 구현합니다.

### **2. View **
- 사용자와 상호작용하는 웹 페이지를 처리합니다.
- HTML과 JSP를 사용하여 사용자에게 입력 양식과 결과를 표시합니다.

### **3. Controller **
- 사용자의 입력을 처리하고, 해당 입력에 맞는 로직을 실행합니다.
- 모델과 뷰 사이의 상호작용을 조정하며, 사용자가 요청한 데이터를 뷰에 전달하거나 모델에 데이터를 전달합니다.

---

## 프로그램 흐름

### **1. 사용자 입력**
사용자에게 연락처 추가, 수정, 삭제, 조회, 회원가입, 로그인 등의 명령을 웹 페이지에서 입력받습니다.
예를 들어, 사용자는 웹 폼에서 이름, 전화번호, 이메일을 입력하여 새로운 연락처를 추가할 수 있습니다.

### **2. 입력 처리**
컨트롤러는 사용자의 요청을 받아 필요한 로직을 실행합니다.
- 회원가입 요청 시 입력된 정보를 모델로 전달하여 사용자 정보를 MySQL에 저장.
- 로그인 요청 시 입력된 정보와 데이터베이스의 정보를 비교하여 인증을 진행.
- 연락처 추가, 수정 및 삭제 요청도 처리합니다.

### **3. 데이터 처리**
모델은 **Spring Data JPA**를 통해 데이터베이스와 연결되어 연락처 및 사용자 데이터를 관리합니다.
MySQL에 데이터를 저장하거나 조회하는 작업을 처리합니다.

### **4. 결과 출력**
모델에서 처리한 데이터를 컨트롤러를 통해 뷰에 전달하여 사용자에게 결과를 표시합니다.
연락처 목록을 출력하거나, 로그인 후 사용자 맞춤형 화면을 보여줍니다.

---
## 결과 화면

### 로그인 화면

![image](https://github.com/user-attachments/assets/771176ee-07b1-40db-9398-65da3301cb1a)

### 아이디 찾기 화면

![image](https://github.com/user-attachments/assets/7c4a95d0-24e8-476a-85c7-14cae5867006)

### 비밀번호 재설정 화면

![image](https://github.com/user-attachments/assets/58d469b0-de81-424c-b977-ca6a0315afa3)

### 회원가입 화면

![image](https://github.com/user-attachments/assets/06387c5e-a513-4666-8544-40246918bef2)

> 비밀번호 암호화 방식(SHA-256 salt 방식)을 통해서 실제로 DB에 저장되는 결과

![image](https://github.com/user-attachments/assets/a762e03f-2d29-4942-850f-dc7fdcdf93b6)

### 회원 로그인 화면

![image](https://github.com/user-attachments/assets/e003f2a0-aecb-447b-b885-deacc6a50988)

![image](https://github.com/user-attachments/assets/f398d16d-9fde-4a2e-8954-3f5b286ac336)

### 연락처 등록 화면

![image](https://github.com/user-attachments/assets/c037360d-7c92-4839-a590-28d185cef7e9)

![image](https://github.com/user-attachments/assets/c9a54d80-8149-42c4-bb47-a8c31e8aa1ce)


### 연락처 목록 화면

![image](https://github.com/user-attachments/assets/2e55dd63-124a-47aa-9e42-a47e02a8f619)

![image](https://github.com/user-attachments/assets/2ff162b0-aef3-468d-b26f-99d6ff7ca963)

### 연락처 수정 화면

![image](https://github.com/user-attachments/assets/d8ca16ca-e613-4fa2-8e66-60e38e2cd6a1)

### 연락처 검색 화면

![image](https://github.com/user-attachments/assets/855bac3d-4043-443d-8d67-27394805bb8b)

---

참고 사이트

1.웹제작 사이트
  - https://codepen.io/pen/

![화면](https://github.com/user-attachments/assets/c7a04de2-f5d6-4b80-bd29-8ce0e9cb4a55)

2. 카카오 우편 번호 서비스
   - https://postcode.map.daum.net/guide
     
![image](https://github.com/user-attachments/assets/54134feb-4516-4686-afaf-2e1270c4a5f4)
