FROM openjdk:11
VOLUME /tmp
EXPOSE 8091
ADD ./target/ms-currency-type-0.0.1-SNAPSHOT.jar ms-currency-type.jar
ENTRYPOINT ["java","-jar","/ms-currency-type.jar"]