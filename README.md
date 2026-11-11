# Knicks Spring Boot Application

A small Spring Boot application for managing customers, cars, and payments with Spring Data JPA and an in-memory H2 database.

## Requirements

- Java 25
- Maven 3.10 or newer

## Run the application

```bash
mvn spring-boot:run
```

The application starts on port 8085:

http://localhost:8085

## Database console

The H2 console is available at:

http://localhost:8085/h2-console

Use these connection settings:

- JDBC URL: `jdbc:h2:mem:parkifydb`
- User: `sa`
- Password: leave blank

The database is in memory, so its data is deleted when the application stops.

## Test

```bash
mvn test
```

## Project structure

- `KnicksApplication` — application entry point and startup runner
- `Models/Customer` — customer JPA entity
- `Models/Car` — car JPA entity
- `Models/Payment` — payment JPA entity
- `*Repo` — Spring Data JPA repositories
- `*Manager` — startup data-management services

The application uses Spring Boot, Spring Data JPA, Hibernate, H2, Lombok, and Maven.
