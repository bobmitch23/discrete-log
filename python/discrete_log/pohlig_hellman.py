
import time 
from .util.discrete_log_util import extended_euclid
from .util.discrete_log_util import prime_factor_multiplicity
from .util.discrete_log_util import chinese_remainder
from .domain.discrete_log_metadata import DiscreteLogMetadata

def solve(gen, base, prime):
    start = time.time()
    prime_factor_multiplcities = prime_factor_multiplicity(prime-1)
    u = 0
    genInv = extended_euclid(gen, prime)[1][0] % prime
    prime1 = prime-1
    base_copy = base
    finalList = []
    for i in range(0, len(prime_factor_multiplcities)):
        x = pow(gen, (prime1//prime_factor_multiplcities[i][0]), prime)
        for j in range(0, (prime_factor_multiplcities[i][1])):
            y = pow(base, (prime1//((prime_factor_multiplcities[i][0])**(j+1))), prime)
            for k in range(0, (prime_factor_multiplcities[i][0])):
                if pow(x, k, prime) == y:
                    u += k*(prime_factor_multiplcities[i][0]**j)
                    break          
            base = (base * pow(genInv, (prime_factor_multiplcities[i][0]**j)*k, prime)) % prime
        finalList.append(u)
        u = 0
        base = base_copy
    factors = []
    for x in range(len(prime_factor_multiplcities)):
        factors.append(prime_factor_multiplcities[x][0]**prime_factor_multiplcities[x][1])

    finalNum = chinese_remainder(finalList, factors) % prime

    if pow(gen, finalNum, prime) == base:
        return DiscreteLogMetadata(finalNum, time.time()-start)
    else:
        return DiscreteLogMetadata(None, time.time()-start)
