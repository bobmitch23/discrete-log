package com.discrete.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigInteger;

@Builder
@Getter
public class PrimeFactorMultiplicity {
    private BigInteger primeFactor;
    private BigInteger multiplicity;
}
