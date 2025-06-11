# Myth Or Fact LGBT API

This project is a Spring Boot backend API for the "Myth Or Fact LGBT" game. It allows user registration, login, management of game statements (myths/facts), and tracking of game history. The API is secured using JWT. It can be run locally with MySQL or deployed to various cloud platforms that support Java applications, such as Render.

## Features
- User registration and JWT-based authentication.
- CRUD operations for game statements.
- Recording and retrieval of game history and scores.
- API documentation via Swagger UI.
- Local development profile (MySQL) and production profile (PostgreSQL on Supabase).
- Standard Spring Boot packaging for easy deployment.

## Prerequisites
- **Java 17** (or newer)
- **Apache Maven** (3.6.x or newer)
- **MySQL Server** (for local development, e.g., version 8.x)
- **Spring Tools Suite (STS)** (Recommended IDE for local development, see tutorial)
- **Git** (for version control)

## Local Development Setup

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd mythOrFactLGBT
    ```

2.  **Create MySQL Database:**
    Connect to your local MySQL server and run:
    ```sql
    CREATE DATABASE mythOrFactLGBT_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

3.  **Configure Local Database Connection:**
    Open `src/main/resources/application-dev.properties`.
    Update the following properties with your MySQL username and password:
    ```properties
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```
    The `spring.datasource.url` should already be set to use `mythOrFactLGBT_dev` on `localhost:3306`.

4.  **Import into Spring Tools Suite (STS):**
    See the [TUTORIAL.md](TUTORIAL.md) for detailed instructions on importing and running the project in STS.

5.  **Build the project (optional, STS and Spring Boot Maven plugin can run without pre-packaging for dev):**
    ```bash
    mvn clean install
    ```

## Running Locally (using Maven)
If not running from STS, to run the application locally using the `dev` profile (connected to your local MySQL) via Maven:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
The API will typically be available at `http://localhost:8080`.

## Running Tests
To execute the unit and integration tests:
```bash
mvn test
```
You can also run tests from STS.

## API Documentation (Swagger UI)
Once the application is running locally, you can access the Swagger UI for API documentation and testing at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

*(The Swagger path might differ if `server.servlet.context-path` or `springdoc.swagger-ui.path` properties are changed).*

## API Endpoints Overview
- **Authentication:** `/api/auth/register`, `/api/auth/login`
- **Users:** `/api/users/me`, `/api/users/{id}`
- **Statements:** `/api/statements` (CRUD)
- **Game History:** `/api/gamehistory`
Refer to Swagger UI for detailed request/response formats and to try out the endpoints.

## Deployment (e.g., to Render)
This application is a standard Spring Boot application and can be deployed to various cloud platforms like Render.
For general guidance on preparing for deployment and specific instructions for STS, see the [TUTORIAL.md](TUTORIAL.md).

---
*This README provides a basic guide. For more detailed instructions, especially on API usage, STS setup, and deployment, see [TUTORIAL.md](TUTORIAL.md).*
