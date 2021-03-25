# municipalsurvey

Municipal Survey is a survey content management system created with Spring currently under development.

Create and manage surveys. View raw data or graphs generated with the Google Charts API.

# Running the application

To run the project standalone, run **mvnw spring-boot:run**

To run the application with docker:

1. Run **mvnw package**
2. Run **docker build -t municipalsurvey .**
3. Run **docker run -p 8080:8080 municipalsurvey**


