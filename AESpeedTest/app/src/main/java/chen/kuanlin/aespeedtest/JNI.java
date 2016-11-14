package chen.kuanlin.aespeedtest;

import android.util.Base64;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kuanlin on 2016/11/14.
 */
public class JNI implements Strategy{

    static {
        try {
            System.loadLibrary("aes_jni");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static final int ENCRYPT = 0;
    public static final int DECRYPT = 1;
    public native static byte[] crypt(byte[] data, long time, int mode);
    public native static byte[] read(String path, long time);

    private static byte[] textbyte = null;

    @Override
    public byte[] Encrypt(byte[] key, byte[] iv, byte[] plaindata){
        try{
            long start_time = System.currentTimeMillis();
            textbyte = crypt(plaindata, System.currentTimeMillis(), ENCRYPT);
            long end_time = System.currentTimeMillis();
            MainActivity.total_time = (end_time-start_time);
            return textbyte;
        }catch(Exception ex){
            return textbyte;
        }
    }

    @Override
    public byte[] Decrypt(byte[] key, byte[] iv, byte[] cipherdata){
        try{
            textbyte = crypt(cipherdata, System.currentTimeMillis(), DECRYPT);
            return textbyte;
        }catch(Exception ex){
            return textbyte;
        }
    }
}
