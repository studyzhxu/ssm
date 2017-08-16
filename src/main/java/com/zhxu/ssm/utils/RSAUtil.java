package com.zhxu.ssm.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密工具
 *
 * @author xusheng
 * @create 2017-07-20 15:45
 **/
public class RSAUtil {

    private static final Logger log = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 下面这行指定了RSA算法的细节，必须更python对应
     */
    private static String RSA_CONFIGURATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /**
     * <B>功能简述</B><br>
     * 通过Pem格式的字符串（PKCS1）生成公钥，base64是去掉头和尾的b64编码的字符串 Pem格式公钥一般采用PKCS1格式
     *
     * @param publicKeyBase64Str
     * @return
     * @date 2017年7月20日 下午3:47:23
     * @author xusheng
     */
    public static PublicKey generatePublicKeyFromPKCS1(String publicKeyBase64Str) {
        byte[] publicKeyBytes;
        try {
            publicKeyBytes = Base64.decodeBase64(publicKeyBase64Str.getBytes("UTF-8"));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec ks = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = kf.generatePublic(ks);
            return publicKey;
        } catch (UnsupportedEncodingException e) {
            log.error("generate public key from pkcs1 unsupportedencoding, exception : ", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("generate public key from pkcs1 nosuchalgorithm, exception : ", e);
        } catch (InvalidKeySpecException e) {
            log.error("generate public key from pkcs1 invalidkeyspec, exception : ", e);
        }
        return null;
    }

    /**
     * <B>功能简述</B><br>
     * 加密 Key一般是公钥
     *
     * @param publicKey
     * @param toBeEncryptedString
     * @return
     * @date 2017年7月20日 下午3:52:22
     * @author xusheng
     */
    public static String encrypt(Key publicKey, String toBeEncryptedString) {
        try {
            Cipher c = Cipher.getInstance(RSA_CONFIGURATION);
            c.init(Cipher.ENCRYPT_MODE, publicKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
            byte[] encodedBytes;
            encodedBytes = c.doFinal(toBeEncryptedString.getBytes("UTF-8"));
            return Base64.encodeBase64String(encodedBytes);
        } catch (IllegalBlockSizeException e) {
            log.error("encrypt illegalblocksize, exception : ", e);
        } catch (BadPaddingException e) {
            log.error("encrypt badpaddingexception, exception : ", e);
        } catch (UnsupportedEncodingException e) {
            log.error("encrypt unsupportedencoding, exception : ", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt nosuchalgorithm, exception : ", e);
        } catch (NoSuchPaddingException e) {
            log.error("encrypt nosuchpadding, exception : ", e);
        } catch (InvalidKeyException e) {
            log.error("encrypt invalidkey, exception : ", e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error("encrypt invalidalgorithmparameter, exception : ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String data = "abc";
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtUJvTkRYLEdX2YzONdqL\n" +
                "Jv3f1Bq+Z2S491SZMsYHrmucy/Vj5fQkWHiF0gensw8mc/v/BLOrIlCWi8H8RSP8\n" +
                "RDve53bYVfSidSJdDgry6VKxfRYADsu6IbrZEDPMgDE1zgxPX4/DH02lP8TzQqXX\n" +
                "d3jZQZDUaxNLNFeNDlNLGTiY2CJaglu4GqsqvL1TwtfNCImCpYK2ZVFCTTVfvYy1\n" +
                "mqvM+6bgAQi69h3vuQanqp97zOOBVftH9kxRQrebpvdzPosbmlTofXQ7AbhfTftw\n" +
                "3RBLsPSxF4FPYM87tTXTPw6KFW7isw3Y9d1gSiHGAH/N4Wluc+pgOWxZjZ5ITCAa\n" +
                "uwIDAQAB";
        PublicKey publicKey = generatePublicKeyFromPKCS1(publicKeyStr);
        System.out.println("明文：" + data);
        System.out.println("密文：" + encrypt(publicKey, data));
    }
}
