package com.discrete.tests

import com.discrete.domain.DiscreteLogMetadata
import com.discrete.service.BabyGiantAlgorithm
import com.discrete.service.PohligHellmanAlgorithm
import com.discrete.service.RhoAlgorithm
import com.discrete.util.DiscreteLogUtil
import spock.lang.Shared
import spock.lang.Specification

class AlgorithmTests extends Specification{

    @Shared
    private DiscreteLogUtil discreteLogUtil;

    def setupSpec(){
        discreteLogUtil = new DiscreteLogUtil()
    }

    def "Test Rho Algorithm"(){
        RhoAlgorithm rhoAlgorithm = new RhoAlgorithm(discreteLogUtil)
        given: "we have 2^x mod 4608347 = 3220595"
        when: "we try and solve the discrete log problem with the rho algorithm"
        DiscreteLogMetadata metadata = rhoAlgorithm.calculateDiscreteLog(BigInteger.valueOf(2l), BigInteger.valueOf(3220595l), BigInteger.valueOf(4608347l))

        then: "x = 777840"
        metadata.solutionFound
        metadata.solution.equals(BigInteger.valueOf(777840l))
        metadata.algorithmName.equals("Rho Algorithm")
        metadata.numberOfSecondsTaken > 0
    }

    def "Test Baby Giant Algorithm"(){
        BabyGiantAlgorithm babyGiantAlgorithm = new BabyGiantAlgorithm(discreteLogUtil)
        given: "we have 2^x mod 4608347 = 3220595"
        when: "we try and solve the discrete log problem with the baby giant algorithm"
        DiscreteLogMetadata metadata = babyGiantAlgorithm.calculateDiscreteLog(BigInteger.valueOf(2l), BigInteger.valueOf(3220595l), BigInteger.valueOf(4608347l))

        then: "x = 777840"
        metadata.solutionFound
        metadata.solution.equals(BigInteger.valueOf(777840l))
        metadata.algorithmName.equals("Baby Giant Algorithm")
        metadata.numberOfSecondsTaken > 0
    }

    def "Test Pohlig-Hellman Algorithm"(){
        PohligHellmanAlgorithm pohligHellmanAlgorithm = new PohligHellmanAlgorithm(discreteLogUtil)
        given: "we have 2^x mod 4608347 = 3220595"
        when: "we try and solve the discrete log problem with the pohlig hellman algorithm"
        DiscreteLogMetadata metadata = pohligHellmanAlgorithm.calculateDiscreteLog(BigInteger.valueOf(2l), BigInteger.valueOf(3220595l), BigInteger.valueOf(4608347l))

        then: "x = 777840"
        metadata.solutionFound
        metadata.solution.equals(BigInteger.valueOf(777840l))
        metadata.algorithmName.equals("Pohlig-Hellman Algorithm")
        metadata.numberOfSecondsTaken > 0
    }

}
