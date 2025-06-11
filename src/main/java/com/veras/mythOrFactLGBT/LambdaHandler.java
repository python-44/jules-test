package com.veras.mythOrFactLGBT;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            // Ensure your main application class is passed here
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(MythOrFactLgbtApplication.class);
            // If you have specific Spring profiles for Lambda, you can activate them here:
            // handler.activateSpringProfiles("lambda");
            // For performance, you can enable asynchronous initialization:
            // handler.setRefreshContext(false); // For Spring Boot 2.x with `spring.main.lazy-initialization=true`
            // For Spring Boot 3.x, refer to AWS Serverless Java Container documentation for latest optimization practices.
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
}
