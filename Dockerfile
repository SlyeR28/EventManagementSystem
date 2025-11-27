FROM eclipse-temurin:21
ADD target/EventManagementSystemAdvanced-0.0.1-SNAPSHOT.jar  EventManagementSystemAdvanced-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" , "-jar" , "/EventManagementSystemAdvanced-0.0.1-SNAPSHOT.jar"]


