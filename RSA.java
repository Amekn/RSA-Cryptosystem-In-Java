import java.math.BigInteger;
import java.util.Random;
public class RSA{
    
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger N_prime;
    private BigInteger e;
    private BigInteger d;
    private static BigInteger x;
    private static BigInteger y;
    
    public RSA(int start, int end){
        //Generate Primes First
        p = primeGenerator(start, end);
        q = primeGenerator(start, end);
        Intialising();
    }
    
    public BigInteger encryption(BigInteger M){
        BigInteger R = M.modPow(e, N);
        return R;
    }
    
    public BigInteger decryption(BigInteger R){
        BigInteger M = R.modPow(d, N);
        return M;
    }
    
    
    public void Intialising(){
        //First is to compute N;
        N = p.multiply(q);//N  = p * q;
        
        //Next is to compute N_prime
        N_prime = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        //Next is to compute e.
        boolean eFound = false;
        for(e = BigInteger.TWO; e.compareTo(N_prime.divide(BigInteger.TWO)) <= 0; e = e.add(BigInteger.ONE)){
            if(gcd(e, N_prime).equals(BigInteger.ONE)){
                eFound = true;
                break;
            }
        }
        if(eFound == false) throw new IllegalArgumentException("e cannot be determined from the specific p and q.");
        
        //Next is compute d;
        d = inverse(e, N_prime);
        //Print results;
        System.out.print(this);
    }
    
    private static void fullGcd( BigInteger a, BigInteger b )
    {
         BigInteger x1, y1;
         if( b.equals(BigInteger.ZERO))
         {
             x = BigInteger.ONE;
             y = BigInteger.ZERO;
         }
         else
         {
             fullGcd( b, a.mod(b));
             x1 = x; y1 = y;
             x = y1;
             y = x1.subtract((a.divide(b)).multiply(y1));
         }
     }
    
     /**
     * Solve ax == 1 (mod n), assuming gcd( a, n ) = 1.
     * @return x.
     */
     public static BigInteger inverse( BigInteger a, BigInteger n )
     {
         fullGcd( a, n );
         //compareTo??
         return (x.compareTo(BigInteger.ZERO) == 1) ? x : x.add(n);
     }
    
    /**
     * gcd(BigInteger a, BigInteger b)
     *
     * Finding the the greatest common divisor between two BigInteger.
     */
    private static BigInteger gcd(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)){
            return a;
        }
        else{
            return gcd(b, a.mod(b));
        }
    }
    
    /**
     * primeGenerator
     *
     * Generate 1 prime number in the range start to end.
     * Both start and end are  inclusive, and both must be positive integers
     * @param start, the lower limit of the range.
     * @param end, the upper limit of the range.
     */
    private static BigInteger primeGenerator(int start, int end){
        if(start <= 0 && end <= 0){
            throw new IllegalArgumentException("Both start and end must be positive integers.");
        }
        Random r1 = new Random();
        int prime = r1.nextInt(start, end+1);//Both start and end must be inclusive.
        int counter = 0;
        boolean isPrime = false;
        while(!isPrime){//while is prime is false.
            if(prime <= 1){//not prime
                prime = r1.nextInt(start, end+1);
                counter++;
                continue;
            }
            else{
                isPrime = true;
                for(int i = 2; i <= prime/2; i++){
                    if((prime % i) == 0){
                        prime = r1.nextInt(start, end+1);
                        counter++;
                        isPrime = false;
                        break;
                    }
                }
                if(isPrime == true){
                    break;
                }
                //Is not prime when the code path to here.
                if(counter*5 >= (end-start)){
                    throw new IllegalArgumentException("Prime cannot be found between" + start + " and " + end);
                }
            }
        }
        //After the while loop the number generated should be an prime number.
        BigInteger temp = new BigInteger(Integer.toString(prime));
        return temp;
    }
    
    @Override public String toString(){
        String text = "";
        text += "p: " + p + "\n";
        text += "q: " + q + "\n";
        text += "N: " + N + "\n";
        text += "N\': " + N_prime + "\n";
        text += "e: " + e + "\n";
        text += "d: " + d + "\n";
        return text;
    }
}
