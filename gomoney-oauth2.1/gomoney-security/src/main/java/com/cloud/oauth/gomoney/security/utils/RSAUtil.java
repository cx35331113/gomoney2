package com.cloud.oauth.gomoney.security.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.util.HashMap;

public class RSAUtil {

    private static final String publicKey;
    private static final String privateKey;

    static {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        publicKey = new String(Base64.encodeBase64(pair.getPublic().getEncoded()));
        privateKey= new String(Base64.encodeBase64((pair.getPrivate().getEncoded())));
    }

    /**
     *
     * @param str 加密前JSONObject->Str数据
     * @return 返回加密后数据
     */
    public static String encrypt(String str){
        return SecureUtil.rsa(privateKey,publicKey).encryptBcd(str, KeyType.PrivateKey);
    }

    /**
     *
     * @param str 加密后的字符串
     * @return
     */
    public static String decrypt (String str){
        return SecureUtil.rsa((String) null,publicKey).decryptStrFromBcd(str, KeyType.PublicKey);
    }

    public static void main(String[] args) {
        //System.out.println("私钥:" + privateKey);
        /**
         * 公钥替换 AdminConst中 userPublicKey
         */
        //System.out.println("公钥:" + publicKey);
        /**
         * 只能传入 JSONObject转String格式，具体传什么值不清楚
         * 加密的值为序列号 encryptStr 可以通过注册，总之随便填
         */
        HashMap<String,Object> params=new HashMap<>();
        params.put("principal", "qq");
        params.put("name","admin");
        params.put("uuid", "365a389b-69a7-47b3-9e23-a1e2c9ea7166");
        String encryptStr = encrypt(JSONObject.toJSONString(params));

        // String decryptStr = decrypt(encryptStr);

        System.out.println("序列号:" + encryptStr);
    }
}
