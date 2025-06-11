## Local Development Setup

To run this application locally, you need to have MySQL installed and running.

1.  **Install MySQL**: If you don't have it, download and install MySQL Community Server from the official website.
2.  **Create a database**:
    ```sql
    CREATE DATABASE supabase_spring_api_dev;
    ```
3.  **Configure MySQL credentials**:
    Open the file `src/main/resources/application-dev.properties`.
    Update the following properties with your MySQL details:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/supabase_spring_api_dev?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```
    Replace `your_mysql_username` and `your_mysql_password` with your actual MySQL credentials. The database name is already set to `supabase_spring_api_dev`.
4.  **Running the application**:
    Once configured, you can run the application using your IDE or via Maven:
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```
    The application will connect to your local MySQL database.
