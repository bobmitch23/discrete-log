package com.discrete.service;

import com.discrete.domain.DiscreteLogMetadata;
import com.discrete.domain.PrimeFactorMultiplicity;
import com.discrete.util.DiscreteLogUtil;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class PohligHellmanAlgorithm implements DiscreteLogCalculator{
    private final DiscreteLogUtil discreteLogUtil;

    public PohligHellmanAlgorithm(DiscreteLogUtil discreteLogUtil){
        this.discreteLogUtil = discreteLogUtil;
    }

    @Override
    public DiscreteLogMetadata calculateDiscreteLog(BigInteger generator, BigInteger power, BigInteger prime) {
        long start = System.currentTimeMillis();

        List<PrimeFactorMultiplicity> primeFactorMultiplicities = discreteLogUtil.primeFactorMultiplicity(prime.subtract(BigInteger.ONE));
        BigInteger generatorInverseModPrime = discreteLogUtil.calculateExtendedEuclid(generator, prime).getCoefficient1().mod(prime);
        BigInteger primeMinus1 = prime.subtract(BigInteger.valueOf(1l));
        BigInteger powerCopy = power;

        BigInteger u = BigInteger.ZERO;
        List<BigInteger> finalList = new ArrayList<>();
        for(int i = 0; i < primeFactorMultiplicities.size(); i++){
            //x = g^((prime-1)/primeFactor) % prime
            BigInteger x = generator.modPow(primeMinus1.divide(primeFactorMultiplicities.get(i).getPrimeFactor()), prime);

            for(BigInteger multiplicity = BigInteger.ZERO; multiplicity.compareTo(primeFactorMultiplicities.get(i).getMultiplicity())<0; multiplicity = multiplicity.add(BigInteger.ONE)){
                //y = power^((prime-1)/primeFactor*(multiplicity+1)) % prime where the multiplicity variable is less than the multiplicity of the prime factor
                BigInteger y = power.modPow(primeMinus1.divide(primeFactorMultiplicities.get(i).getPrimeFactor().pow(multiplicity.add(BigInteger.ONE).intValue())), prime);
                BigInteger finalFactor = null;
                for(BigInteger factor = BigInteger.ZERO; factor.compareTo(primeFactorMultiplicities.get(i).getPrimeFactor())<0; factor = factor.add(BigInteger.ONE)){
                    if(x.modPow(factor,prime).equals(y)){
                        //u += factor*(primeFactorMultiplicities[i][0]^multiplicity)
                        u = u.add(factor.multiply(primeFactorMultiplicities.get(i).getPrimeFactor().pow(multiplicity.intValue())));
                        finalFactor = factor;
                        break;
                    }
                    finalFactor = factor;
                }
                //power = power * (generatorInverseModPrime^((primeFactorMultiplicities[i][0]^multiplicity)*finalFactor)%prime)%prime
                power = power.multiply(generatorInverseModPrime.modPow(primeFactorMultiplicities.get(i).getPrimeFactor().pow(multiplicity.intValue()).multiply(finalFactor),prime)).mod(prime);
            }
            finalList.add(u);
            u = BigInteger.ZERO;
            power = powerCopy;
        }
        List<BigInteger> factors = new ArrayList<>();
        for (int i = 0; i <primeFactorMultiplicities.size(); i++){
            factors.add(primeFactorMultiplicities.get(i).getPrimeFactor().pow(primeFactorMultiplicities.get(i).getMultiplicity().intValue()));
        }

        BigInteger finalNum = discreteLogUtil.chineseRemainder(finalList.toArray(new BigInteger[0]), factors.toArray(new BigInteger[0])).mod(prime);

        String name = "Pohlig-Hellman Algorithm";
        double numSecondsTaken = (System.currentTimeMillis() - (double) start)/1000;
        boolean solutionFound = false;
        BigInteger solution = null;
        if(discreteLogUtil.verify(generator, power, prime, finalNum)){
            solutionFound = true;
            solution = finalNum;
        }
        return DiscreteLogMetadata.builder().algorithmName(name).numberOfSecondsTaken(numSecondsTaken).solution(solution).solutionFound(solutionFound).build();
    }
}
