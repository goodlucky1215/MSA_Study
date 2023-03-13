#모든 인프라 환경 설정을 Dockerfile에 포함시키는 방법
#주석을 라인 도중에 발생하면, 인수로 해석되어 예기치 않은 동작이 초래 될 수 있으니 주의해야한다. => 지금은 연습이니 설명을 위해 주석을 중간에 달도록 했다.

#베이스 이미지를 우분투로 설정
FROM ubuntu:14.04

#Dockerfile 작성자 정보 => 일반적으로 작성자 이름과 이메일
MAINTAINER "hamster <milktea@com>"

#생성하는 이미지에 대한 설명을 작성 => 타이틀, 버전, 설명, 라이선스 정보 작성
LABEL perpose = 'dockerfile study' \
      version = '1.0' \
      description = 'Learn how to write dockerfiles'

#필요한 패키지 설치
#**중요** Ubuntu이미지를 베이스 이미지로 지정한 경우는 반드시 apt-get update를 우선 수행해야만 다른 패키지 설치가 가능
RUN apt-get update && apt-get -y install apache2 \
                                         git \
                                         curl \
                                         ssh \
                                         wget

#아파치2 환경 변수를 설정. 아래 설정값은 아파치2 기본값
ENV APACHE2_RUN_USER www-data \
    APACHE2_RUN_GROUP www-data \
    APACHE2_LOG_DIR /var/log/apache2 \
    APACHE2_WEB_DIR /var/www/html \
    APACHE2_PID_FILE /var/run/apache2/apache2.pid

#기본 웹 페이지 생성
RUN echo 'welcome to my web~~' > /var/www/html/index.html

# 80포트를 노출
EXPOSE 80

# RUN, CMD, ENTRYPOINT의 명령어가 실행되는 디렉터리를 설정한다
WORKDIR /var/www/html

# 이미지가 컨테이너로 실행될 때 아파치 서비스를 자동으로 실행되게 한다.
CMD ["/usr/sbin/apache2ctl","-D","FOREGROUND"]