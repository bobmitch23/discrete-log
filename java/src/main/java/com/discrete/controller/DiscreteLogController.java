package com.discrete.controller;

import com.discrete.domain.DiscreteLogMetadata;
import com.discrete.service.DiscreteLogCalculator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DiscreteLogController {
    private final Map<String,DiscreteLogCalculator> discreteLogCalculators;

    public DiscreteLogController(Map<String,DiscreteLogCalculator> discreteLogCalculators){
        this.discreteLogCalculators = discreteLogCalculators;
    }

    @GetMapping(value="/discrete-log", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DiscreteLogMetadata> calcDiscreteLogs(@RequestParam BigInteger generator, @RequestParam BigInteger base, @RequestParam BigInteger prime){
        return discreteLogCalculators.values().stream().map(calc -> calc.calculateDiscreteLog(generator, base, prime)).collect(Collectors.toList());
    }

    @GetMapping(value="/pohlig-hellman", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiscreteLogMetadata pohligHellman(@RequestParam BigInteger generator, @RequestParam BigInteger base, @RequestParam BigInteger prime){
        return discreteLogCalculators.get("pohligHellmanAlgorithm").calculateDiscreteLog(generator, base, prime);
    }

    @GetMapping(value="/baby-giant", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiscreteLogMetadata babyGiant(@RequestParam BigInteger generator, @RequestParam BigInteger base, @RequestParam BigInteger prime){
        return discreteLogCalculators.get("babyGiantAlgorithm").calculateDiscreteLog(generator, base, prime);
    }

    @GetMapping(value="/rho", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DiscreteLogMetadata rhoGiant(@RequestParam BigInteger generator, @RequestParam BigInteger base, @RequestParam BigInteger prime){
        return discreteLogCalculators.get("rhoAlgorithm").calculateDiscreteLog(generator, base, prime);
    }
}
