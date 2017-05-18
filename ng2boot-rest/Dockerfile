FROM java:8-jdk


COPY build/libs/*.jar /rest-api.jar

RUN sh -c 'touch /rest-api.jar'

EXPOSE 8080

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rest-api.jar", "--spring.profiles.active=jenkins"]
