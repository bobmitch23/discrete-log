package com.discrete.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Builder
public class DiscreteLogMetadata {
    private boolean solutionFound;
    private BigInteger solution;
    private String algorithmName;
    private double numberOfSecondsTaken;
}
