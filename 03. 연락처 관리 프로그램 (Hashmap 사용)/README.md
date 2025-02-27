# Java 연락처 관리 프로그램 (HashMap 사용)

이 프로그램은 `HashMap`을 사용하여 연락처를 생성, 읽기, 수정, 삭제할 수 있는 기능을 제공합니다. 데이터베이스는 사용하지 않고, 프로그램 실행 중에 메모리 내에서 연락처를 관리합니다.

## 프로그램 설명

- **연락처 생성**: 새로운 연락처를 추가합니다.
- **연락처 업데이트**: 기존 연락처 정보를 수정합니다.
- **연락처 삭제**: 지정된 연락처를 삭제합니다.
- **저장된 연락처 읽기**: 저장된 연락처 목록을 읽습니다.
- **특정 사용자 및 특정 전화번호로 검색**: 특정 사용자명 또는 전화번호로 검색하는 기능

## 프로그램 기능

### 1. 저장된 연락처 읽기
- 저장된 연락처 목록을 출력합니다.
- 프로그램 종료 시 binary 파일로 저장이 되고 시작 시 binary 파일의 내용을 불러옵니다.

#### 프로그램 첫 시작

![프로그램 첫 시작](https://github.com/user-attachments/assets/7a88ee85-3461-405b-a756-1b4fcadd353a)

#### 전체 목록 출력

![전체 목록 출력](https://github.com/user-attachments/assets/65faabd0-f37b-4175-932a-d829325d0441)

### 2. 특정 사용자 및 특정 전화번호로 검색
- **이름으로 검색**: 이름을 입력하면 해당 이름을 가진 모든 사용자들을 목록으로 출력합니다. 동일한 이름을 가진 다수의 연락처가 있을 경우 모두 표시됩니다.
- **전화번호로 검색**: 특정 전화번호를 입력하여 해당 전화번호에 연결된 연락처를 검색할 수 있습니다. 전화번호는 유일하므로 정확한 일치 결과만 표시됩니다.
- 이 기능은 사용자가 이름이나 전화번호로 원하는 연락처를 쉽게 찾을 수 있도록 도와줍니다.

#### 이름으로 검색

![이름으로 검색](https://github.com/user-attachments/assets/37162181-b082-4384-9048-01bdb06ea592)

#### 전화번호로 검색

![전화번호로 검색](https://github.com/user-attachments/assets/6f66ed53-0339-4aeb-9f67-f4493f8c8f43)

### 3. 연락처 추가
- 이름, 전화번호, 주소, 관계를 입력받아 `HashMap`에 저장합니다.
- 전화번호는 반드시 `010`으로 시작하는 10자리 또는 11자리의 숫자여야 하며, 중복된 전화번호는 입력할 수 없습니다.
- 관계는 `가족`, `친구`, `기타` 중에서 선택해야 합니다.

![연락처 추가](https://github.com/user-attachments/assets/b26ac49b-e4f9-44f3-a7bf-6a7c011055f8)

### 4. 연락처 업데이트
- 이름을 기준으로 기존 연락처를 찾아 수정합니다.
- 수정 시 전화번호 중복 체크 후 수정됩니다.

![연락처 업데이트](https://github.com/user-attachments/assets/64644e6a-cf13-4a3d-99ea-0e467bd16d47)

### 5. 연락처 삭제
- 이름을 기준으로 연락처를 삭제할 수 있습니다.
- 동일한 이름을 가진 사람이 여러 명일 경우 전화번호로 구체적으로 삭제할 사람을 선택할 수 있습니다.

![연락처 삭제](https://github.com/user-attachments/assets/6b681cef-2012-48e9-8547-25a8f6173a71)

## 6. 프로그램 종료
  - 프로그램 종료 시 binary 파일로 저장
