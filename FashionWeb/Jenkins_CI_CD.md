- Jenkins란? CI/CD
- item : jenkins에서 사용하고 있는 작업의 최소 단위를 의미
  => item이 여러개 연결되면 파이프라인이 된다.

[ Jenkins 환경 ]

- docker run -d -v jenkins_home:/var/jenkins_home -p 8080:8080 -p 50000:50000 --restart=on-failure --name jenkins-server jenkins/jenkins:lts-jdk11
- Spring Boot는 JDK 8로 개발했는데, Jenkins는 JDK 11을 사용하여 실행해도 되는지?  
  => Jenkins는 Java로 개발되었으며, JDK 11은 JDK 8과 호환성이 있으므로 JDK 11을 사용해도 Spring Boot 애플리케이션을 빌드하고 배포하는 데 문제가 없다.
