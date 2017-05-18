FROM java:8-jdk

COPY libs/ng2boot-rest-@version@.jar /ng2boot-rest.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ng2boot-rest.jar", "--spring.profiles.active=jenkins" ]
