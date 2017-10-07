package ScratchPadPackage;


import UtilityClasses.CryptoPackage.EncryptClass;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Decode {
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        EncryptClass instance = new EncryptClass(2048,256,90000);
        String k = instance.hashPassword("password");
        byte[] bt = instance.getMC();
        String fromDb = instance.getMMC();
        boolean t = instance.chkPass("password",fromDb,bt);
        System.out.println(k);
        System.out.println(Arrays.toString(bt));
        System.out.println(t);
    }
}

