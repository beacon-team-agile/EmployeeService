package com.teamagile.employeeService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {
	
	private final String AWS_ACCESS_KEY = "AKIA5PBPFUHPY5I6M6PG";
	private final String AWS_ACCESS_SECRETKEY = "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";
	private final String AWS_REGION = "us-east-1";
    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_ACCESS_SECRETKEY);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(AWS_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}