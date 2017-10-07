package ScratchPadPackage;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
public class Hello{
    public static String salty;
    public static String passay;
    public static byte[] cole;
    private static byte[] getSalt() throws NoSuchAlgorithmException, InvalidKeySpecException{
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[256];
        secureRandom.nextBytes(salt);
        cole = salt;
        return salt;
    }
    public static String hashedPass(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        char[] hash = password.toCharArray();
        byte[] salt = getSalt();
        salty = new String(salt);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(hash,salt,8000,2048);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hsh = keyFactory.generateSecret(pbeKeySpec).getEncoded();
        String temp = new String(hsh);
        passay = temp;
        return temp;
    }
    public static boolean decode(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] hash = password.toCharArray();
        byte[] tempByt = salty.getBytes();
        PBEKeySpec pbeKeySpec = new PBEKeySpec(hash,cole,8000,2048);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hsh = keyFactory.generateSecret(pbeKeySpec).getEncoded();
        String ttt = new String(hsh);
        if(ttt.equals(passay))return true;
        return false;
    }

    public static void main(String []args)throws NoSuchAlgorithmException, InvalidKeySpecException{
        /*String k = "password";
        String l = Hello.hashedPass(k);
        System.out.println(Hello.salty);
        System.out.println(l);
        System.out.println(Hello.decode("password"));*/
        //System.out.println(1000*1000);
        //System.out.println((-7)%3);
    }
}