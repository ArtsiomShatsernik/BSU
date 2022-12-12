package org.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class CryptoLib {
    Cipher cipher;
    final String encryptAlgo = "AES/ECB/PKCS5Padding";

    public static void encrypt() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        SecretKeySpec keySpec = createSecretKey();

    }

    private static SecretKeySpec createSecretKey() throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException {
        SecretKeySpec sks = null;
        byte[] bytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        MessageDigest md;
        byte[] key;
        md = MessageDigest.getInstance("SHA-1");
        key = md.digest(bytes);
        key = Arrays.copyOf(key, 16);
        sks = new SecretKeySpec(key, "AES");
        KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection("1234".toCharArray());
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream keyStoreData = new FileInputStream((File) null);
        keyStore.load(keyStoreData, "1234".toCharArray());
        KeyStore.SecretKeyEntry ske = new KeyStore.SecretKeyEntry(sks);
        keyStore.setEntry("MyKey", ske, protection);
        FileOutputStream fileOut = new FileOutputStream("keystore.ks");
        keyStore.store(fileOut, "1234".toCharArray());
        return sks;
    }
}
