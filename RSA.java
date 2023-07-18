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
    private final static BigInteger defaultLowerBound = new BigInteger("1");
    private final static BigInteger defaultUpperBound = new BigInteger("1000000");
    
    public RSA(){
        p = primeGenerator(defaultLowerBound, defaultUpperBound); //Default range from 1 to a billion includsive.
        q = equivalenceGenerate(p, defaultLowerBound, defaultUpperBound);
        Intialising();
    }
    
    public RSA(BigInteger start, BigInteger end){
        //Generate Primes First
        p = primeGenerator(start, end);
        q = equivalenceGenerate(p, start, end);
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
    
    private static BigInteger equivalenceGenerate(BigInteger p, BigInteger start, BigInteger end){
        BigInteger q = BigInteger.ZERO;
        int i;
        for(i = 0; i < 100; i++){
            q = bigIntegerGenerator(start, end);
            if(!(q.equals(p))){
                break;
            }
        }
        if(i == 100){
            throw new IllegalArgumentException("Could not find two difference prime in the range.");
        }
        return q;
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
     private static BigInteger inverse( BigInteger a, BigInteger n )
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
    private static BigInteger primeGenerator(BigInteger start, BigInteger end){
        if(start.compareTo(BigInteger.ZERO) <= 0 && end.compareTo(BigInteger.ZERO) <= 0){
            throw new IllegalArgumentException("Both start and end must be positive integers.");
        }
        if(!(start.compareTo(end) == -1)){
            throw new IllegalArgumentException("Please ensure start must be smaller than end.");
        }
        BigInteger prime = bigIntegerGenerator(start, end.add(BigInteger.ONE));
        BigInteger counter = new BigInteger("0");
        boolean isPrime = false;
        while(!isPrime){//while is prime is false.
            if(prime.compareTo(BigInteger.ONE) <= 0){//not prime
                prime = bigIntegerGenerator(start, end.add(BigInteger.ONE));
                counter = counter.add(BigInteger.ONE);
                continue;
            }
            else{
                isPrime = true;
                for(BigInteger i = new BigInteger("2"); i.compareTo(prime.divide(BigInteger.TWO)) <= 0; i = i.add(BigInteger.ONE)){
                    if(prime.mod(i).equals(BigInteger.ZERO)){
                        prime = bigIntegerGenerator(start, end.add(BigInteger.ONE));
                        counter = counter.add(BigInteger.ONE);
                        isPrime = false;
                        break;
                    }
                }
                //Is not prime when the code path to here.
                if(counter.compareTo(end.subtract(start).multiply(BigInteger.TEN)) == 1){
                    throw new IllegalArgumentException("Prime cannot be found between" + start + " and " + end);
                }
                
            }
        }
        //After the while loop the number generated should be an prime number.
        return prime;
    }
    
    private static BigInteger bigIntegerGenerator(BigInteger start, BigInteger end){
        BigInteger difference = end.subtract(start);
        BigInteger newBI = new BigInteger(end.bitLength(), new Random());
        BigInteger reminder = newBI.mod(difference);
        BigInteger temp = start.add(reminder);
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
