import { SSTConfig } from "sst";
import { Api } from "sst/constructs";

export default {
  config(_input) {
    return {
      name: "myth-or-fact-lgbt",
      region: "us-east-1", // Or your preferred AWS region
    };
  },
  stacks(app) {
    app.stack(function Stack({ stack }) {
      // Database environment variables from issue description (for Supabase PostgreSQL)
      // These should ideally be configured as secrets in SST or AWS Parameter Store for production
      // For simplicity in this setup, we're passing them directly.
      // Ensure these are handled securely in a real production environment.
      const POSTGRES_URL_NON_POOLING = "postgres://postgres.goazwmbvraoengmetdpy:f3qCjViqbpfrg1Hz@aws-0-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require"; // Or the pooler URL if suitable for Lambda
      const SUPABASE_JWT_SECRET = "Qiopu5BdnmUj2YJ0/JvEuqrnpIM3QKBnRYx0ozGfpi+OW5ULPrdG9MPfYZjn9AphRxNmWJQbacey8iKJQIy5pg=="; // Your app's JWT secret

      const api = new Api(stack, "api", {
        // Custom domain can be configured here if needed
        // customDomain: "api.yourdomain.com",
        routes: {
          // Proxy all requests to the Spring Boot application
          // The {proxy+} route catches all paths
          "ANY /{proxy+}": {
            // Assuming the Spring Boot fat JAR is built and placed in a known location
            // The handler points to the Lambda function that will run the Spring Boot app
            // This requires a Lambda adapter for Spring Boot, like AWS Serverless Java Container
            type: "function",
            function: {
              handler: "com.veras.mythOrFactLGBT.LambdaHandler::handleRequest",
              runtime: "java17", // Match your Spring Boot project's Java version
              memorySize: 1536, // Adjust based on application needs
              timeout: 30, // Adjust based on application needs (API Gateway max is 29s for HTTP APIs)
              jar: "./target/mythOrFactLGBT-0.0.1-SNAPSHOT.jar", // Path to your Spring Boot fat JAR
              environment: {
                // Pass environment variables to the Spring Boot application
                // Spring Boot will pick these up for the 'prod' profile
                SPRING_PROFILES_ACTIVE: "prod",
                SPRING_DATASOURCE_URL: POSTGRES_URL_NON_POOLING,
                SPRING_DATASOURCE_USERNAME: "postgres.goazwmbvraoengmetdpy",
                SPRING_DATASOURCE_PASSWORD: "f3qCjViqbpfrg1Hz",
                SUPABASE_JWT_SECRET: SUPABASE_JWT_SECRET,
                // Any other environment variables your Spring Boot app needs for production
              },
              // If using AWS Serverless Java Container, you might need to configure specific settings
              // e.g., for request/response marshalling or initialization types.
            },
          },
        },
      });

      // Show the API endpoint in the output
      stack.addOutputs({
        ApiEndpoint: api.url,
      });
    });
  },
} satisfies SSTConfig;
