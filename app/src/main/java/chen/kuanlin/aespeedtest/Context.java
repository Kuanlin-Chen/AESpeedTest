package chen.kuanlin.aespeedtest;
/**
 * Created by kuanlin on 2016/11/14.
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }
    public byte[] executeEncrypt(byte[] key, byte[] iv, byte[] plaindata){
        return strategy.Encrypt(key, iv, plaindata);
    }
    public byte[] executeDecrypt(byte[] key, byte[] iv, byte[] cipherdata){
        return strategy.Decrypt(key, iv, cipherdata);
    }
}
