package chen.kuanlin.aespeedtest;
/**
 * Created by kuanlin on 2016/11/14.
 */
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class BC implements Strategy{

    private static byte[] textbyte = null;

    @Override
    public byte[] Encrypt(byte[] key, byte[] iv, byte[] plaindata){
        try{
            AlgorithmParameterSpec algorithm = new IvParameterSpec(iv);
            SecretKeySpec secretkey = new SecretKeySpec(key, "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, algorithm);

            long start_time = System.currentTimeMillis();
            textbyte = cipher.doFinal(plaindata);
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
            AlgorithmParameterSpec algorithm = new IvParameterSpec(iv);
            SecretKeySpec secretkey = new SecretKeySpec(key, "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, secretkey, algorithm);

            textbyte = cipher.doFinal(cipherdata);
            return textbyte;
        }catch(Exception ex){
            return textbyte;
        }
    }
}
