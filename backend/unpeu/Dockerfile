## 1. build stage start

# 기본 이미지는 gradle:jdk11. 별칭으로 builder 지정
FROM gradle:jdk11 AS builder
WORKDIR /api

# --chown : 파일의 소유자나 소유그룹을 변경하기 위한 명령어. [소유자:소유그룹] [파일]
COPY --chown=gradle:gradle . /api

# 권한 주기
RUN chmod +x ./gradlew
RUN ./gradlew clean build

## 2. production stage start

# JDK11 기반으로 도커 이미지 생성
FROM openjdk:11-jdk
COPY --from=builder /api/build/libs/*.jar app.jar

# EXPOSE : 사용할 포트 명시
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

## FROM을 2개이상 사용하는 것을 Multi Stage Build 라고 부른다.
# 컨테이너 이미지를 만들면서 빌드 등에는 필요하지만, 
# 최종 컨테이너 이미지에는 필요 없는 환경을 제거할 수 있도록 단계를 나누어 기반 이미지를 만드는 방법