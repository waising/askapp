package com.asking91.app.ui.juniorhigh;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * Created by jswang on 2017/6/9.
 */

public class DESHelper {
    //秘钥算法
    private static final String KEY_ALGORITHM = "DES";
    //加密算法：algorithm/mode/padding 算法/工作模式/填充模式
    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    private static final byte[] KEY = {56, 57, 58, 59, 60, 61, 62, 63};//DES 秘钥长度必须是8 位或以上

    /**
     * 文件进行加密并保存加密后的文件到指定目录
     */
    public static void encrypt(InputStream is, OutputStream out) {
        SecretKey secretKey = new SecretKeySpec(KEY, KEY_ALGORITHM);
        CipherInputStream cis = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cis != null) {
                    cis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * AES方式解密文件
     */
    public static byte[] decryptFile(String sourceFilePath) {
        FileInputStream in = null;
        File sourceFile = null;
        try {
            sourceFile = new File(sourceFilePath);
            long fileSize = sourceFile.length();
            if (fileSize > Integer.MAX_VALUE) {
                return null;
            }
            in = new FileInputStream(sourceFile);
            byte[] buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = in.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            SecretKey secretKey = new SecretKeySpec(KEY, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件进行解密并保存解密后的文件到指定目录
     *
     * @param fromFilePath 已加密的文件 如c:/加密后文件.txt
     * @param toFilePath   解密后存放的文件 如c:/ test/解密后文件.txt
     */
    public static void decrypt(String fromFilePath, String toFilePath) {
        File fromFile = new File(fromFilePath);
        if (!fromFile.exists()) {
            return;
        }
        File toFile = getFile(toFilePath);

        SecretKey secretKey = new SecretKeySpec(KEY, KEY_ALGORITHM);

        InputStream is = null;
        OutputStream out = null;
        CipherOutputStream cos = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            is = new FileInputStream(fromFile);
            out = new FileOutputStream(toFile);
            cos = new CipherOutputStream(out, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) >= 0) {
                cos.write(buffer, 0, r);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (cos != null) {
                    cos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件进行加密并保存加密后的文件到指定目录
     */
    public static void encrypt(String fromFilePath, String toFilePath) {

        File fromFile = new File(fromFilePath);
        if (!fromFile.exists()) {
            return;
        }
        File toFile = getFile(toFilePath);

        SecretKey secretKey = new SecretKeySpec(KEY, KEY_ALGORITHM);
        InputStream is = null;
        OutputStream out = null;
        CipherInputStream cis = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            is = new FileInputStream(fromFile);
            out = new FileOutputStream(toFile);
            cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cis != null) {
                    cis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
