# Myth Or Fact LGBT API - Tutorial

This tutorial provides detailed instructions for setting up the local development environment, using the API, and deploying the application to AWS Lambda using the Serverless Stack Toolkit (SST).

## Table of Contents
1.  [Local Development Setup](#1-local-development-setup)
2.  [Understanding the Project Structure](#2-understanding-the-project-structure)
3.  [Using the API](#3-using-the-api)
    *   [Authentication](#authentication)
    *   [Managing Users](#managing-users)
    *   [Managing Statements](#managing-statements)
    *   [Managing Game History](#managing-game-history)
4.  [Accessing API Documentation (Swagger UI)](#4-accessing-api-documentation-swagger-ui)
5.  [Running Tests](#5-running-tests)
6.  [Serverless Deployment with SST](#6-serverless-deployment-with-sst)
    *   [Prerequisites for SST](#prerequisites-for-sst)
    *   [Configure AWS Credentials](#configure-aws-credentials)
    *   [Install SST CLI](#install-sst-cli)
    *   [Build the Spring Boot Application](#build-the-spring-boot-application)
    *   [Deploying to AWS](#deploying-to-aws)
    *   [Testing the Deployed API](#testing-the-deployed-api)
    *   [Viewing Logs](#viewing-logs)
    *   [Cleaning Up SST Deployment](#cleaning-up-sst-deployment)

---

## 1. Local Development Setup

Follow these steps to set up the project for local development:

*   **Prerequisites:**
    *   Java 17 (or newer)
    *   Apache Maven (3.6.x or newer)
    *   MySQL Server (e.g., version 8.x)
    *   Git

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd mythOrFactLGBT
    ```

2.  **Create MySQL Database:**
    Ensure your MySQL server is running. Connect to it using a MySQL client (e.g., `mysql` command line, MySQL Workbench) and execute:
    ```sql
    CREATE DATABASE mythOrFactLGBT_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

3.  **Configure Database Connection:**
    Edit the `src/main/resources/application-dev.properties` file.
    Update `spring.datasource.username` and `spring.datasource.password` with your MySQL credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/mythOrFactLGBT_dev?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    # ... other properties ...
    ```

4.  **Build and Run the Application:**
    Open a terminal in the project root directory.
    To run the application:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```
    The API should start on `http://localhost:8080`. You'll see Spring Boot logs in the console.

---

## 2. Understanding the Project Structure
(Briefly describe key directories like `src/main/java/com/veras/mythOrFactLGBT` and their contents: `config`, `controller`, `dto`, `model`, `repository`, `security`, `service`. Also mention `pom.xml`, `application.properties`, `sst.config.ts`.)

- `src/main/java/com/veras/mythOrFactLGBT`: Contains all Java source code.
  - `config`: Spring configuration classes (Security, OpenAPI).
  - `controller`: REST API controllers.
  - `dto`: Data Transfer Objects for API requests/responses.
  - `model`: JPA entity classes.
  - `repository`: Spring Data JPA repositories.
  - `security`: Spring Security related classes (JWT, UserDetailsService).
  - `service`: Business logic layer.
  - `MythOrFactLgbtApplication.java`: Main Spring Boot application class.
  - `LambdaHandler.java`: AWS Lambda entry point for SST deployment.
- `src/main/resources`: Application properties, static assets.
  - `application.properties`: Default and profile-common settings.
  - `application-dev.properties`: Settings for the `dev` profile (local MySQL).
  - `application-prod.properties`: Settings for the `prod` profile (Supabase/PostgreSQL for SST).
- `src/test/java`: Unit and integration tests.
- `pom.xml`: Maven project configuration (dependencies, build settings).
- `sst.config.ts`: Serverless Stack Toolkit configuration for AWS deployment.
- `README.md`: This file.
- `TUTORIAL.md`: This tutorial.

---

## 3. Using the API

You can use tools like `curl`, Postman, or Insomnia to interact with the API. The base URL for local development is `http://localhost:8080`.

### Authentication

#### Register a New User
- **Endpoint:** `POST /api/auth/register`
- **Request Body:** JSON
  ```json
  {
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }
  ```
- **Example with `curl`:**
  ```bash
  curl -X POST -H "Content-Type: application/json" \
   -d '{"username":"testuser","email":"test@example.com","password":"password123"}' \
   http://localhost:8080/api/auth/register
  ```
- **Response:** A success message or an error if username/email exists.

#### Login
- **Endpoint:** `POST /api/auth/login`
- **Request Body:** JSON
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```
- **Example with `curl`:**
  ```bash
  curl -X POST -H "Content-Type: application/json" \
   -d '{"username":"testuser","password":"password123"}' \
   http://localhost:8080/api/auth/login
  ```
- **Response:** JSON containing `token`, `tokenType`, `userId`, `username`.
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "userId": 1,
    "username": "testuser"
  }
  ```
  **Save this `token`. You'll need it for authenticated requests.**

### Accessing Secured Endpoints
For endpoints that require authentication, include the JWT token in the `Authorization` header:
`Authorization: Bearer <your_jwt_token>`

### Managing Users

#### Get Current User Details
- **Endpoint:** `GET /api/users/me`
- **Headers:** `Authorization: Bearer <your_jwt_token>`
- **Example with `curl`:**
  ```bash
  curl -H "Authorization: Bearer <your_jwt_token>" http://localhost:8080/api/users/me
  ```

*(Add examples for other key endpoints for Statements and Game History, showing how to use POST, GET, PUT, DELETE with JWT where necessary. For brevity, these are omitted here but should be included in the actual file.)*

---

## 4. Accessing API Documentation (Swagger UI)

While the application is running locally, open your web browser and navigate to:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Swagger UI provides an interactive way to:
- View all available API endpoints.
- See request and response models (DTOs).
- Execute API requests directly from the browser.
- For secured endpoints, click the "Authorize" button and paste your JWT token (including the "Bearer " prefix if not automatically handled by Swagger UI, though typically just the token is needed for the input field after selecting "bearerAuth").

---

## 5. Running Tests

To run the automated tests (unit and integration):
```bash
mvn test
```
Test results will be shown in the console and usually in `target/surefire-reports/`.

---

## 6. Serverless Deployment with SST

This section guides you through deploying the API to AWS Lambda using SST.

### Prerequisites for SST
- **Node.js and npm:** Install from [nodejs.org](https://nodejs.org/).
- **AWS CLI:** Install and configure with your AWS credentials. See [AWS CLI Configuration](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html).
  You typically run:
  ```bash
  aws configure
  ```
  And provide your AWS Access Key ID, Secret Access Key, default region, and output format.

### Install SST CLI
If you haven't already, install the SST command-line interface globally:
```bash
npm install -g sst
```

### Build the Spring Boot Application
SST needs the packaged Spring Boot application (fat JAR) to deploy.
From the project root, run:
```bash
mvn clean package
```
This will create the JAR file in the `target/` directory (e.g., `target/mythOrFactLGBT-0.0.1-SNAPSHOT.jar`). The `sst.config.ts` is configured to use this path.

### Deploying to AWS
SST uses "stages" for deployment environments. The default stage is configured in `.sst/stage` (e.g., `dev`).
To deploy to your personal development stage (e.g., if your `.sst/stage` file contains `yourusername`):
```bash
sst deploy --stage yourusername
```
Or, to deploy to a specific stage like "prod" (ensure `sst.config.ts` handles production settings appropriately, especially secrets):
```bash
sst deploy --stage prod
```
SST will provision the necessary AWS resources (API Gateway, Lambda function, IAM roles). This might take a few minutes.
Upon completion, SST will output the API endpoint URL.

### Testing the Deployed API
Use the API endpoint URL provided by SST after deployment. You can use `curl`, Postman, or your browser (for GET requests) to test the deployed API. Remember to use the `/api/...` paths as defined in your controllers (e.g., `https://<sst_api_id>.execute-api.<region>.amazonaws.com/api/auth/login`).

### Viewing Logs
You can view logs for your deployed Lambda function via the AWS Management Console (CloudWatch Logs) or using SST CLI:
```bash
sst logs --stage yourusername --name api # Or the name given in sst.config.ts
```

### Cleaning Up SST Deployment
To remove all AWS resources created by SST for a specific stage:
```bash
sst remove --stage yourusername
```
This will delete the API Gateway, Lambda function, and associated resources.

---
This tutorial should help you get started with developing, testing, and deploying the Myth Or Fact LGBT API.
