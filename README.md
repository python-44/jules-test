# Myth Or Fact LGBT API

This project is a Spring Boot backend API for the "Myth Or Fact LGBT" game. It allows user registration, login, management of game statements (myths/facts), and tracking of game history. The API is secured using JWT. It can be run locally with MySQL or deployed as a serverless application to AWS Lambda using the Serverless Stack Toolkit (SST).

## Features
- User registration and JWT-based authentication.
- CRUD operations for game statements.
- Recording and retrieval of game history and scores.
- API documentation via Swagger UI.
- Local development profile (MySQL) and production profile (PostgreSQL on Supabase).
- Serverless deployment configured with SST.

## Prerequisites
- **Java 17** (or newer)
- **Apache Maven** (3.6.x or newer)
- **MySQL Server** (for local development, e.g., version 8.x)
- **Node.js and npm** (for SST, latest LTS recommended)
- **AWS CLI** (configured with credentials, for SST deployment)
- **SST CLI** (`npm install -g sst`)

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
    *(Using utf8mb4 is good practice for broader character support).*

3.  **Configure Local Database Connection:**
    Open `src/main/resources/application-dev.properties`.
    Update the following properties with your MySQL username and password:
    ```properties
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```
    The `spring.datasource.url` should already be set to use `mythOrFactLGBT_dev` on `localhost:3306`.

4.  **Build the project (optional, Spring Boot Maven plugin can run without pre-packaging for dev):**
    ```bash
    mvn clean install
    ```

## Running Locally
To run the application locally using the `dev` profile (connected to your local MySQL):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
The API will typically be available at `http://localhost:8080`.

## Running Tests
To execute the unit and integration tests:
```bash
mvn test
```

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

## Serverless Deployment (SST)
For deploying to AWS Lambda using SST, please refer to the [TUTORIAL.md](TUTORIAL.md) for detailed instructions.

---
*This README provides a basic guide. For more detailed instructions, especially on API usage and SST deployment, see [TUTORIAL.md](TUTORIAL.md).*
