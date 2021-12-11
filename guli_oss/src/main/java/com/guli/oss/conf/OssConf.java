package com.guli.oss.conf;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OssConf implements InitializingBean {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.keyid}")
    private String keyId;

    @Value("${aliyun.oss.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.bucketname}")
    private String bucketName;

    @Value("${aliyun.oss.filehost}")
    private String fileHost;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String FILE_HOST;


    @Override
    public void afterPropertiesSet() throws Exception {

        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
        FILE_HOST = fileHost;

    }
}
