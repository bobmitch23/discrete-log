# The Discrete Log Problem

Given a large prime number *p*, a large intgeger *a*, and a number *g* such that every number *b* coprime to *p* is congruent to a power of *g mod p* (i.e. g is a primite root mod p), then *g<sup>a</sup> mod p* = *s* is very easy to compute using the repeated squaring algorithm.  However, determining the value of *a* if we are given *g*, *p* and *s* is extremely diffucult, and thus discrete log is considered a one way function.  Several important public-key cryptographic systems base their security on the assumption that the discrete logarithm problem over carefully chosen groups has no efficient solution. These include Diffie-Hellman Key Exchange, ElGamal encryption, and the Digital Signature Algorithm. 

While the discrete log problem is considered cryptographically sound, this repository provides three algorithms that provide significant improvement over a brute-force tactic.  They include the Pohlig-Hellman Algorithm (practically the fastest), the Baby Step Giant Step Algorithm and the Rho Algorithm.

## Java code
Containts a Spring Boot application with four endpoints, one that calculates the discrete log problem using all three algorithms, and then one endpoint for each algorithm.

*generator<sup>solution</sup>* *mod prime* = *base*

http://localhost:8080/discrete-log?generator=5&base=222137199848&prime=4889427811007

http://localhost:8080/pohlig-hellman?generator=5&base=222137199848&prime=4889427811007<br/>
http://localhost:8080/rho?generator=5&base=222137199848&prime=4889427811007<br/>
http://localhost:8080/baby-giant?generator=5&base=222137199848&prime=4889427811007<br/>

## Python code
import the discreteLog module and then you have the three algorithms available to you

```python
import discreteLog

rho_metadata = discreteLog.rho.solve(5, 222137199848, 4889427811007)
print(rho_metadata.solution, rho_metadata.secondsTaken)

pohlig_hellman_metadata = discreteLog.pohlig_hellman.solve(5, 222137199848, 4889427811007)
print(pohlig_hellman_metadata.solution, pohlig_hellman_metadata.secondsTaken)

baby_giant_metadata = discreteLog.baby_giant.solve(5, 222137199848, 4889427811007)
print(baby_giant_metadata.solution, baby_giant_metadata.secondsTaken)
```


