# 📝 BucketList CRUD (Java)

## 📌 프로젝트 소개
이 프로젝트는 **Java 언어만을 활용**하여 버킷리스트 CRUD(Create, Read, Update, Delete) 기능을 구현한 프로그램입니다.  
파일 기반 데이터 저장 방식을 사용하여 회원이 원하는 버킷리스트를 추가/조회/수정/삭제할 수 있습니다.

- 🛠 **개발 언어**: Java (JDK 17)

- 📂 **프로젝트 구조**:
  
hch-bucket/

└─ src/main/java/com/bucket/hch/app/

├─ aggregate/ # 엔티티 및 VO 클래스 (예: bucketlist, AccountStatus)

├─ repository/ # Repository 계층 (데이터 저장/조회)

└─ run/ # Application 실행 클래스


---

## ⚙️ 기능 소개
- **Create**: 새로운 버킷리스트 항목 추가
- **Read**: 전체/단일 버킷리스트 조회(제목+내용)
- **Update**: 기존 항목 제목, 내용 수정
- **Delete**: 완료된 버킷리스트 항목 삭제
- **기타**: 조회수 증가, 상태값(AccountStatus) 관리

---

## 💻 실행 방법
```bash
# 저장소 클론
git clone https://github.com/사용자명/레포명.git

# 프로젝트 빌드/실행 (예시)
cd hch-bucket
javac src/main/java/com/bucket/hch/app/run/Application.java
java com.bucket.hch.app.run.Application

```

 ---

## 🙋‍♂️ 개발자
- 허창훈 ([@hch-huni2](https://github.com/hch-huni2))
