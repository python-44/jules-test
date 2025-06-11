# Myth Or Fact LGBT API - Tutorial

This tutorial provides detailed instructions for setting up the local development environment using Spring Tools Suite (STS), using the API, and preparing the application for deployment to platforms like Render.

## Table of Contents
1.  [Local Development Setup with Spring Tools Suite (STS)](#1-local-development-setup-with-spring-tools-suite-sts)
    *   [Prerequisites](#prerequisites)
    *   [Importing Project into STS](#importing-project-into-sts)
    *   [Database Setup (MySQL)](#database-setup-mysql)
    *   [Configuring Database Connection in Project](#configuring-database-connection-in-project)
    *   [Running the Application in STS](#running-the-application-in-sts)
2.  [Understanding the Project Structure](#2-understanding-the-project-structure)
3.  [Using the API](#3-using-the-api)
    *   [Authentication](#authentication)
    *   [Managing Users](#managing-users)
    *   [Managing Statements](#managing-statements)
    *   [Managing Game History](#managing-game-history)
4.  [Accessing API Documentation (Swagger UI)](#4-accessing-api-documentation-swagger-ui)
5.  [Running Tests](#5-running-tests)
    *   [Running Tests with Maven](#running-tests-with-maven)
    *   [Running Tests in STS](#running-tests-in-sts)
6.  [Preparing for Deployment (e.g., to Render)](#6-preparing-for-deployment-eg-to-render)
    *   [Build the Executable JAR](#build-the-executable-jar)
    *   [General Deployment Guidance for Render](#general-deployment-guidance-for-render)

---

## 1. Local Development Setup with Spring Tools Suite (STS)

### Prerequisites
- **Java 17** (or newer JDK)
- **Apache Maven** (3.6.x or newer - often bundled with STS or can be configured)
- **MySQL Server** (e.g., version 8.x)
- **Spring Tools Suite (STS)**: Download and install from [spring.io/tools](https://spring.io/tools).
- **Git**

### Importing Project into STS
1.  **Clone the repository** (if you haven't already):
    ```bash
    git clone <repository-url>
    ```
2.  **Launch STS.**
3.  Go to **File -> Import...**.
4.  In the Import dialog, expand **Maven** and select **Existing Maven Projects**. Click **Next**.
5.  For **Root Directory**, click **Browse...** and navigate to the directory where you cloned the `mythOrFactLGBT` project.
6.  STS should automatically detect the `pom.xml` file. Ensure it is checked.
7.  Click **Finish**. STS will import the project and download dependencies (this may take some time).

### Database Setup (MySQL)
1.  Ensure your MySQL server is running.
2.  Connect to MySQL using a client (e.g., `mysql` command line, MySQL Workbench, DBeaver) and execute:
    ```sql
    CREATE DATABASE mythOrFactLGBT_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

### Configuring Database Connection in Project
1.  In STS, navigate to `src/main/resources` in the Project Explorer.
2.  Open `application-dev.properties`.
3.  Update `spring.datasource.username` and `spring.datasource.password` with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/mythOrFactLGBT_dev?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```

### Running the Application in STS
1.  In the Project Explorer or Package Explorer, find the main application class: `src/main/java/com/veras/mythOrFactLGBT/MythOrFactLgbtApplication.java`.
2.  Right-click on `MythOrFactLgbtApplication.java`.
3.  Select **Run As -> Spring Boot App**.
4.  The application will start, and you'll see logs in the STS Console view. By default, it uses the `dev` profile (due to `spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}` in `application.properties` and no `SPRING_PROFILES_ACTIVE` environment variable being set).
5.  The API should be available at `http://localhost:8080`.

   **To explicitly set a profile (e.g., 'dev') in STS Run Configuration:**
   1. Right-click `MythOrFactLgbtApplication.java` -> Run As -> Run Configurations...
   2. Select "Spring Boot App" -> "MythOrFactLgbtApplication".
   3. Go to the "(x)= Arguments" tab. In "VM arguments", you can add: `-Dspring.profiles.active=dev`
   4. Or, go to the "Spring Boot" tab and under "Profile", type `dev`.
   5. Click Apply, then Run.

---

## 2. Understanding the Project Structure
(This section can largely remain the same as before, just ensure paths like `LambdaHandler.java` are removed if they were mentioned)
- `src/main/java/com/veras/mythOrFactLGBT`: Contains all Java source code.
  - `config`: Spring configuration classes (Security, OpenAPI).
  - `controller`: REST API controllers.
  - `dto`: Data Transfer Objects for API requests/responses.
  - `model`: JPA entity classes.
  - `repository`: Spring Data JPA repositories.
  - `security`: Spring Security related classes (JWT, UserDetailsService).
  - `service`: Business logic layer.
  - `MythOrFactLgbtApplication.java`: Main Spring Boot application class.
- `src/main/resources`: Application properties, static assets.
  - `application.properties`: Default and profile-common settings.
  - `application-dev.properties`: Settings for the `dev` profile (local MySQL).
  - `application-prod.properties`: Settings for the `prod` profile (PostgreSQL for production).
- `src/test/java`: Unit and integration tests.
- `pom.xml`: Maven project configuration.
- `README.md`: Project overview.
- `TUTORIAL.md`: This tutorial.

---

## 3. Using the API
(This section remains the same - `curl` examples for registration, login, and accessing secured endpoints.)

---

## 4. Accessing API Documentation (Swagger UI)
(This section remains the same - instructions to access Swagger UI at `http://localhost:8080/swagger-ui.html`.)

---

## 5. Running Tests

### Running Tests with Maven
From the project root in a terminal:
```bash
mvn test
```

### Running Tests in STS
1.  In the Project Explorer, right-click on the project root (`mythOrFactLGBT`), a specific package, or a test class.
2.  Select **Run As -> JUnit Test**.
3.  Test results will appear in the JUnit view in STS.

---

## 6. Preparing for Deployment (e.g., to Render)

This application is a standard Spring Boot application. Platforms like Render can typically deploy it by running its executable JAR file.

### Build the Executable JAR
To create the executable "fat JAR" that contains all dependencies:
1.  Open a terminal in the project root.
2.  Run the Maven package command:
    ```bash
    mvn clean package
    ```
3.  The JAR file will be created in the `target/` directory (e.g., `target/mythOrFactLGBT-0.0.1-SNAPSHOT.jar`).

### General Deployment Guidance for Render

When deploying to Render (or similar platforms like Heroku, Google Cloud Run, AWS Elastic Beanstalk):

1.  **Service Type:** Choose "Web Service" on Render.
2.  **Environment/Runtime:** Select a Java environment. Render often auto-detects Spring Boot applications.
3.  **Build Command (if Render builds from your repository):**
    Render might auto-detect a Maven project and run `mvn clean package -DskipTests` or similar. You can usually customize this to `mvn clean package`.
4.  **Start Command:**
    This command runs your packaged application. You must activate the `prod` profile.
    ```bash
    java -Dspring.profiles.active=prod -jar target/mythOrFactLGBT-0.0.1-SNAPSHOT.jar
    ```
5.  **Environment Variables:**
    You will need to configure these in Render's dashboard for your service:
    *   `SPRING_PROFILES_ACTIVE`: `prod`
    *   `SPRING_DATASOURCE_URL`: The JDBC URL for your production Supabase PostgreSQL database. Example (from `application-prod.properties`): `jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require`
    *   `SPRING_DATASOURCE_USERNAME`: Your Supabase database username (e.g., `postgres.goazwmbvraoengmetdpy`)
    *   `SPRING_DATASOURCE_PASSWORD`: Your Supabase database password.
    *   `SUPABASE_JWT_SECRET`: Your application's JWT secret (the long string you provided).
    *   `SERVER_PORT`: Render typically sets a `PORT` environment variable. Spring Boot automatically picks this up. If you need to override or ensure a specific port, you can set `server.port=${PORT:8080}` in `application-prod.properties` or pass `-Dserver.port=$PORT` in the start command, but usually, Spring Boot's default behavior is sufficient.

6.  **Database:**
    Ensure your Supabase (or other production PostgreSQL) database is accessible from Render's services. The connection details are provided via the environment variables above.

7.  **Health Checks (Render):**
    Render will likely perform health checks. Spring Boot Actuator (`spring-boot-starter-actuator`) provides a default `/actuator/health` endpoint. If Actuator is not included, Render might ping the root `/` or a custom path you define. Ensure your application responds with a `200 OK` on the health check path. (Note: Actuator is not currently in the pom.xml, so default Spring MVC behavior on `/` or error page would be the target unless specified).

---
This tutorial should help you get started with developing in STS and deploying the Myth Or Fact LGBT API to platforms like Render.
