import java.math.BigInteger;
import java.util.Random;
public class Test{
    public static void main(String[] args){
        BigInteger b1 = new BigInteger("100");
        BigInteger b2 = new BigInteger("2000");
        RSA r1 = new RSA(b1,b2);
        //Encrypting a text;
        BigInteger plaintext = new BigInteger("17892");
        BigInteger ciphertext = r1.encryption(plaintext);
        System.out.println("Encrypted text from " + plaintext + " to " + ciphertext);
        //Decryption a text;
        BigInteger decrypted = r1.decryption(ciphertext);
        System.out.println("Decrypted text from " + ciphertext + " to " + plaintext);
         
    }
}
