package com.discrete.service;

import com.discrete.domain.DiscreteLogMetadata;
import com.discrete.util.DiscreteLogUtil;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Component
public class BabyGiantAlgorithm implements DiscreteLogCalculator{
    private final DiscreteLogUtil discreteLogUtil;

    public BabyGiantAlgorithm(DiscreteLogUtil discreteLogUtil){
        this.discreteLogUtil = discreteLogUtil;
    }

    @Override
    public DiscreteLogMetadata calculateDiscreteLog(BigInteger generator, BigInteger base, BigInteger prime) {
        long start = System.currentTimeMillis();

        //m is the ceiling of sqrt(prime-1).  BigInteger sqrt always rounds down
        BigInteger m = prime.subtract(BigInteger.ONE).sqrt();
        if(!m.multiply(m).equals(prime.subtract(BigInteger.ONE))){
            m.add(BigInteger.ONE);
        }
        Map<BigInteger, BigInteger> dictionary = new HashMap<>();

        for(BigInteger bi = BigInteger.ZERO; bi.compareTo(m) < 0; bi = bi.add(BigInteger.ONE)){
            dictionary.put(generator.modPow(bi, prime), bi);
        }

        BigInteger inverseModPrime = discreteLogUtil.calculateExtendedEuclid(generator, prime).getCoefficient1().mod(prime);
        BigInteger a = inverseModPrime.modPow(m, prime);
        BigInteger q = base;

        for(BigInteger bi = BigInteger.ONE; bi.compareTo(m) < 0; bi = bi.add(BigInteger.ONE)){
            //q = (q*a) % prime
            q = q.multiply(a).mod(prime);

            //Check if we have a collision
            if(dictionary.containsKey(q)){
                //finalNum = (bi*max + dictionary[q]) % prime
                BigInteger finalNum = bi.multiply(m).add(dictionary.get(q)).mod(prime);
                if(discreteLogUtil.verify(generator, base, prime, finalNum)){
                    return DiscreteLogMetadata.builder()
                            .algorithmName("Baby Giant Algorithm")
                            .solutionFound(true)
                            .solution(finalNum)
                            .numberOfSecondsTaken((System.currentTimeMillis() - (double) start)/1000).build();
                }
            }
        }

        return DiscreteLogMetadata.builder()
                .algorithmName("Baby Giant Algorithm")
                .solutionFound(false)
                .solution(null)
                .numberOfSecondsTaken((System.currentTimeMillis() - (double) start)/1000).build();

    }
}
