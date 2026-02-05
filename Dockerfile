FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY HotelBooking.jar HotelBooking.jar

EXPOSE 8080

CMD ["java", "-jar", "HotelBooking.jar"]
