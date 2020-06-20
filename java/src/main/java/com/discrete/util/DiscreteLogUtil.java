package com.discrete.util;

import com.discrete.domain.ExtendedEuclid;
import com.discrete.domain.PrimeFactorMultiplicity;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiscreteLogUtil {

    /**
     * Find gcd(num1, num2) = coefficient1(num1) + coefficient2(num2)
     */
    public ExtendedEuclid calculateExtendedEuclid(BigInteger num1, BigInteger num2){
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger aa = BigInteger.ONE;
        BigInteger bb = BigInteger.ZERO;

        while (num2.compareTo(BigInteger.ZERO) != 0){
            BigInteger quotient = num1.divide(num2);
            BigInteger remainder = num1.mod(num2);
            BigInteger tempA = a;
            BigInteger tempB = b;
            a = aa.subtract(quotient.multiply(a));
            b = bb.subtract(quotient.multiply(b));
            aa = tempA;
            bb = tempB;
            num1 = num2;
            num2 = remainder;
        }
        return ExtendedEuclid.builder().greatestCommonDivisor(num1).coefficient1(aa).coefficient2(bb).build();
    }

    /**
     * Verify that we have solved the discrete log problem
     */
    public boolean verify(BigInteger generator, BigInteger power, BigInteger prime, BigInteger finalNum){
        return generator.modPow(finalNum, prime).equals(power);
    }

    /**
     * Given a number return a list of its prime factors and their multiplicity.  Ex 500 comes to [[2,2], [5,3]]
     */
    public List<PrimeFactorMultiplicity> primeFactorMultiplicity(BigInteger num){
        List<BigInteger> primeFactors = findPrimeFactors(num);
        List<PrimeFactorMultiplicity> primeFactorMultiplicities = new ArrayList<>();
        if(primeFactors.size()==0) return primeFactorMultiplicities;
        BigInteger current = null;
        BigInteger n = BigInteger.ZERO;
        for(BigInteger e : primeFactors){
            if(e.equals(current)){
                n = n.add(BigInteger.ONE);
            }else{
                if(n.compareTo(BigInteger.ZERO) > 0){
                    primeFactorMultiplicities.add(PrimeFactorMultiplicity.builder().primeFactor(current).multiplicity(n).build());
                }
                n = BigInteger.ONE;
                current = e;
            }
        }
        primeFactorMultiplicities.add(PrimeFactorMultiplicity.builder().primeFactor(current).multiplicity(n).build());
        return primeFactorMultiplicities;
    }

    private List<BigInteger> findPrimeFactors(BigInteger n){
        if (n.compareTo(BigInteger.ONE) <= 0) return new ArrayList<>();
        List<BigInteger> primeFactors = new ArrayList<>();
        BigInteger d = BigInteger.valueOf(2l);
        //while n>= d^2
        while (n.compareTo(d.multiply(d))>=0){
            if(n.mod(d).compareTo(BigInteger.ZERO) == 0){
                primeFactors.add(d);
                n = n.divide(d);
            }else{
                BigInteger mod2 = d.mod(BigInteger.valueOf(2l));
                d = d.add(BigInteger.ONE).add(mod2);
            }
        }
        primeFactors.add(n);
        return primeFactors;
    }

    /**
     * Chinese remainder theorem states that if one knows the remainders of the Euclidean division of an integer n by several integers,
     * then one can determine uniquely the remainder of the division of n by the product of these integers,
     * under the condition that the divisors are pairwise coprime.
     */
    public BigInteger chineseRemainder(BigInteger[] z, BigInteger[] factors){
        List<BigInteger[]> pairs = new ArrayList<>();
        for(int i = 0; i < z.length; i++ ){
            BigInteger[] pair = {z[i], factors[i]};
            pairs.add(pair);
        }

        BigInteger a = pairs.get(0)[0];
        BigInteger m = pairs.get(0)[1];
        for(int j = 1; j < pairs.size(); j ++){
            BigInteger b = pairs.get(j)[0];
            BigInteger p = pairs.get(j)[1];

            BigInteger k = b.subtract(a).multiply(calculateExtendedEuclid(m,p).getCoefficient1()).mod(p);
            a = m.multiply(k).add(a).mod(m.multiply(p));
            m = m.multiply(p);
        }

        return a;
    }
}
