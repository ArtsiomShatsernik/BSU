package org.example;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class CryptoLib {
    final private static String encryptAlgo = "DES/ECB/PKCS5Padding";

    public static void encrypt(String fileName, String privateKey) throws GeneralSecurityException, IOException {
        DESKeySpec dks = new DESKeySpec(privateKey.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(encryptAlgo);
        cipher.init(Cipher.ENCRYPT_MODE, desKey);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileName);
            fos = new FileOutputStream("encrypted_" + fileName);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            doCopy(cis, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    public static void decrypt(String fileName, String privateKey) throws GeneralSecurityException, IOException {
        DESKeySpec dks = new DESKeySpec(privateKey.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fileName);
            fos = new FileOutputStream("decrypted"+ fileName.substring(fileName.lastIndexOf("_")));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            doCopy(fis, cos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
    private static SecretKey createSecretKey(String secret) throws InvalidKeyException {
        DESKeySpec dks = new DESKeySpec(secret.getBytes());
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKey desKey;
        try {
            desKey = skf.generateSecret(dks);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return desKey;
    }
    private static void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }
}
