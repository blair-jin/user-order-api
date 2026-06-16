# User Order API
Spring boot 기반의 사용자, 상품, 장바구니, 주문 API 프로젝트 입니다.

## 1. Project Goal
이 프로젝트는 실제 서비스 환경에서 자주 발생하는 인증, 권한, 주문 처리, 재고 차감, 조회 성능, 트랜잭션 경계 등을 학습하기 위해 만들었습니다.

주요 목표는 다음과 같습니다.

- JWT 기반 인증/인가
- Redis 기반 Refresh Token 관리
- User / Product / Cart / Order 도메인 분리
- 주문 시, 재고 차감과 동시성 제어
- QueryDSL 기반 상품 검색
- Specification 기반 주문 검색
- MySQL 인덱스와 실행계획 개선
- Docker 기반 로컬 실행 환경 구성

## 2. Tech Stack
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- QueryDSL
- MySQL
- Redis
- Docker / Docker compose
- Gradle

## 3. Domain Overview

### User
회원 가입, 로그인, 내 정보 조회, 프로필 수정, 비밀번호 변경

### Product
관리자의 상품 등록, 수정, 삭제
사용자의 상품 조회, 검색

### Cart
사용자의 장바구니 관리
상품 추가 / 수량 감소 / 삭제

### Order
장바구니 상품을 기반으로 주문을 생성. 주문 생성 시 상품 재고 차감

## 4. Architecture

```
Controller
: Http 요청과 응답을 담당
    -> UseCase
    : 도메인의 흐름을 조합
        -> CommandService / QueryService
        : 생성/수정/삭제/조회를 담당
            -> Reader
            : 조회와 존재여부 확인, 예외 처리 담당
                -> Repository
```

## 5. Main Feature

### Authentication

- 회원가입
- 로그인
- Access Token 발급
- Refresh Token 재발급

### Product

- 상품 등록
- 상품 수정
- 상품 삭제
- 상품 검색

### Cart

- 장바구니 상품 추가
- 수량 감소
- 삭제

### Order

- 주문 생성
- 주문 조회
- 주문 취소
