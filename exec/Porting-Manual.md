# Porting Manual

## 🎮Tech Stack

### Front-End

<img src="https://img.shields.io/badge/Vue.js-2.6.14-4FC08D?style=for-the-badge&logo=Vue.js&logoColor=white"/> <img src="https://img.shields.io/badge/vuetify-2.6.4-1867C0?style=for-the-badge&logo=vuetify&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">

<details>
	<summary><strong>API 및 라이브러리 등</strong> 자세히보기</summary>
	<div markdown="1">
		<table>
            <th>Package</th>
            <th>Version</th>
            <th>Comment</th>
			<tr>
				<td>vue-router</td>
				<td>3.5.3</td>
                <td>요청 URL에 따라 브라우저에서 dom 변경</td>
			</tr>
			<tr>
				<td>vuex</td>
				<td>3.6.2</td>
                <td>vue 상태 관리 패턴 라이브러리</td>
			</tr>
			<tr>
				<td>axios</td>
				<td>0.26.1</td>
                <td>API 통신 라이브러리</td>
			</tr>
            <tr>
				<td>Sweetalert2</td>
				<td>5.0.2</td>
                <td>다양한 기능과 디자인을 가진 Alert창을 구현</td>
			</tr>
            <tr>
				<td>Vue2Editor</td>
				<td>2.10.3</td>
                <td>다이어리 게시글 에디터</td>
			</tr>
            <tr>
				<td>quill-image-resize-module</td>
				<td>3.0.0</td>
                <td>에디터 이미지 크기 조절 가능</td>
			</tr>
            <tr>
				<td>dayjs</td>
				<td>0.26.1</td>
                <td>가장 가벼운 날짜 관련 라이브러리</td>
			</tr>
            <tr>
				<td>flatpickr</td>
				<td>04.6.13</td>
                <td>달력 라이브러리</td>
			</tr>
		</table>
	</div>
</details>

### Back-End

<img src="https://img.shields.io/badge/java-openJDK11-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Spring Boot-2.5.3-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/gradle-7.4.1-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-8.0.28-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>

<details>
	<summary><strong>API 및 라이브러리 등</strong> 자세히보기</summary>
	<div markdown="1">
		<table>
            <th>Package</th>
            <th>Version</th>
            <tr>
                <td>Spring Boot Data JPA</td>
                <td>2.5.10</td>
            </tr>
            <tr>
                <td>Spring Security</td>
                <td>5.5.5</td>
            </tr>
            <tr>
                <td>JWT</td>
                <td>3.10.3</td>
            </tr>
            <tr>
                <td>JUnit</td>
                <td>4.13.1</td>
            </tr>
            <tr>
                <td>Lombok</td>
                <td>1.18.20</td>
            </tr>
            <tr>
                <td>Swagger</td>
                <td>3.0.0</td>
            </tr>
            <tr>
                <td>spring-boot-starter-mail</td>
                <td>2.5.10</td>
            </tr>
		</table>
	</div>
</details>

### Infra

<img src="https://img.shields.io/badge/AWS EC2-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">

### Team Collaboration

<img src="https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=Jira Software&logoColor=white"> <img src="https://img.shields.io/badge/Mattormost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white"> <img src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white"> <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"> <img src="https://img.shields.io/badge/Google Drive-4285F4?style=for-the-badge&logo=Google Drive&logoColor=white">



## ⚙Install and Usage

### 📍 Preparation

- OS : Windows10 (Local), Ubuntu 20.04 (Deploy)

- Install [JDK 11 GA](https://jdk.java.net/archive/)

- Install [Node.js / NPM](https://nodejs.org/ko/)

- Install [MySQL 8.0](https://dev.mysql.com/downloads/mysql/)

- Clone Repository

  ```bash
  git clone <repo URL>
  ```

- Setting Database

  `application.properties`에 OS 환경에 맞게 DB 설정

### 📍 Installation

**Unpeu** 서비스를 `Local`에서 사용하기 위해서는 다음과 같은 방법으로 실행합니다. 

- **backend 실행**

  ```bash
  cd backend/unepu
  ./gradlew clean build
  ```

  ```bash
  cd build/libs
  java -jar [생성된 파일 이름].jar
  ```

  > IntelliJ, STS와 같은 IDE에 import 후 실행해도 됩니다.

- **frontend 실행**

  ```bash
  cd frontend
  npm install
  ```

  ```bash
  npm run serve
  ```



## 🗂**Directory Structure**

### Front-End

```bash
📦frontend
 ┣ 📂nginx              /* nginx 설정 파일 */
 ┣ 📂src
   ┣ 📂api              /* REST API 파일 */
   ┣ 📂assets           /* image, css, font, music 리소스 */
   ┣ 📂components
   ┃ ┣ 📂diary          /* 다이어리 관련 컴포넌트 */
   ┃ ┃ ┗ 📂comment
   ┃ ┣ 📂eventRoom      /* 이벤트룸 관련 컴포넌트 */
   ┃ ┣ 📂login          /* 소셜 로그인 관련 컴포넌트 */
   ┃ ┣ 📂option         /* 피드백, 링크 공유 관련 컴포넌트 */
   ┃ ┗ 📂present	
   ┃ ┃ ┣ 📂management   /* 선물 관리 관련 컴포넌트 */
   ┃ ┃ ┗ 📂payment      /* 선물 결제, 메세지 등록 관련 컴포넌트 */
   ┣ 📂config           /* 개발 환경 상수 저장 */
   ┣ 📂plugins          /* REST API 파일 */
   ┣ 📂router		
   ┣ 📂store            /* Vuex의 store 모듈화 */
   ┣ 📂views            /* 페이지 단위의 Vue 파일 */
   ┣ 📜App.vue
   ┗ 📜main.js
```

### Back-End

```
📦unpeu
 ┗ 📂src
   ┣ 📂main
     ┣ 📂java
     ┃ ┗ 📂com
     ┃   ┗ 📂unpeu
     ┃     ┣ 📂api			/* Controller 단위의 클래스 */
     ┃     ┃ ┣ 📂diary
     ┃     ┃ ┣ 📂mail
     ┃     ┃ ┣ 📂present
     ┃     ┃ ┗ 📂users
     ┃     ┣ 📂config
     ┃     ┃ ┣ 📂auth		/* JWT 관련 클래스 */
     ┃     ┃ ┣ 📂common
     ┃     ┃ ┣ 📂exception	/* Exception 처리 관련 클래스 */
     ┃     ┃ ┣ 📂media		/* 이미지 처리 관련 클래스 */
     ┃     ┃ ┣ 📂scheduling	/* Controller 단위의 클래스 */
     ┃     ┃ ┗ 📂security	/* Security 관련 클래스 */
     ┃     ┣ 📂domain
     ┃     ┃ ┣ 📂entity		/* JPA entity 클래스 */
     ┃     ┃ ┣ 📂repository	/* JPA repository 클래스 */
     ┃     ┃ ┣ 📂request	/* 요청 DTO 클래스 */
     ┃     ┃ ┗ 📂response	/* 응답 DTO 클래스 */
     ┃     ┣ 📂service		/* service 단위의 클래스 */
     ┃     ┃ ┣ 📂iface
     ┃     ┃ ┗ 📂impl		
     ┃     ┗ 📜UnpeuApplication.java
     ┗ 📂resources
       ┣ 📂globals			/* 이미지 파일 경로 */
       ┗ 📜application.properties	/* db설정 properties 존재 */
```
