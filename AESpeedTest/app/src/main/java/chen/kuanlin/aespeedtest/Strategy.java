package chen.kuanlin.aespeedtest;
/**
 * Created by kuanlin on 2016/11/14.
 */
public interface Strategy {
    public byte[] Encrypt(byte[] key, byte[] iv, byte[] plaindata);
    public byte[] Decrypt(byte[] key, byte[] iv, byte[] cipherdata);
}
