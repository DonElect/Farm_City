FROM eclipse-temurin:17

EXPOSE 4444

WORKDIR /app

COPY env.sh env.sh

COPY target/user-mgmt-service-1.0.1.jar user-mgmt-service.jar

ENTRYPOINT ["/bin/bash" , "-c", "source env.sh && java -jar user-mgmt-service.jar"]
