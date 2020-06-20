package com.discrete.service;

import com.discrete.domain.DiscreteLogMetadata;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface DiscreteLogCalculator {
    DiscreteLogMetadata calculateDiscreteLog(BigInteger generator, BigInteger base, BigInteger prime);
}
