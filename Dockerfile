FROM java:8

ADD target/jsug-shop-1.0-SNAPSHOT.jar /opt/spring/jsug-shop.jar
EXPOSE 8080
WORKDIR /opt/spring/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "jsug-shop.jar"]