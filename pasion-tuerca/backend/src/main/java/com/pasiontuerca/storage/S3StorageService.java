package com.pasiontuerca.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;

@Service("s3StorageService")
public class S3StorageService implements StorageService {

    @Value("${s3.bucket:}")
    private String bucket;

    @Value("${s3.region:us-east-1}")
    private String region;

    @Value("${s3.accessKey:}")
    private String accessKey;

    @Value("${s3.secretKey:}")
    private String secretKey;

    @Value("${s3.endpoint:}")
    private String endpoint;

    private S3Client s3;

    @PostConstruct
    public void init(){
        S3ClientBuilder b = S3Client.builder().region(Region.of(region));
        if(accessKey!=null && !accessKey.isBlank()){
            b.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)));
        }
        if(endpoint!=null && !endpoint.isBlank()){
            b.endpointOverride(URI.create(endpoint));
        }
        s3 = b.build();
    }

    @Override
    public String store(MultipartFile file) throws IOException {
        String name = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        PutObjectRequest por = PutObjectRequest.builder().bucket(bucket).key(name).build();
        s3.putObject(por, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return "https://"+bucket+".s3."+region+".amazonaws.com/"+name;
    }

    @Override
    public void delete(String url) throws IOException {
        // Not implemented in this skeleton.
    }
}
