FROM openjdk:8
COPY target/scala-2.12/quadcode.jar /usr/src/myapp/quadcode.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "quadcode.jar"]