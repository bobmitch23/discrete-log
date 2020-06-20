package com.discrete.service;

import com.discrete.domain.DiscreteLogMetadata;
import com.discrete.util.DiscreteLogUtil;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class RhoAlgorithm implements DiscreteLogCalculator{
    private final DiscreteLogUtil discreteLogUtil;

    public RhoAlgorithm(DiscreteLogUtil discreteLogUtil){
        this.discreteLogUtil = discreteLogUtil;
    }

    @Override
    public DiscreteLogMetadata calculateDiscreteLog(BigInteger generator, BigInteger base, BigInteger prime) {
        long start = System.currentTimeMillis();
        //subGroup = (prime - 1)/2
        BigInteger subGroup = prime.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2l));

        BigInteger X, x;
        X = x = generator.multiply(base);
        BigInteger A, a, B, b;
        A = a = B = b = BigInteger.ONE;

        // move x and X at different 'speeds' (x once and X twice) until a collision occurs
        for(int i = 1; i < prime.longValue() - 1; i++){
            BigInteger [] movex = newXAB (x, a, b, generator, base, prime, subGroup);
            x = movex[0];
            a = movex[1];
            b = movex[2];

            BigInteger [] moveX1 = newXAB (X, A, B, generator, base, prime, subGroup);
            BigInteger [] moveX2 = newXAB (moveX1[0], moveX1[1], moveX1[2], generator, base, prime, subGroup);
            X = moveX2[0];
            A = moveX2[1];
            B = moveX2[2];

            //collision occurred
            if (x.equals(X)) break;
        }
        //finalNum = (inverse(B-b, subGroup) * (a-A)) % subGroup
        BigInteger finalNum = discreteLogUtil.calculateExtendedEuclid(B.subtract(b), subGroup).getCoefficient1().multiply(a.subtract(A)).mod(subGroup);

        String name = "Rho Algorithm";
        double numSecondsTaken = (System.currentTimeMillis() - (double) start)/1000;
        BigInteger solution = null;
        boolean solutionFound = false;
        if(discreteLogUtil.verify(generator, base, prime, finalNum)){
            solution = finalNum;
            solutionFound = true;
        }
        else if (discreteLogUtil.verify(generator, base, prime, finalNum.add(subGroup))){
            solution = finalNum;
            solutionFound = true;
        }
        return DiscreteLogMetadata.builder().solutionFound(solutionFound).algorithmName(name).solution(solution).numberOfSecondsTaken(numSecondsTaken).build();
    }

    //Cyclical group is divided into three disjoint subsets and within each subset a new value of x, a and b is calculated
    private BigInteger[] newXAB (BigInteger x, BigInteger a, BigInteger b, BigInteger gen, BigInteger power, BigInteger prime, BigInteger sub){
        if(x.mod(BigInteger.valueOf(3l)).compareTo(BigInteger.ZERO) == 0){
            x = x.multiply(gen).mod(prime);
            a = a.add(BigInteger.ONE).mod(sub);
        }else if(x.mod(BigInteger.valueOf(3l)).compareTo(BigInteger.ONE) == 0){
            x = x.multiply(power).mod(prime);
            b = b.add(BigInteger.ONE).mod(sub);
        }else{
            x = x.multiply(x).mod(prime);
            a = a.multiply(BigInteger.valueOf(2l)).mod(sub);
            b = b.multiply(BigInteger.valueOf(2l)).mod(sub);
        }
        BigInteger[] newXAB = {x,a,b};
        return newXAB;
    }
}
