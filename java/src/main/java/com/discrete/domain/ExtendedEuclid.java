package com.discrete.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Builder
public class ExtendedEuclid {
    private BigInteger greatestCommonDivisor;
    private BigInteger coefficient1;
    private BigInteger coefficient2;
}
