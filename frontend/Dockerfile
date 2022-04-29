## 1. build stage start

# 기본 이미지는 node:alpine. 별칭으로 builder 지정
FROM node:lts-alpine as builder

# WORKDIR : 작업디렉토리 /app로 이동. app이 없으면 생성 후 이동
# COPY : [호스트에서 복사할 파일/경로] [이미지에서 파일이 위치할 경로]
WORKDIR /app
COPY package*.json ./

COPY . .
RUN npm install
RUN npm run build

## 2. production stage start

FROM nginx:stable-alpine as production

RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

# --from : 'builder'라는 이름의 이미지로부터 복사한다
# 빌드 단계의 이미지 작업디렉토리(/app/dist)에서 nginx의 디렉토리로 파일 복사
COPY --from=builder /app/dist usr/share/nginx/html

# EXPOSE : 사용할 포트 명시
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

## FROM을 2개이상 사용하는 것을 Multi Stage Build 라고 부른다.
# 컨테이너 이미지를 만들면서 빌드 등에는 필요하지만, 
# 최종 컨테이너 이미지에는 필요 없는 환경을 제거할 수 있도록 단계를 나누어 기반 이미지를 만드는 방법