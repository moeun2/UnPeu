// 도커 파이프라인
pipeline {
    agent none

    options { skipDefaultCheckout(false) }

    stages {
        stage('git pull') { // pull 받아오는 상태
            agent any
            steps {
                echo '===================== git pull ====================='
                mattermostSend (
                    color: "#2A42EE",
                    message: "Build STARTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                )
                checkout scm
            }
        }
        stage('Docker build') { // docker build 상태
            agent any
            steps {
                echo '===================== Docker build ====================='
                script {
                    try {
                        // docker build -t [이미지명] [Dockerfile위치]
                        // Dockerfile를 참조하여 docker 이미지 생성
                        // -t : tag 옵션
                        sh 'docker build -t front:latest ./frontend'
                        sh 'docker build -t back:latest ./backend/unpeu'
                    } catch(e) {
                        mattermostSend (
                            color: "danger",
                            message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                        )
                    }
                }
            }
        }
        stage('Docker run') { // docker 배포 상태
            agent any
            steps {
                echo '===================== Docker run ====================='
                script {
                    try {
                        // 도커 시작 전 기존 컨테이너 정지 및 삭제해야 한다.
                        // 기존 컨테이너 정지 :  docker ps -f name=[컨테이너이름] -q | xargs --no-run-if-empty docker container stop
                        sh 'docker ps -f name=front -q | xargs --no-run-if-empty docker container stop'
                        sh 'docker ps -f name=back -q | xargs --no-run-if-empty docker container stop'

                        // 기존 컨테이너 삭제 : docker container ls -a -f name=[컨테이너이름] -q | xargs -r docker container rm
                        sh 'docker container ls -a -f name=front -q | xargs -r docker container rm'
                        sh 'docker container ls -a -f name=back -q | xargs -r docker container rm'
                        
                        // Dangling 이미지 삭제
                        // Dangling image : 더 이상 컨테이너에 연결되어 있지 않고, 태그가 없어진 이미지. <none>으로 표시한다.
                        // -f : 필터 지정 옵션. dangling 값이 true인 이미지만 출력
                        // -q : 이미지의 id 값만 출력
                        sh 'docker rmi $(docker images -f dangling=true -q)'

                        // 컨테이너 실행 : docker run -d --name [컨테이너이름] -p [호스트포트]:[컨테이너포트] -v [호스트경로]:[컨테이너경로] [이미지명]
                        // -d : 백그라운드 모드
                        sh 'docker run -d --name front -p 80:80 -p 443:443 front:latest'
                        sh 'docker run -d --name back -p 8080:8080 back:latest'
                    } catch(e) {
                        currentBuild.result = "FAILURE"
                    } finally {
                        if(currentBuild.result == "FAILURE") {
                            mattermostSend (
                                color: "danger", 
                                message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                            )
                        }
                        else {
                            mattermostSend (
                                color: "good", 
                                message: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)"
                            )
                        }
                    }
                }
            }
        }
    }
}
