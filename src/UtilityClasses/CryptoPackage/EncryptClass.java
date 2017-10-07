package UtilityClasses.CryptoPackage;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class EncryptClass {
    private static final String ALGO = "PBKDF2WithHmacSHA512";
    private SecureRandom secureRandom;
    private int saltSize;
    private int hashBites;
    private int iterations;
    private byte[] MC;
    private String MMC;


    private byte[] getSalt(){
        this.secureRandom = new SecureRandom();
        byte[] salt = new byte[this.saltSize];
        this.secureRandom.nextBytes(salt);
        this.MC = salt;
        return salt;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        char[] hash = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(hash,salt,this.iterations,this.hashBites);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);
        byte[] hsh = keyFactory.generateSecret(pbeKeySpec).getEncoded();
        String temp = new String(hsh);
        this.MMC = temp;
        return temp;
    }

    public boolean chkPass(String userInput, String fromDb, byte[] namak) throws NoSuchAlgorithmException, InvalidKeySpecException{
        char[] hash1 = userInput.toCharArray();

        PBEKeySpec pbeKeySpec1 = new PBEKeySpec(hash1, namak, iterations, hashBites);
        SecretKeyFactory keyFactory1 = SecretKeyFactory.getInstance(ALGO);
        byte[] cake1 = keyFactory1.generateSecret(pbeKeySpec1).getEncoded();

        String comp1 = new String(cake1);
        return comp1.equals(fromDb);
    }

    public EncryptClass(int hashBites, int saltSize, int iterations) {
        this.saltSize = saltSize;
        this.iterations = iterations;
        this.hashBites = hashBites;
    }

    public EncryptClass(int hashBites, int iterations) {
        this.hashBites = hashBites;
        this.iterations = iterations;
    }

    public void setByteSize(int byteSize) {
        this.saltSize = byteSize;
    }

    public void setIterations(int iterations){
        this.iterations = iterations;
    }

    public void setHashBites(int hashBites) {
        this.hashBites = hashBites;
    }

    public byte[] getMC(){
        return this.MC;
    }

    public String getMMC() {
        return MMC;
    }

}
