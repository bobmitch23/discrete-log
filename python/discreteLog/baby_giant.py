import math
import time
from .util.discrete_log_util import extended_euclid
from .domain.discrete_log_metadata import DiscreteLogMetadata

def solve(gen, base, prime):
    start = time.time()
    X = dict()
    m = int(math.ceil(math.sqrt(prime-1))) 
    for j in range(m):
        X[pow(gen, j, prime)] = j
    invModPrime = extended_euclid(gen, prime)[1][0] % prime
    a = pow(invModPrime, m, prime)
    q =base 
    for i in range(1, m):       
        q = (q * a) % prime
        if q in X:
            finalNum = (i*m + X[q]) % prime
            if pow(gen, finalNum, prime) == base:
                return DiscreteLogMetadata(finalNum, time.time()-start)
            else:
                return DiscreteLogMetadata(None, time.time()-start)
