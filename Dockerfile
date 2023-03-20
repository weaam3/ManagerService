FROM openjdk:8
EXPOSE 8090
ENV database_url=http://localhost:8060
ENV auth_url=http://localhost:8070
COPY target/ManagerService.jar ManagerService.jar
CMD ["java", "-jar", "ManagerService.jar"]
