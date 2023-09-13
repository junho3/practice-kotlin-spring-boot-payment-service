# payment-service (코틀린 스프링부트 연습)

## 프로젝트 정보
```
언어 : Kotlin (Java 11)
프레임워크 : Spring 2.7.1
DB : H2
```

## 프로젝트 구조
```
payment-service
   ㄴ adapters
      ㄴ clients 
      ㄴ persistence
   ㄴ applications
      ㄴ payment-api
   ㄴ common
   ㄴ domain
```
- 멀티 모듈로 구성하여 도메인 레이어가 격리된 헥사고날 모델
- clients : 외부 API 통신 레이어 (FeignClient, Kafka 등등)
- persistence : 영속성 레이어 (JPA)
- payment-api : 어플리케이션, 컨트롤러
- common : 공통 사용 모듈 (Enum, 상수, 유틸 등)
- domain : 도메인 로직 (트랜잭션 단위)


## 빌드 방법
```
./gradlew payment-api:clean payment-api:build

localhost:8080 기동
```

## DB 접속 정보
```
JDBC URL : jdbc:h2:tcp://localhost/~/payment
사용자명 : sa
비밀번호 : 없음

ddl-auto: create 옵션으로 앱이 기동될 때 테이블 생성
```

## 테이블 스키마
### transaction 거래 테이블
| 컬럼명            | 속성                   | 키   | 설명          |
|----------------|----------------------|-----|-------------|
| transaction_id | varchar(20) not null | PK  | 전체 거래 관리 번호 |
| created_at     | Timestamp not null   |     | 데이터 생성일     |

### payment 결제/취소건 테이블
| 컬럼명            | 속성                                 | 키                               | 설명                                        |
|----------------|------------------------------------|---------------------------------|-------------------------------------------|
| payment_id     | varchar(20) not null               | PK                              | 결제 번호                                     |
| transaction_id | varchar(20) not null               | FK (transaction.transaction_id) | 전체 거래 관리 번호                               |
| payment_type   | varchar(16) not null               |                                 | 결제종류 (PAYMENT: 결제, CANCEL: 취소)            |
| payment_status | varchar(16) not null               |                                 | 결제상태 (SUCESS: 성공, FAIL: 실패) 현재 SUCESS만 적재 |
| payment_amount | unsigned bigint not null default 0 |                                 | 결제/취소 금액                                  |
| vat_amount     | unsigned bigint not null default 0 |                                 | 결제/취소 VAT 금액                              |
| created_at     | Timestamp not null                 |                                 | 데이터 생성일                                   |

### payment_card 결제/취소건의 카드정보 테이블
| 컬럼명                    | 속성                    | 키                          | 설명                                |
|------------------------|-----------------------|----------------------------|-----------------------------------|
| payment_id             | varchar(20) not null  | PK FK (payment.payment_id) | 결제 번호                             |
| card_transaction_id    | varchar(36) not null  |                            | card.card_transaction_id 카드사 거래번호 |
| card_transaction_data  | varchar(450) not null |                            | 카드사 요청 데이터                        |
| card_installment_month | unsigned int not null |                            | 할부개월                              |
| encrypt_card_data      | varchar(300) not null |                            | 암호화된 카드 데이터 (카드번호,카드만료시간,CVC)     |
| created_at             | Timestamp not null    |                            | 데이터 생성일                           |


### transaction_fail_log 거래 실패 로깅 테이블
| 컬럼명            | 속성                    | 키   | 설명                                                         |
|----------------|-----------------------|-----|------------------------------------------------------------|
| id             | bigint not null       | PK  | auto increment                                             |
| transaction_id | varchar(20) not null  |     | transaction.transaction_id 전체 거래 관리 번호                     |
| payment_id     | varchar(20) not null  |     | payment.payment_id 결제 관리 번호                                |
| code           | varchar(255) not null |     | 실패코드 DB_ERROR: DB 에러, CARD_API_ERROR: 카드API 호출 에러, ETC: 기타 |
| reason         | varchar(4000)         |     | 실패 상세 사유 (ex 에러로그)                                         |


### transaction_balance 거래건 조회용 테이블 취소발생시 업데이트 됨
| 컬럼명                        | 속성                                 | 키   | 설명                                                       |
|----------------------------|------------------------------------|-----|----------------------------------------------------------|
| transaction_id             | varchar(20) not null               | PK  | 전체 거래 관리 번호 transaction.transaction_id 와 같은 값            |
| transaction_type           | varchar(32) not null               |     | 최종 거래 상태 PAYMENT: 결제, PARTIAL_CANCEL: 부부취소, CANCEL: 전체취소 |
| payment_transaction_amount | unsigned bigint not null default 0 |     | 결제 금액                                                    |
| payment_vat_amount         | unsigned bigint not null default 0 |     | 결제 부가세                                                   |
| cancel_transaction_amount  | unsigned bigint not null default 0 |     | 누적 취소된 결제금액                                              |
| cancel_vat_amount          | unsigned bigint not null default 0 |     | 누적 취소된 부가세                                               |
| created_at                 | Timestamp not null                 |     | 데이터 생성일                                                  |
| updated_at                 | Timestamp                          |     | 데이터 수정일                                                  |


### payment_balance 결제/취소건 조회용 테이블
| 컬럼명                    | 속성                                 | 키                                       | 설명                                               |
|------------------------|------------------------------------|-----------------------------------------|--------------------------------------------------|
| payment_id             | varchar(20) not null               | PK                                      | 결제 번호 payment.payment_id 와 같은 번호                 |
| transaction_id         | varchar(20) not null               | FK (transaction_balance.transaction_id) | 전체 거래 관리 번호 transaction.transaction_id 와 같은 값    |
| payment_type           | varchar(16) not null               |                                         | 결제종류 (PAYMENT: 결제, CANCEL: 취소)                   |
| payment_amount         | unsigned bigint not null default 0 |                                         | 결제/취소 금액                                         |
| vat_amount             | unsigned bigint not null default 0 |                                         | 결제/취소 부가세                                        |
| payment_balance_amount | unsigned bigint not null default 0 |                                         | 결제금액 - 취소금액                                      |
| vat_balance_amount     | unsigned bigint not null default 0 |                                         | 결제부가세 - 취소부가세                                    |
| card_installment_month | unsigned int not null              |                                         | 할부개월                                             |
| encrypt_card_data      | varchar(300) not null              |                                         | 암호화된 카드 데이터 (카드번호,카드만료시간,CVC)                    |  
| created_at             | Timestamp not null                 |                                         | 데이터 생성일                                          |
| updated_at             | Timestamp                          |                                         | 데이터 수정일                                          |


### card 카드사 트랜잭션 테이블
| 컬럼명                    | 속성                    | 키   | 설명                                                 |
|------------------------|-----------------------|-----|----------------------------------------------------|
| transaction_id         | varchar(36) not null  | PK  | uuid 카드사 트랜잭션 고유값 payment_card.card_transaction_id |
| partner_transaction_id | varchar(20) not null  | UNI | 고객사 결제 아이디 payment.payment_id                      |
| status                 | varchar(32) not null  | UNI | 트랜잭션 상태 COMPLETE : 거래성공, ROLLBACK 거래 롤백            |
| transaction_data       | varchar(450) not null |     | 카드사 요청 데이터                                         |
| created_at             | Timestamp not null    |     | 데이터 생성일                                            |


## 문제해결 전략
### 1. 헥사고날 아키텍처를 선택한 이유
- 모듈(레이어)별 디펜던시를 제한하여 무분별한 참조를 막기 위함
- DDD (도메인주도개발)을 하기 위함 
  * 개발 초반에는 Jpa 엔티티(테이블)과 도메인 엔티티가 거의 흡사하여 문제가 없지만, 시간이 흐름에 따라 다양한 요구사항 추가로 Jpa 엔티티와 도메인 엔티티의 간극이 벌어지고 테스트 코드 및 도메인 로직까지 영향을 받게 됨
  * 도메인 엔티티 기준으로 테스트 코드를 작성하기 때문에 테스트 코드 작성시 JPA의 각종 관계에 따른 비대한 엔티티를 만들 필요가 없음
- Persistence 레이어 추상화 가능
  * 기존 H2 DB에서 MongoDB로 변경시 Adapter 하위에 Persistence-mongo와 같이 모듈을 추가하면, 기존 코드에 영향 없이 추가 가능 
- 단점 : 러닝커브가 필요하고, 추상화 및 Port & Adapter간 주고 받는 데이터를 객체화해야 하므로 개발속도가 더딤

### 2. transaction과 payment 2 depth 테이블 구조로 설계한 이유
- 하나의 트랜잭션으로 하위 개별 결제/취소건을 추적하기 위함
- payment 테이블 내에서 최초 결제 payment_id 컬럼을 두고 추적할 수도 있지만, 최초 결제가 완료되면 결제/취소건을 그룹핑할 수 있는 하나의 트랜잭션 관점으로 봐야함

### 3. 조회용 Balance 테이블을 별도로 설계한 이유
- 결제 / 정산에서는 테이블(데이터)에 원장이라는 개념 존재. 원장 테이블을 불변으로 절대 수정(Update) 불가
  * 인서트만 되는 원장 테이블을 기반으로 조회 데이터를 만드는 일은 꽤나 복잡하므로 용도별 테이블 분리
- CQRS로 명령과 조회의 책임 분리를 위함
  * WRITE(Master) / READ(Slave) 로 물리적인 DB를 분리하여도 서비스가 거대해지고, 트래픽이 많아질 수록 테이블 Lock 이슈가 발생할 수 있음
  * 비즈니스 요건을 수행하는 테이블은 transaction, payment, payment_card 테이블이 담당
  * 화면 노출을 수행하는 테이블은 transaction_balance, payment_balance 테이블이 담당
- 주로 도메인 로직을 처리하는 WRITE 테이블과 화면 노출을 위한 READ 테이블은 요구사항이 다르기 때문에 구조가 달라질 수 밖에 없음
  * WRITE 테이블만 사용한다면, 화면 노출용 데이터를 생성하려면 비즈니스 로직이 복잡해짐


### 4. 결제 API
```
POST localhost:8080/v1/payment

Request
{
    "cardNo": 2275116277791,
    "cardExpiredDate": "1022",
    "cardValidationCode": 123,
    "cardInstallmentMonth": 0,
    "paymentAmount": 10000,
    "vatAmount": 100 // Optional
}

Response
{
    "success": true,
    "message": "success",
    "data": {
        "transactionId": "25634551737585701566",
        "paymentId": "54137175211417526425",
        "cardTransactionData": "_446PAYMENT___541371752114175264252275116277791__..."
    }
}
```
- 요구사항 기반으로 파라미터 정의
- 옵션필드인 vatAmount가 존재하지 않을 경우 paymentAmount 기반으로 자동 계산
- 로직 순서
  1. 파라미터 검증
  2. transaction_id 생성
  3. payment_id 생성
  4. transaction_id와 payment_id 중복 생성 체크 필요 (구현 X)
  5. 카드정보 암호화
  6. 카드사요청 
     1. 장애 발생시 failover 처리
        * timeout 등으로 내 서비스에서는 실패하였지만, 카드사에서는 성공처리 되었을 수 있음
        * 카드사 망취소 API 호출 (일부 PG사에서 제공)
        * transaction_fail_log 실패 로그 저장
  7. 데이터 저장
     1. 장애 발생시 failover 처리
  8. 후처리(알림톡 발송, 카프카메세지 발행 등)이 존재할 경우 코루틴으로 비동기 처리
  9. API 응답

### 5. 취소/부분취소 API
```
POST localhost:8080/v1/payment/cancel

Request
{
    "transactionId": "25634551737585701566",
    "paymentAmount": 1000,
    "vatAmount": 100 //Optional
}

Response
{
    "success": true,
    "message": "success",
    "data": {
        "transactionId": "25634551737585701566",
        "paymentId": "26085030213722341840",
        "cardTransactionData": "_446CANCEL____260850302137223418402275116277791__..."
    }
}
```
- 취소 / 부분취소도 독립적인 개별 거래건이므로 PUT이 아닌 POST로 정의
- 요구사항 기반으로 파라미터 정의
- 옵션필드인 vatAmount가 존재하지 않을 경우 paymentAmount 기반으로 자동 계산
- 로직순서
  1. 파라미터 검증
  2. transaction_id로 거래건 조회
     1. 거래건 미 존재시 Excpetion 발생
  3. 금액으로 취소타입 체크
     1. 전체취소 : 결제금액 - 취소시도금액 = 0
     2. 부분취소 : 결제금액 - 취소시도금액 > 0
     3. 부분취소 : 결제금액 - 취소완료금액 - 취소시도금액 > 0
     4. 전체취소 : 결제금액 - 취소완료금액 - 취소시도금액 = 0
     5. 이외 Exception 발생
        * 부가세 단수로 인한 마지막 취소에서 실패 발생 확인
        * 정확한 정책을 알지 못하여 단수 처리는 하지 않음
     6. 매번 취소건 마다 금액 체크를 하기 때문에 연속적인 부분취소 가능 (테스트 코드는 작성하지 못 함)
  4. payment_id 생성
  5. 카드사 호출
     1. 장애발생시 failover 처리
  6. 데이터 저장 및 Balance 테이블 업데이트
     1. 장애발생시 failover 처리
  7. 후처리(알림톡 발송, 카프카메세지 발행 등)이 존재할 경우 코루틴으로 비동기 처리
  8. API 응답


### 6. 트랜잭션 조회 API
```
GET localhost:8080/v1/transactions/25634551737585701566

Response
{
    "success": true,
    "message": "success",
    "data": {
        "transactionId": "25634551737585701566",
        "transactionType": "부분취소",
        "paymentTransactionAmount": 10000,
        "paymentVatAmount": 909,
        "cancelTransactionAmount": 100,
        "cancelVatAmount": 9,
        "payments": [
            {
                "paymentId": "54137175211417526425",
                "cardNo": "22751*****791",
                "cardValidationCode": 123,
                "cardExpiredDate": "1022",
                "cardInstallmentMonth": 0,
                "paymentType": "결제",
                "paymentAmount": 10000,
                "vatAmount": 909,
                "paymentBalanceAmount": 10000,
                "vatBalanceAmount": 909,
                "createdAt": "2022-07-17T15:32:23.060355"
            },
            {
                "paymentId": "26085030213722341840",
                "cardNo": "22751*****791",
                "cardValidationCode": 123,
                "cardExpiredDate": "1022",
                "cardInstallmentMonth": 0,
                "paymentType": "취소",
                "paymentAmount": 100,
                "vatAmount": 9,
                "paymentBalanceAmount": 9900,
                "vatBalanceAmount": 900,
                "createdAt": "2022-07-17T15:32:23.060371"
            }
        ],
        "createdAt": "2022-07-17T15:09:36.413985",
        "updatedAt": "2022-07-17T15:23:03.242732"
    }
}
```

### 7. 결제/취소건 조회 API
```
GET localhost:8080/v1/transactions/25634551737585701566/payments/54137175211417526425

Response
{
    "success": true,
    "message": "success",
    "data": {
        "transactionId": "25634551737585701566",
        "transactionType": "부분취소",
        "paymentTransactionAmount": 10000,
        "paymentVatAmount": 909,
        "cancelTransactionAmount": 100,
        "cancelVatAmount": 9,
        "payments": [
            {
                "paymentId": "54137175211417526425",
                "cardNo": "22751*****791",
                "cardValidationCode": 123,
                "cardExpiredDate": "1022",
                "cardInstallmentMonth": 0,
                "paymentType": "결제",
                "paymentAmount": 10000,
                "vatAmount": 909,
                "paymentBalanceAmount": 10000,
                "vatBalanceAmount": 909,
                "createdAt": "2022-07-17T15:33:45.679387"
            }
        ],
        "createdAt": "2022-07-17T15:09:36.413985",
        "updatedAt": "2022-07-17T15:23:03.242732"
    }
}
```

### 8. 암호화
- 암호화는 복호화를 해야하므로 AES128 사용
- 인터넷 예시 코드를 참고하여 작정


### 9. 20자리 Uuid
- 인터넷 예시 코드를 참고하여 작성


### 10. 파라미터 Validation
- implementation("org.springframework.boot:spring-boot-starter-validation") 에서 제공하는 기능으로 해결


### 11. @RestControllerAdvice
- 에러 발생시 @RestControllerAdvice에서 Exception을 핸들링 하여 공통화된 포맷으로 응답을 내려주도록 함


### 12. (선택문제) Multi Thread 환경 대비
#### 12-1 결제 : 하나의 카드번호로 동시에 결제를 할 수 없습니다.
- 하나의 카드번호로 동시에 결제할 수 없다라는 문장을 완벽하게 이해하지 못 했습니다.
- 현실 세계에서는 충분히 가능하지 않나요? A와 B가 하나의 카드로 동시에 결제할 수 있을 것 같은데, 동일한 결제건인지 판단하기 위한 아이디어가 떠오르지 않았습니다.
- 본 과제에서는 transaction_id와 payment_id를 PK로 설정했기 때문에 동일한 값에 의한 인서트는 막혀 있습니다.

#### 12-2 전체취소 : 결제 한 건에 대한 전체취소를 동시에 할 수 없습니다.
- pessimistic lock 
  * pessimistic lock을 설정하면 DB에서 Lock을 걸고, 이후에 들어오는 트랜잭션을 차단하는걸로 알고 있습니다.
  * 아직 실무에서 사용하지 않아 본 프로젝트에는 적용해보지 않았습니다.
- redis를 활용하는 방법
  * 하나의 트랜잭션에 대해 취소 요청이 들어왔을 때 Redis에 기록을 남겨서, 취소가 완료되기 전까지 접근을 차단하는 방법이 있을 것 같습니다.
  * 취소 요청 > Redis 조회 > Redis에 데이터가 없으면 기록 > 취소로직 시작 > 취소로직 완료 > Redis에 데이터 삭제 

#### 12-3 부분취소 : 결제 한 건에 대한 부분취소를 동시에 할 수 없습니다.
- 12-1 결제건과 마찬가지로 실제로 하나의 거래건에 대하여 A와 B가 동일한 정보의 부분취소를 요청할 수 있을 것 같고, 동일한 부분취소인지 판단하기 위한 아이디어가 떠오르지 않았습니다.
- 잔액이 10000원 남았는데, 10000원 취소가 동시에 진입한 경우라면
  * pessimistic lock을 사용하면 되는걸로 알고 있습니다.
  * 카드사에서 첫번째 요청은 처리하고, 두번째 요청은 실패처리로 응답할 것이기 때문에 이에 맞게 예외처리를 하면 될 것 같습니다.

