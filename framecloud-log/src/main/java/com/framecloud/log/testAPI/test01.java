package com.framecloud.log.testAPI;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class test01 {

    public static void main(String[] args) {

        String requestMapping = "post";
        String post_api = "/api/tenants/73697/rest/channels/638/messages";
        String X_Auth_Expires = "300000";
        String client_id = "815ba28b-185e-41f5-ab2c-ff0bb6c7e2e5";
        String Client_Secret = "50eb63176f097bebd1807a4cd4bca340";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

            String md5 = stringToMD5("{\"bodies\":[{\"msg\":\"testmsg2\",\"type\":\"txt\"}],\"ext\":{\"queue_id\":\"\",\"queue_name\":\"\",\"agent_username\":\"\",\"visitor\":{\"user_nickname\":\"userNickname\",\"true_name\":\"userTrueName\",\"qq\":\"999999999\",\"email\":\"test@test.test\",\"phone\":\"18888888888\",\"company_name\":\"companyName\",\"description\":\"description\"}},\"msg_id\":\"14332423141234234\",\"origin_type\":\"rest\",\"from\":\"test_weichat_visitor05\",\"timestamp\":1468832767680}");

            String secret = "secret";
            String message = requestMapping+" "+post_api+" "+md5;

            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hash);
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
