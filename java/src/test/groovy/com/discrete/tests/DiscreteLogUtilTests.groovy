package com.discrete.tests

import com.discrete.domain.ExtendedEuclid
import com.discrete.domain.PrimeFactorMultiplicity
import com.discrete.util.DiscreteLogUtil
import spock.lang.Shared
import spock.lang.Specification

class DiscreteLogUtilTests extends Specification{

    @Shared
    private DiscreteLogUtil discreteLogUtil;

    def setupSpec(){
        discreteLogUtil = new DiscreteLogUtil()
    }

    def "Test extended euclid with small numbers"(){
        when: "3 and 2 are sent to the extended euclid algorithm"
        ExtendedEuclid extendedEuclid = discreteLogUtil.calculateExtendedEuclid(BigInteger.valueOf(9l), BigInteger.valueOf(3l))

        then: "3 is the gcd, 0 is the first coefficient and 1 is the second coefficient"
        assert extendedEuclid.greatestCommonDivisor.equals(BigInteger.valueOf(3l))
        assert extendedEuclid.coefficient1.equals(BigInteger.valueOf(0l))
        assert extendedEuclid.coefficient2.equals(BigInteger.valueOf(1l))
    }

    def "Test extended euclid with medium numbers"(){
        when: "43124 and 15984 are sent to the extended euclid algorithm"
        ExtendedEuclid extendedEuclid = discreteLogUtil.calculateExtendedEuclid(BigInteger.valueOf(43124l), BigInteger.valueOf((15984l)))

        then: "4 is the gcd, 341 is the first coefficient and -920 is the second coefficient"
        assert extendedEuclid.greatestCommonDivisor.equals(BigInteger.valueOf(4l))
        assert extendedEuclid.coefficient1.equals(BigInteger.valueOf(341l))
        assert extendedEuclid.coefficient2.equals(BigInteger.valueOf(-920l))
    }

    def "Test extended euclid with large numbers"(){
        when: "15673827453612 and 42222222 are sent to the extended euclid algorithm"
        ExtendedEuclid extendedEuclid = discreteLogUtil.calculateExtendedEuclid(BigInteger.valueOf(15673827453612l), BigInteger.valueOf(42222222l))

        then: "6 is the gcd, 333418 is the first coefficient and -123772173855 is the second coefficient"
        assert extendedEuclid.greatestCommonDivisor.equals(BigInteger.valueOf(6l))
        assert extendedEuclid.coefficient1.equals(BigInteger.valueOf(333418l))
        assert extendedEuclid.coefficient2.equals(BigInteger.valueOf(-123772173855l))
    }

    def "Test verify 1"(){
        when: "variables are setup"
        boolean validation = discreteLogUtil.verify(BigInteger.valueOf(2l), BigInteger.valueOf(3l), BigInteger.valueOf(5l), BigInteger.valueOf(7l))

        then: "2^7 mod 5 == 3"
        assert validation
    }

    def "Test verify 2"(){
        when: "variables are setup"
        boolean validation = discreteLogUtil.verify(BigInteger.valueOf(2l), BigInteger.valueOf(3220595l), BigInteger.valueOf(4608347l), BigInteger.valueOf(777840l))

        then: "2^777840 mod 4608347 == 3220595"
        assert validation
    }

    def "Test factor multiplicity with a small number"(){
        when: "5 is sent to the factor multiplicity function"
        List<PrimeFactorMultiplicity> primeFactorMultiplicities = discreteLogUtil.primeFactorMultiplicity(BigInteger.valueOf(5l));

        then: "the factor multiplicity is (5,1)"
        assert primeFactorMultiplicities.get(0).primeFactor.equals(BigInteger.valueOf(5l))
        assert primeFactorMultiplicities.get(0).multiplicity.equals(BigInteger.valueOf(1l))
    }

    def "Test factor multiplicity with a large number"(){
        when: "500 is sent to the factor multiplicity function"
        List<PrimeFactorMultiplicity> primeFactorMultiplicities = discreteLogUtil.primeFactorMultiplicity(BigInteger.valueOf(500l));

        then: "the factor multiplicity is [(2, 2), (5, 3)]"
        assert primeFactorMultiplicities.get(0).getPrimeFactor().equals(BigInteger.valueOf(2l))
        assert primeFactorMultiplicities.get(0).getMultiplicity().equals(BigInteger.valueOf(2l))
        assert primeFactorMultiplicities.get(1).getPrimeFactor().equals(BigInteger.valueOf(5l))
        assert primeFactorMultiplicities.get(1).getMultiplicity().equals(BigInteger.valueOf(3l))
    }

    def "Test factor multiplicity with the number 1"(){
        when: "1 is sent to the factor multiplicity function"
        List<PrimeFactorMultiplicity> primeFactorMultiplicities = discreteLogUtil.primeFactorMultiplicity(BigInteger.ONE);

        then: "the factor multiplicity is []"
        assert primeFactorMultiplicities.size() == 0
    }

    def "Test factor multiplicity with the number 0"(){
        when: "0 is sent to the factor multiplicity function"
        List<PrimeFactorMultiplicity> primeFactorMultiplicities  = discreteLogUtil.primeFactorMultiplicity(BigInteger.ZERO);

        then: "the factor multiplicity is []"
        assert primeFactorMultiplicities.size() == 0
    }

    def "Test chinese remainder 1"(){
        when: "[1,2] and [3,4] are sent to the chineseRemainder function"
        BigInteger[] z = [BigInteger.ONE, BigInteger.valueOf(2l)];
        BigInteger[] factors = [BigInteger.valueOf(3l), BigInteger.valueOf(4l)];
        BigInteger chineseRemainder = discreteLogUtil.chineseRemainder(z, factors);

        then: "the chinese remainder is 10"
        assert chineseRemainder.equals(BigInteger.valueOf(10l));
    }

    def "Test chinese remainder 2"(){
        when: "[0, 709, 753], [2, 1361, 1693] are sent to the chineseRemainder function"
        BigInteger[] z = [BigInteger.valueOf(0l), BigInteger.valueOf(709l), BigInteger.valueOf(753l)];
        BigInteger[] factors = [BigInteger.valueOf(2l), BigInteger.valueOf(1361l), BigInteger.valueOf(1693l)];
        BigInteger chineseRemainder = discreteLogUtil.chineseRemainder(z, factors);

        then: "the chinese remainder is 777840"
        assert chineseRemainder.equals(BigInteger.valueOf(777840l));
    }
}
