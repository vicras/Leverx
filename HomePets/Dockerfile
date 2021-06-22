FROM maven:3-jdk-11

VOLUME /home/user/.m2 /root/.m2
COPY ./ /usr/src

WORKDIR /usr/src

RUN mvn clean package