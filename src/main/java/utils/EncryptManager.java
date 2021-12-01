package utils;

import org.apache.commons.codec.binary.Base64;

public abstract class EncryptManager {
        
    public static String encrypt(String text) {
        return Base64.encodeBase64String(text.getBytes());
    }
    
    public static String decrypt(String text) {
       byte[] decoded = Base64.decodeBase64(text.getBytes());
       return new String(decoded);
    }
    
}
