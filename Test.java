import java.math.BigInteger;
import java.util.Random;
public class Test{
    public static void main(String[] args){
        
        RSA r1 = new RSA();
        //Encrypting a text;
        BigInteger plaintext = new BigInteger("17892");
        BigInteger ciphertext = r1.encryption(plaintext);
        System.out.println("Encrypted text from " + plaintext + " to " + ciphertext);
        //Decryption a text;
        BigInteger decrypted = r1.decryption(ciphertext);
        System.out.println("Decrypted text from " + ciphertext + " to " + plaintext);
         
    }
}
